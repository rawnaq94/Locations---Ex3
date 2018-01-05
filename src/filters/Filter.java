package filters;

import java.util.List;
import java.util.Map;

import models.ScanInfo;
import models.WifiNetwork;

public interface Filter {
	public Map<ScanInfo, List<WifiNetwork>> filter(Map<ScanInfo, List<WifiNetwork>> scans);

	public String toStr();
}