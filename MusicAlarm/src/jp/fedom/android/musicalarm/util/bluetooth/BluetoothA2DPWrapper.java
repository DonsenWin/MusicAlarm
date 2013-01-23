package jp.fedom.android.musicalarm.util.bluetooth;

import java.lang.reflect.Method;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.IBluetoothA2dp;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public enum BluetoothA2DPWrapper {
	instance;

	public void BlutoothA2DPWapper(){
		prepareBlueTooth();
	}
	
	public static BluetoothA2DPWrapper getInstance(){
		return instance;
	}
	
	/**
	 * This is dummy comment. TODO:describe comment
	 */
	private void prepareBlueTooth() {
		final BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
		if (bluetooth == null) {
			//showMessage("bluetoothをサポートしていません。");
		} else {
			bluetooth.cancelDiscovery();
			//showMessage("bluetoothをサポートしています");
			if (bluetooth.isEnabled()) {
				// showMessage("bluetoothは有効です。");
			} else {
				//showMessage("bluetoothは無効です。");
			}
		}

	}
	public void connect(String macAddress){
		IBluetoothA2dp ibta = getIBluetoothA2dp();
		if (ibta != null) {
			final BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
			BluetoothDevice device = (BluetoothDevice) bluetooth.getRemoteDevice(macAddress);
			try {
				if (android.os.Build.VERSION.SDK_INT < 11) {
					ibta.connectSink(device);
				} else {
					// TODO: for 4.2 http://code.google.com/p/a2dp-connect/issues/detail?id=11
					// ibta.connect(device);
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void disconnect(String macAddress){
		IBluetoothA2dp ibta = getIBluetoothA2dp();
		if (ibta != null) {
			final BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
			BluetoothDevice device = (BluetoothDevice) bluetooth.getRemoteDevice(macAddress);
			try {
				if (android.os.Build.VERSION.SDK_INT < 11) {
					ibta.disconnectSink(device);
				} else {
					// TODO: for 4.2 http://code.google.com/p/a2dp-connect/issues/detail?id=11
					// ibta.connect(device);
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @return IBluetoothA2dp object. if can't create, return null
	 */
	private IBluetoothA2dp getIBluetoothA2dp() {
		// TODO: create wrapper class 
		IBluetoothA2dp ibta = null;
			try {
				Method m2 = Class.forName("android.os.ServiceManager").getDeclaredMethod("getService", String.class);
				IBinder b = (IBinder) m2.invoke(null, "bluetooth_a2dp");
				
				Class<?> c = Class.forName("android.bluetooth.IBluetoothA2dp").getDeclaredClasses()[0];
				Method m = c.getDeclaredMethod("asInterface", IBinder.class);
				m.setAccessible(true);
				
				ibta = (IBluetoothA2dp) m.invoke(null, b);
			} catch (Exception e) {
				Log.e("flowlab", "Erroraco!!! " + e.getMessage());
			}
		
		return ibta;
	}
}
