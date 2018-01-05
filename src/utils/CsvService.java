package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import models.ScanInfo;
import models.WifiNetwork;

public class CsvService {
	private static final String csvDelim = ",";

	public static String toString(Map<ScanInfo, List<WifiNetwork>> scans) {
		String csv = "Time,ID,Lat,Lon,Alt,#WiFi networks (up to 10),";
		for (int i = 1; i <= 10; i++) {
			csv += String.format("SSID%d,MAC%d,Frequncy%d,Signal%d", i, i, i, i);
			if (i < 10)
				csv += ",";
		}
		csv += "\n";
		for (Entry<ScanInfo, List<WifiNetwork>> entry : scans.entrySet()) {
			ScanInfo info = entry.getKey();
			List<WifiNetwork> networks = entry.getValue();
			List<WifiNetwork> networksSortedAndLimited = networks.stream()
					.sorted((x, y) -> (-1) * Double.compare(x.signal, y.signal)).limit(10).collect(Collectors.toList());
			csv += String.format("%s,%s,%s,%s,%s,%d,", info.time, info.id, info.latitude, info.longitude, info.altitude,
					networksSortedAndLimited.size());
			for (int i = 0; i < networksSortedAndLimited.size(); i++) {
				WifiNetwork network = networksSortedAndLimited.get(i);
				csv += String.format("%s,%s,%s,%s", network.ssid, network.mac, network.frequency, network.signal);
				if (i < networksSortedAndLimited.size() - 1)
					csv += ",";
			}
			csv += "\n";
		}
		return csv;
	}

	public static Map<ScanInfo, List<WifiNetwork>> read(Path csvFilePath) {
		Map<ScanInfo, List<WifiNetwork>> scans = new HashMap<ScanInfo, List<WifiNetwork>>();

		try {

			List<String> lines = Files.readAllLines(csvFilePath);
			String first = lines.get(0);
			if (!first.contains("WigleWifi") || !first.contains("model=")) {
				System.err
						.println("Bad format of file '" + csvFilePath.getFileName() + "'. expecting WigleWifi format.");
				return scans;
			}

			String id = first.split(csvDelim)[2].replace("model=", "");

			for (int i = 2; i < lines.size(); i++) {
				String[] parts = lines.get(i).split(csvDelim);
				if (parts.length <= 10 || !parts[10].equals("WIFI"))
					continue;
				WifiNetwork network = new WifiNetwork(parts[1], parts[0], parts[4], Double.parseDouble(parts[5]));

				SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				Date date = parser.parse(parts[3]);
				ScanInfo scanInfo = new ScanInfo(date, id, Double.parseDouble(parts[6]), Double.parseDouble(parts[7]),
						Double.parseDouble(parts[8]));

				if (scans.containsKey(scanInfo)) {
					scans.get(scanInfo).add(network);
				} else {
					List<WifiNetwork> networks = new LinkedList<WifiNetwork>();
					networks.add(network);
					scans.put(scanInfo, networks);
				}
			}

		} catch (FileNotFoundException e) {
			System.err.println("File not found at " + csvFilePath);
		} catch (IOException e) {
			System.err.println(e.toString());
			System.err.println(csvFilePath);
		} catch (ParseException e) {
			System.err.println(e.toString());
			System.err.println(csvFilePath);
		}
		return scans;
	}
}
