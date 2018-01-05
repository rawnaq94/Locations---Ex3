package filters;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import models.ScanInfo;
import models.WifiNetwork;

public class AltFilter implements Filter {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4056736915027528579L;
	double min;
	double max;

	public AltFilter(double min, double max) {
		super();
		this.min = min;
		this.max = max;
	}

	@Override
	public Map<ScanInfo, List<WifiNetwork>> filter(Map<ScanInfo, List<WifiNetwork>> scans) {
		return scans.entrySet().stream().filter(p -> p.getKey().altitude >= min && p.getKey().altitude <= max)
				.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
	}

	@Override
	public String toStr() {
		String s = "(";
		if (min != Double.MIN_VALUE) {
			s += this.min + " <= alt";
		}
		if (min != Double.MIN_VALUE && max != Double.MAX_VALUE) {
			s += " && ";
		}
		if (max != Double.MAX_VALUE) {
			s += "alt <= " + this.max;
		}
		s += ")";
		return s;
	}

}
