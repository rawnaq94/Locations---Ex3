package filters;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import models.ScanInfo;
import models.WifiNetwork;

/**
 * Filter interface
 */
public interface Filter extends Serializable {
	public Map<ScanInfo, List<WifiNetwork>> filter(Map<ScanInfo, List<WifiNetwork>> scans);

	public String toStr();
}