package models;

public class WifiNetwork {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((frequency == null) ? 0 : frequency.hashCode());
		result = prime * result + ((mac == null) ? 0 : mac.hashCode());
		long temp;
		temp = Double.doubleToLongBits(signal);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((ssid == null) ? 0 : ssid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WifiNetwork other = (WifiNetwork) obj;
		if (frequency == null) {
			if (other.frequency != null)
				return false;
		} else if (!frequency.equals(other.frequency))
			return false;
		if (mac == null) {
			if (other.mac != null)
				return false;
		} else if (!mac.equals(other.mac))
			return false;
		if (Double.doubleToLongBits(signal) != Double.doubleToLongBits(other.signal))
			return false;
		if (ssid == null) {
			if (other.ssid != null)
				return false;
		} else if (!ssid.equals(other.ssid))
			return false;
		return true;
	}

	public String ssid;
	public String mac;
	public String frequency;
	public double signal;

	public WifiNetwork(String ssid, String mac, String frequency, double signal) {
		super();
		this.ssid = ssid;
		this.mac = mac;
		this.frequency = frequency;
		this.signal = signal;
	}

}
