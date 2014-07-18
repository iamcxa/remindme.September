package me.iamcxa.remindme.provider;

import common.CommonVar;

import android.content.Context;
import android.location.Location;
import android.os.Handler;

public class LocationGetter implements GPSCallback {
	public double Lat;
	public double Lon;
	public double Speed;
	public GPSManager gpsManager = null;
	public static boolean isGpsStrat = false;
	private Context context;
	private Handler handler = null;
	private static PriorityCalculator UpdatePriority;
	private String updatePeriod;
	private boolean UseOnceTime;
	private static String timePeriod;
	private static boolean isSortingOn;

	@Override
	public void onGPSUpdate(Location location) {
		// TODO Auto-generated method stub
		Lat = location.getLatitude();
		Lon = location.getLongitude();
		Speed = location.getSpeed();
		CommonVar.debugMsg(0, "LocationProvider onGPSUpdate");
	}

	public LocationGetter(Context context) {
		this.context = context;
		gpsManager = new GPSManager();
	}

	public void stopListening() {
		gpsManager.stopListening();
		gpsManager.setGPSCallback(null);
		isGpsStrat = false;
		CommonVar.debugMsg(0, "LocationProvider stopListening");
	}

	public void startNetWorkListening(GPSCallback gpsCallBack) {
		gpsManager.startNetWorkListening(context);
		gpsManager.setGPSCallback(gpsCallBack);
		isGpsStrat = true;
		CommonVar.debugMsg(0, "LocationProvider startNetWorkListening");
	}

	public void startGpsListening(GPSCallback gpsCallBack) {
		gpsManager.startGpsListening(context);
		gpsManager.setGPSCallback(gpsCallBack);
		isGpsStrat = true;
		CommonVar.debugMsg(0, "LocationProvider startNetWorkListening");
	}

	public boolean isLocationGet() {
		if (Lon != 0 && Lat != 0) {
			return true;
		} else {
			return false;
		}
	}

	public void UpdatePriority() {
		handler = new Handler();
		UpdatePriority = new PriorityCalculator(context);

		updatePeriod = CommonVar.getUpdatePeriod();

		handler.postDelayed(GpsTime, Long.parseLong(updatePeriod));
		UseOnceTime = false;
	}

	public void UpdateOncePriority() {
		handler = new Handler();
		UpdatePriority = new PriorityCalculator(context);
		handler.post(GpsTime);
		UseOnceTime = true;
	}

	public void CloseUpdatePriority() {
		handler.removeCallbacks(GpsTime);
	}

	public static boolean getIsSortingOn() {
		return isSortingOn;
	}

	public static void setIsSortingOn(boolean isSortingOn) {
		LocationGetter.isSortingOn = isSortingOn;
	}

	public static String getTimePeriod() {
		return timePeriod;
	}

	public static void setTimePeriod(String timePeriod) {
		LocationGetter.timePeriod = timePeriod;
	}

	public  double CompareLastDistance(double Lat,double Lon){
		return DistanceCalculator.haversine(
				gpsManager.LastLocation().getLatitude(),
				gpsManager.LastLocation().getLongitude(),
				Lat, 
				Lon);
	}
	private Runnable GpsTime = new Runnable() {
		@Override
		public void run() {

			CommonVar.debugMsg(0, "service GpsTime start");

			setIsSortingOn(CommonVar.IS_SORTING_ON());			

			CommonVar.debugMsg(0, "service preferance isSortingOn="+getIsSortingOn());

			if (getIsSortingOn()) {

				if (isLocationGet()) {
					stopListening();
					CommonVar.debugMsg(0, "比較上次距離:"+CompareLastDistance(Lat,Lon));
					if(CompareLastDistance(Lat,Lon)>CommonVar.GpsSetting.GpsTolerateErrorDistance){
						CommonVar.debugMsg(0, "跟新權重");
						UpdatePriority.SetLatLng(Lat, Lon);
						UpdatePriority.ProcessData(UpdatePriority.loadData());
					}
					if (UseOnceTime) {
						CloseUpdatePriority();
					} else {
						updatePeriod = CommonVar.mPreferences.getString(
								"GetPriorityPeriod", "5000");
						handler.postDelayed(this, Long.parseLong(updatePeriod));
					}

				} else {
					if (isGpsStrat) {
						handler.postDelayed(this, 1000);
						CommonVar.debugMsg(0, "已經開啟GPS但是還沒拿到資料:" + Lat + ","
								+ Lon);
					} else {
						startNetWorkListening(LocationGetter.this);
						handler.postDelayed(this, 1000);
						// make log
						CommonVar.debugMsg(0, "開啟GPS:" + Lat + "," + Lon);
					}
				}

			}else{

				CommonVar.debugMsg(0, "service GpsTime stop because isSortingOn=False");

			}
		}
	};

}
