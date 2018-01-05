package filters;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import models.ScanInfo;
import models.WifiNetwork;

/**
 * Negation of one filter
 */
public class NotFilter implements Filter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7018950203491156173L;
	private Filter filter;

	public NotFilter(Filter filter) {
		this.filter = filter;
	}

	@Override
	public Map<ScanInfo, List<WifiNetwork>> filter(Map<ScanInfo, List<WifiNetwork>> scans) {
		Map<ScanInfo, List<WifiNetwork>> firstFilterItems = filter.filter(scans);
		return scans.entrySet().stream().filter(p -> !firstFilterItems.containsKey(p.getKey()))
				.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
	}

	@Override
	public String toStr() {
		return "not(" + filter.toStr() + ")";
	}
}
