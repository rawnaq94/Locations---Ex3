package main;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import models.ScanInfo;
import models.WifiNetwork;
import utils.CsvService;
import utils.IO;
import utils.KmlService;

class OldMain {
	public static void oldMain(String[] args) {
		if (args.length != 2) {
			System.err.println("Usage: Locations [DIRECTORY] [CSV|KML]");
			return;
		}
		String inputDir = args[0];
		String outputFormat = args[1].toLowerCase();
		if (!outputFormat.equals("csv") && !outputFormat.equals("kml")) {
			System.err.println("Output format should be CSV or KML.");
			return;
		}
		String[] files = IO.getFileNamesInFolder(inputDir);
		Map<ScanInfo, List<WifiNetwork>> scans = new HashMap<ScanInfo, List<WifiNetwork>>();
		for (String file : files) {
			if (!IO.getExtensionFromFileName(file).equals("csv")) {
				continue;
			}
			System.out.println("Parsing file: " + file);
			Map<ScanInfo, List<WifiNetwork>> newScans = CsvService.read(Paths.get(inputDir, file));
			for (Entry<ScanInfo, List<WifiNetwork>> entry : newScans.entrySet()) {
				if (scans.containsKey(entry.getKey())) {
					List<WifiNetwork> networks = scans.get(entry.getKey());
					networks.addAll(entry.getValue());
				} else {
					scans.put(entry.getKey(), entry.getValue());
				}
			}
		}
		Path outputFilePath = Paths.get(inputDir, "OUTPUT." + outputFormat);
		String outputStr = "";
		if (outputFormat.equals("kml")) {
			outputStr = KmlService.toString(scans);
		} else if (outputFormat.equals("csv")) {
			outputStr = CsvService.toString(scans);
		}
		System.out.println("Writing output to file: " + outputFilePath.toAbsolutePath());
		IO.writeToFile(outputFilePath, outputStr);
		System.out.println("DONE");
	}
}
