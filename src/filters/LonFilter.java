package filters;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import models.ScanInfo;
import models.WifiNetwork;

/**
 * Longitude filter
 */
public class LonFilter implements Filter {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8063876880082907864L;
	double min;
	double max;

	public LonFilter(double min, double max) {
		super();
		this.min = min;
		this.max = max;
	}

	@Override
	public Map<ScanInfo, List<WifiNetwork>> filter(Map<ScanInfo, List<WifiNetwork>> scans) {
		return scans.entrySet().stream().filter(p -> p.getKey().longitude >= min && p.getKey().longitude <= max)
				.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
	}

	@Override
	public String toStr() {
		String s = "(";
		if (min != Double.MIN_VALUE) {
			s += this.min + " <= lon";
		}
		if (min != Double.MIN_VALUE && max != Double.MAX_VALUE) {
			s += " && ";
		}
		if (max != Double.MAX_VALUE) {
			s += "lon <= " + this.max;
		}
		s += ")";
		return s;
	}

}
