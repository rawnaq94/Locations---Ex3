package filters;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import models.ScanInfo;
import models.WifiNetwork;

public class OrFilter implements Filter {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1344822895836621240L;
	private Filter filter;
	private Filter otherFilter;

	public OrFilter(Filter filter, Filter otherFilter) {
		this.filter = filter;
		this.otherFilter = otherFilter;
	}

	@Override
	public Map<ScanInfo, List<WifiNetwork>> filter(Map<ScanInfo, List<WifiNetwork>> scans) {
		Map<ScanInfo, List<WifiNetwork>> firstFilterItems = filter.filter(scans);
		Map<ScanInfo, List<WifiNetwork>> otherFilterItems = otherFilter.filter(scans);

		for (Entry<ScanInfo, List<WifiNetwork>> scan : otherFilterItems.entrySet()) {
			if (!firstFilterItems.containsKey(scan.getKey())) {
				firstFilterItems.put(scan.getKey(), scan.getValue());
			}
		}
		return firstFilterItems;
	}

	@Override
	public String toStr() {
		return "(" + filter.toStr() + " || " + otherFilter.toStr() + ")";
	}
}
