package utils;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import filters.Filter;
import models.ScanInfo;
import models.WifiNetwork;

/*
 * this service wrap the scans results and handle all the operations related to them including the filters.
 */
public class ScansService implements INeedToReloadData {
	private Map<ScanInfo, List<WifiNetwork>> scans = new HashMap<ScanInfo, List<WifiNetwork>>();
	private List<String> csvs = new LinkedList<String>();
	private INeedToReloadData notify;
	private Filter filter = null;

	public ScansService(INeedToReloadData n) {
		notify = n;
	}

	public void clearScans() {
		scans = new HashMap<ScanInfo, List<WifiNetwork>>();
	}

	public void clearFilter() {
		filter = null;
	}

	public Map<ScanInfo, List<WifiNetwork>> getScans() {
		if (filter == null) {
			return scans;
		}
		return filter.filter(scans);
	}

	public void saveToCsv(String absolutePath) {
		System.out.println("Writing output to file: " + absolutePath);
		IO.writeToFile(Paths.get(absolutePath), CsvService.toString(scans));
	}

	public void saveToKml(String absolutePath) {
		System.out.println("Writing output to file: " + absolutePath);
		IO.writeToFile(Paths.get(absolutePath), KmlService.toString(scans));
	}

	private void addCsvInternal(String fileFullPath) {
		System.out.println("Parsing file: " + fileFullPath);
		Map<ScanInfo, List<WifiNetwork>> newScans = CsvService.read(Paths.get(fileFullPath));
		for (Entry<ScanInfo, List<WifiNetwork>> entry : newScans.entrySet()) {
			if (scans.containsKey(entry.getKey())) {
				List<WifiNetwork> networks = scans.get(entry.getKey());

				entry.getValue().stream().forEach(n -> {
					if (!networks.contains(n))
						networks.add(n);
				});
			} else {
				scans.put(entry.getKey(), entry.getValue());
			}
		}
	}

	public void addCsv(final String fileFullPath) {
		addCsvInternal(fileFullPath);
		csvs.add(fileFullPath);
		new Thread(new Runnable() {
			File file = new File(fileFullPath);
			long lastModified = file.lastModified();

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (lastModified != file.lastModified()) {
						lastModified = file.lastModified();
						ScansService.this.reload();
					}
				}
			}
		}).start();
	}

	public void addDir(String inputDir) {
		String[] files = IO.getFileNamesInFolder(inputDir);
		for (String file : files) {
			if (!IO.getExtensionFromFileName(file).equals("csv")) {
				continue;
			}
			addCsv(Paths.get(inputDir, file).toString());
		}
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public Filter getFilter() {
		return filter;
	}

	public String getFilterString() {
		if (filter == null)
			return "()";
		return filter.toStr();
	}

	@Override
	public void reload() {
		clearScans();
		for (String csv : csvs) {
			addCsvInternal(csv);
		}
		notify.reload();
	}
}
