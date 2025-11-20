package cat.tmb.jadb;

public class DevicesList {
	
	private static final String[] IP_DEVICES = { "192.168.51.2", "192.168.51.7", "192.168.51.13", "192.168.51.23",
//		    "192.168.51.10",
			"192.168.51.8", "192.168.51.6", "192.168.51.9", "192.168.51.5", "192.168.51.4", "192.168.51.3",
			"192.168.51.1", "192.168.51.11" };
	
	public static void iterateDevices() {
		for (String device : IP_DEVICES) {
//			connection(device);
		}
	}


}
