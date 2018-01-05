package filters;

import java.util.List;
import java.util.Map;

import models.ScanInfo;
import models.WifiNetwork;

/**
 * AND of two filters
 */
public class AndFilter implements Filter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5305747891352629205L;
	private Filter filter;
	private Filter otherFilter;

	public AndFilter(Filter filter, Filter otherFilter) {
		this.filter = filter;
		this.otherFilter = otherFilter;
	}

	@Override
	public Map<ScanInfo, List<WifiNetwork>> filter(Map<ScanInfo, List<WifiNetwork>> scans) {
		return otherFilter.filter(filter.filter(scans));
	}

	@Override
	public String toStr() {
		return "(" + filter.toStr() + " && " + otherFilter.toStr() + ")";
	}
}
