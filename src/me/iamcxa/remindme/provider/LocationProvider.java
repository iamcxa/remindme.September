package me.iamcxa.remindme.provider;

import me.iamcxa.remindme.CommonUtils;
import android.content.Context;
import android.location.Location;

public class LocationProvider implements GPSCallback {
	public double Lat;
	public double Lon;
	public double Speed;
	public GPSManager gpsManager = null;
	public static boolean isGpsStrat = false;
	private Context context;

	@Override
	public void onGPSUpdate(Location location) {
		// TODO Auto-generated method stub
		Lat = location.getLatitude();
		Lon = location.getLongitude();
		Speed = location.getSpeed();
		CommonUtils.debugMsg(0, "LocationProvider onGPSUpdate");
	}

	public LocationProvider(Context context) {
		this.context = context;
		gpsManager = new GPSManager();
	}

	public void stopListening() {
		gpsManager.stopListening();
		gpsManager.setGPSCallback(null);
		CommonUtils.debugMsg(0, "LocationProvider stopListening");
	}

	public void startNetWorkListening(GPSCallback gpsCallBack) {
		gpsManager.startNetWorkListening(context);
		gpsManager.setGPSCallback(gpsCallBack);
		CommonUtils.debugMsg(0, "LocationProvider startNetWorkListening");
	}
	
	public void startGpsListening(GPSCallback gpsCallBack) {
		gpsManager.startGpsListening(context);
		gpsManager.setGPSCallback(gpsCallBack);
		CommonUtils.debugMsg(0, "LocationProvider startNetWorkListening");
	}
	
	public boolean getGpsStatus() {
		if ((Lon > 0) && (Lat > 0)) {
			isGpsStrat = true;
		} else {
			isGpsStrat = false;
		}
		return isGpsStrat;
	}
}
