package cat.tmb.jadb;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

import se.vidstige.jadb.ConnectionToRemoteDeviceException;
import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.JadbException;

public class JadbConnectionService {

	public static void main(String[] args) {
		connection("10.160.44.153");
	}

	private static void connection(String ipAddress) {

		JadbConnection jadb = null;
		InetSocketAddress address = new InetSocketAddress(ipAddress, 5555);
		InetSocketAddress connected = null;

		try {
			jadb = new JadbConnection();
			connected = jadb.connectToTcpDevice(address);
			List<JadbDevice> devices = jadb.getDevices();

			if (!devices.isEmpty()) {
				JadbDevice jadbDevice = devices.get(0);
				System.out.println("Connected device: " + jadbDevice);
				InputStream inputStream = jadbDevice.executeShell("pm", "install", "-r", "/sdcard/Download/pic/update/1.3.9/app-int-release.apk");
				String output = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
				System.out.println("Installation output: " + output);
				verifyOperation("install", "app-int-release.apk", output);

				InputStream monkeyStream = jadbDevice.executeShell("monkey", "-p", "cat.tmb.pic.pre", "-c", "android.intent.category.LAUNCHER", "1");
				String monkeyOutput = new String(monkeyStream.readAllBytes(), StandardCharsets.UTF_8);
				System.out.println("Monkey output: " + monkeyOutput);

			}

		} catch (IOException | JadbException | ConnectionToRemoteDeviceException e) {
			System.out.println("Error during ADB operation: " + e.getMessage());
			e.printStackTrace();

		} finally {

			try {
				if (jadb != null && connected != null) {
					InetSocketAddress disconnected = jadb.disconnectFromTcpDevice(address);
					System.out.println("Disconnected from device at: " + disconnected);
				}
			} catch (IOException | JadbException | ConnectionToRemoteDeviceException e) {
				System.out.println("Error during disconnect: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private static String getErrorMessage(String operation, String target, String errorMessage) {
		return "Could not " + operation + " " + target + ": " + errorMessage;
	}

	private static void verifyOperation(String operation, String target, String result) throws JadbException {
		if (!result.contains("Success"))
			throw new JadbException(getErrorMessage(operation, target, result));
	}

}
