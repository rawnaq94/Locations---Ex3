package filters;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import models.ScanInfo;
import models.WifiNetwork;

public class LatFilter implements Filter {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6327336357516935654L;
	double min;
	double max;

	public LatFilter(double min, double max) {
		super();
		this.min = min;
		this.max = max;
	}

	@Override
	public Map<ScanInfo, List<WifiNetwork>> filter(Map<ScanInfo, List<WifiNetwork>> scans) {
		return scans.entrySet().stream().filter(p -> p.getKey().latitude >= min && p.getKey().latitude <= max)
				.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
	}

	@Override
	public String toStr() {
		String s = "(";
		if (min != Double.MIN_VALUE) {
			s += this.min + " <= lat";
		}
		if (min != Double.MIN_VALUE && max != Double.MAX_VALUE) {
			s += " && ";
		}
		if (max != Double.MAX_VALUE) {
			s += "lat <= " + this.max;
		}
		s += ")";
		return s;
	}
}
