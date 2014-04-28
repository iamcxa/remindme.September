package me.iamcxa.remindme.provider;

import me.iamcxa.remindme.CommonUtils;
import android.content.Context;
import android.location.Location;
import android.os.Handler;

public class LocationProvider implements GPSCallback {
	public double Lat;
	public double Lon;
	public double Speed;
	public GPSManager gpsManager = null;
	public static boolean isGpsStrat = false;
	private Context context;
	private Handler handler = null;
	private static PriorityProvider UpdatePriority;
	private Long time;
	private boolean UseOnceTime;
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
	
	public void UpdatePriority(long time){
		handler = new Handler();
		UpdatePriority = new PriorityProvider(context);
		this.time=time;
		handler.postDelayed(GpsTime, time);
		UseOnceTime =false;
	}
	
	public void UpdateOncePriority(){
		handler = new Handler();
		UpdatePriority = new PriorityProvider(context);
		handler.post(GpsTime);
		UseOnceTime =true;
	}
	
	
	public void CloseUpdatePriority(){
		handler.removeCallbacks(GpsTime);
	}
	
	
	
	private Runnable GpsTime = new Runnable() {
		@Override
		public void run() {
//			 Stopself();
			CommonUtils.debugMsg(0, "service GpsTime run");
	

			if (Lat != 0 && Lon != 0) {

				// isGpsStrat = false;
				stopListening();

				UpdatePriority
						.SetLatLng(Lat, Lon);
				UpdatePriority.ProcessData(UpdatePriority.loadData());
				if(UseOnceTime){
					CloseUpdatePriority();
					stopListening();
				}
				else{
					handler.postDelayed(this, time);
				}
			
			} else {
				if (getGpsStatus()) {
					handler.postDelayed(this, 1000);

					CommonUtils.debugMsg(0, "已經開啟GPS但是還沒拿到資料:"
							+ Lat + "," + Lon);
				} else {
					startNetWorkListening(LocationProvider.this);

					// isGpsStrat = true;
					handler.postDelayed(this, 1000);

					// make log
					CommonUtils.debugMsg(0, "開啟GPS:" + Lat + ","
							+ Lon);
				}
			}
		}
	};

}
