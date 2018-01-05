package utils;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import filters.Filter;
import models.ScanInfo;
import models.WifiNetwork;

public class ScansService {
	private Map<ScanInfo, List<WifiNetwork>> scans = new HashMap<ScanInfo, List<WifiNetwork>>();
	private Filter filter = null;

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

	public void addCsv(String fileFullPath) {
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
}
