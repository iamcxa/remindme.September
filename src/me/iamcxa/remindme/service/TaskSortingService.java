/**
 * 
 */
package me.iamcxa.remindme.service;

import me.iamcxa.remindme.provider.GPSManager;
import me.iamcxa.remindme.provider.TaskDBProvider;
import me.iamcxa.remindme.CommonUtils;
import me.iamcxa.remindme.R;
import me.iamcxa.remindme.RemindmeMainActivity;
import me.iamcxa.remindme.provider.GPSCallback;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;

/**
 * @author cxa
 * 
 */
public class TaskSortingService extends Service implements GPSCallback {

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		handler.removeCallbacks(GpsTime);
	}
	private static final int notifyID = 1;
	public static double Lat;
	public static double Lon;
	private Handler handler = null;
	 private GPSManager gpsManager = null;
	 private static boolean isGpsStrat= false;
	 private String msg=null;
	 private static Notification noti ;
	 private static GetDistance UpdateDistance;
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * me.iamcxa.remindme.provider.GPSCallback#onGPSUpdate(android.location.
	 * Location)
	 */
	@Override
	public void onGPSUpdate(Location location) {
		// TODO Auto-generated method stub
		Lat=location.getLatitude();
        Lon=location.getLongitude();
       // Toast.makeText(getApplicationContext(), Lat+","+Lon,Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		handler = new Handler();
		gpsManager = new GPSManager();
		CommonUtils.mPreferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		
		String timePeriod=CommonUtils.mPreferences.getString("GetPriorityPeriod", "5000");

		handler.postDelayed(GpsTime, Long.parseLong(timePeriod));
		
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//
//				CommonUtils.debugMsg(0,"service setDaemon");
//				handler.postDelayed(GpsTime, CommonUtils.mPreferences.getLong("GetPriorityPeriod", 5000));
//			}
//		}).setDaemon(true);
		
		UpdateDistance = new GetDistance(getApplicationContext());
		
		CommonUtils.debugMsg(0,"service pre-start");
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager nNotificationManager = (NotificationManager) getSystemService(ns);
		CharSequence tickertextr = "remindme is running";
		long when = System.currentTimeMillis();
		Intent intent = new Intent(this, RemindmeMainActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				intent, 0);
		Bitmap bm = BitmapFactory.decodeResource(getResources(),
				R.drawable.remindme_logo);
		 noti = new Notification.Builder(getApplicationContext())
		.setContentTitle("remindme Task shorting service")
		.setContentText("remindme is running")
		.setSmallIcon(R.drawable.remindme_logo).setLargeIcon(bm)
		.setNumber(notifyID).setSubText(msg).setWhen(when)
		.setContentIntent(contentIntent).build();
		  nNotificationManager.notify(notifyID,noti);
	
		try {

			
		
			
			CommonUtils.debugMsg(0,"service started");
		} catch (Exception e) {
			CommonUtils.debugMsg(0,"service start error="+e.toString()
					);
		}

	}
	
	
	
	private Runnable GpsTime = new Runnable() {
		  @Override
		public void run() {
			  if(Lat!=0 && Lon!=0)
				{
					//Toast.makeText(getApplicationContext(),"當前速度:"+speed,Toast.LENGTH_SHORT).show();
					gpsManager.stopListening();
				    gpsManager.setGPSCallback(null);
				    isGpsStrat=false;
				    handler.postDelayed(this,10000);				    
//				    GetDistance.Lat=Lat;
//				    GetDistance.Lon=Lon;
				    
//					Intent intent1 = new Intent();
//					intent1.setClass(getApplication(), GetDistance.class);
//					intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					startActivity(intent1);
				    
				    UpdateDistance.SetLatLng(Lat, Lon);
				    UpdateDistance.ProcessData(UpdateDistance.loadData());
				    
					CommonUtils.debugMsg(0,"service setDaemon ok"); 
					Lat=0;
				    Lon=0;
				}else
				{
					if(isGpsStrat)
					{
						handler.postDelayed(this,1000);
						msg="已經開啟GPS但是還沒拿到資料:"+Lat+","+Lon;
						//noti.notify();
				//	Toast.makeText(getApplicationContext(), "已經開啟GPS但是還沒拿到資料:"+Lat+","+Lon,Toast.LENGTH_SHORT).show();
					}
					else
					{
						gpsManager.startNetWorkListening(getApplicationContext());
						gpsManager.setGPSCallback(TaskSortingService.this);
						isGpsStrat=true;
						handler.postDelayed(this,1000);
						//Toast.makeText(getApplicationContext(), "開啟GPS:"+Lat+","+Lon,Toast.LENGTH_SHORT).show();
						msg="開啟GPS:"+Lat+","+Lon;
						//noti.notify();
					}
				}
		  }
	};
	
	

}
