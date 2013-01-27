package jp.fedom.android.musicalarm.util.bluetooth;

import java.lang.reflect.Method;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.IBluetoothA2dp;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * This is dummy comment.
 * TODO: update comment
 * @author taka2
 *
 */
public enum BluetoothA2DPWrapper {
    /** Singleton implement. */
    instance;

    /**
     * This is dummy comment.
     * TODO: update comment
     * @return singleton instance
     */
    public static BluetoothA2DPWrapper getInstance() {
        return instance;
    }

    /**
     * This is dummy comment.
     * TODO: update comment
     * @param macAddress MACADDRESS of device which you want to connect
     */
    public void connect(final String macAddress) {
        final IBluetoothA2dp ibta = getIBluetoothA2dp();
        if (ibta != null) {
            final BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
            bluetooth.cancelDiscovery();
            final BluetoothDevice device = (BluetoothDevice) bluetooth.getRemoteDevice(macAddress);
            try {
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
                    ibta.connectSink(device);
                } else {
                    Log.i("VERSION", "this is over SDK 11");
                    // TODO: for 4.2 http://code.google.com/p/a2dp-connect/issues/detail?id=11
                    // ibta.connect(device);
                }
            } catch (RemoteException e) {
                Log.e("connect", "RemoteException", e);
            }
        }
    }

    /**
     * This is dummy comment.
     * TODO: update comment
     * @param macAddress MACADDRESS of device which you want to disconnect
     */
    public void disconnect(final String macAddress) {
        final IBluetoothA2dp ibta = getIBluetoothA2dp();
        if (ibta != null) {
            final BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
            bluetooth.cancelDiscovery();
            final BluetoothDevice device = (BluetoothDevice) bluetooth.getRemoteDevice(macAddress);
            try {
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
                    ibta.disconnectSink(device);
                } else {
                    Log.i("VERSION", "this is over SDK 11");
                    // TODO: for 4.2
                    // http://code.google.com/p/a2dp-connect/issues/detail?id=11
                    // ibta.connect(device);
                }
            } catch (RemoteException e) {
                Log.e("connect", "RemoteException", e);
            }
        }
    }

    /**
     * This is dummy comment.
     * TODO: update comment
     * @return IBluetoothA2dp object. if can't create, return null
     */
    private IBluetoothA2dp getIBluetoothA2dp() {
        IBluetoothA2dp ibta = null;
        try {
            final Method getServicemethod = Class.forName(
                    "android.os.ServiceManager").getDeclaredMethod(
                    "getService", String.class);

            final IBinder ibinder = (IBinder) getServicemethod.invoke(null,    "bluetooth_a2dp");

            final Class<?> a2dpClass = Class.forName(
                    "android.bluetooth.IBluetoothA2dp").getDeclaredClasses()[0];

            final Method interfaceMethod = a2dpClass.getDeclaredMethod("asInterface", IBinder.class);
            interfaceMethod.setAccessible(true);

            ibta = (IBluetoothA2dp) interfaceMethod.invoke(null, ibinder);
        } catch (Exception e) {
            Log.e("flowlab", "Erroraco!!! " + e.getMessage());
        }
        return ibta;
    }
}
