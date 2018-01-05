package filters;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import models.ScanInfo;
import models.WifiNetwork;

public class TimeFilter implements Filter {
	Date min;
	Date max;

	public TimeFilter(Date min, Date max) {
		this.min = min;
		this.max = max;
	}

	@Override
	public Map<ScanInfo, List<WifiNetwork>> filter(Map<ScanInfo, List<WifiNetwork>> scans) {
		return scans.entrySet().stream()
				.filter(p -> (p.getKey().time.after(min) || p.getKey().time.equals(min))
						&& (p.getKey().time.before(max) || p.getKey().time.equals(max)))
				.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
	}

	@Override
	public String toStr() {
		String s = "(";
		if (!min.equals(new Date(Long.MIN_VALUE))) {
			s += this.min + " <= time";
		}
		if (!min.equals(new Date(Long.MIN_VALUE)) && !max.equals(new Date(Long.MAX_VALUE))) {
			s += " && ";
		}
		if (!max.equals(new Date(Long.MAX_VALUE))) {
			s += "time <= " + this.max;
		}
		s += ")";
		return s;
	}

}
