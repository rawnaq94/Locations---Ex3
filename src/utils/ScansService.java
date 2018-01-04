package utils;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import models.ScanInfo;
import models.WifiNetwork;

public class ScansService {
	public Map<ScanInfo, List<WifiNetwork>> scans = new HashMap<ScanInfo, List<WifiNetwork>>();

	public void clear() {
		scans = new HashMap<ScanInfo, List<WifiNetwork>>();
	}

	public void saveToCsv(String absolutePath) {
		// TODO Auto-generated method stub

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
}
