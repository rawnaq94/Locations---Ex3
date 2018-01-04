package utils;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import models.ScanInfo;
import models.WifiNetwork;

public class KmlService {

	private static String KmlFileTemplateFormat = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<kml xmlns=\"http://www.opengis.net/kml/2.2\"><Document>\n%s\n</Document>\n</kml>";
	private static String KmlPlacemarkTemplateFormat = "<Placemark>\n<name>%s</name>\n<description>%s</description>\n<Point>\n<coordinates>%s,%s,%s</coordinates></Point>\n</Placemark>";

	public static String toString(Map<ScanInfo, List<WifiNetwork>> scans) {
		String kmlStr = "";
		for (Entry<ScanInfo, List<WifiNetwork>> entry : scans.entrySet()) {
			ScanInfo info = entry.getKey();
			List<WifiNetwork> networks = entry.getValue();
			for (WifiNetwork network : networks) {
				kmlStr += String.format(KmlPlacemarkTemplateFormat, network.ssid, network.mac, info.longitude,
						info.latitude, info.altitude);
			}
		}
		return String.format(KmlFileTemplateFormat, kmlStr);
	}
}
