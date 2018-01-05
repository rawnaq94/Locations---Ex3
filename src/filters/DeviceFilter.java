package filters;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import models.ScanInfo;
import models.WifiNetwork;

public class DeviceFilter implements Filter {
	String str;

	public DeviceFilter(String str) {
		super();
		this.str = str;
	}

	@Override
	public Map<ScanInfo, List<WifiNetwork>> filter(Map<ScanInfo, List<WifiNetwork>> scans) {
		return scans.entrySet().stream().filter(p -> p.getKey().id.contains(str))
				.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
	}

	@Override
	public String toStr() {
		return "('" + str + "' in device)";
	}

}
