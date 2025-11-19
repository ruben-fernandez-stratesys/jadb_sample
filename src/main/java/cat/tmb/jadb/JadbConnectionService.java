package cat.tmb.jadb;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import se.vidstige.jadb.ConnectionToRemoteDeviceException;
import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.JadbException;

public class JadbConnectionService {
	
	private static final String[] IP_DEVICES = {
		    "192.168.51.2",
		    "192.168.51.7",
		    "192.168.51.13",
		    "192.168.51.23",
//		    "192.168.51.10",
		    "192.168.51.8",
		    "192.168.51.6",
		    "192.168.51.9",
		    "192.168.51.5",
		    "192.168.51.4",
		    "192.168.51.3",
		    "192.168.51.1",
		    "192.168.51.11"
		};


	public static void main(String[] args) {

		connection();

	}


	private static void connection() {
		try {
			
			for (String device : IP_DEVICES) {
				JadbConnection jadb = new JadbConnection();
				InetSocketAddress address = new InetSocketAddress(device, 5555);
				InetSocketAddress connected = jadb.connectToTcpDevice(address);
				List<JadbDevice> devices = jadb.getDevices();
				
				for (JadbDevice jadbDevice : devices) {
					if (jadbDevice.getSerial().equals(device.concat(":5555"))) {
						System.out.println("Connected device: " + jadbDevice);
						System.out.println("Connected to device at: " + connected);
					}
				}

				InetSocketAddress disconnected = jadb.disconnectFromTcpDevice(address);
				System.out.println("Disconnected from device at: " + disconnected);
			}
			
//			File apkFile = new File("C:\\ruta\\a\\tu\\archivo.apk");
//
//			if (!apkFile.exists()) {
//				System.out.println("El archivo APK no existe: " + apkFile.getAbsolutePath());
//				return;
//			}

//			List<JadbDevice> devices = jadb.getDevices();

//			if (devices.isEmpty()) {
//				System.out.println("No hay dispositivos conectados.");
//				return;
//			}c

//			for (JadbDevice jadbDevice : devices) {
//				jadbDevice.pull(apkFile, new RemoteFile("/sdcard/app-pro-release.apk"));
//				new PackageManager(jadbDevice).install(apkFile);
//				System.out.println("Device: " + jadbDevice);
//			}

			
//			for (String device : IP_DEVICES) {
//				InetSocketAddress address = new InetSocketAddress(device, 5555);
//				InetSocketAddress disconnected = jadb.disconnectFromTcpDevice(address);
//				System.out.println("Disconnected from device at: " + disconnected);
//			}
//			
//			List<JadbDevice> devicesAfterDisconnect = jadb.getDevices();
//			
//			System.out.println("Connected devices:" + devicesAfterDisconnect);

		} catch (IOException | JadbException | ConnectionToRemoteDeviceException e) {
			System.out.println("Error during ADB operation: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	

}
