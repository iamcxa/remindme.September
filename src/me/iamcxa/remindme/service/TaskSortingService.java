/**
 * 
 */
package me.iamcxa.remindme.service;

import java.util.List;

import me.iamcxa.remindme.provider.DistanceProvider;
import me.iamcxa.remindme.provider.GPSManager;
import com.google.android.gms.drive.internal.r;
import me.iamcxa.remindme.CommonUtils;
import me.iamcxa.remindme.R;
import me.iamcxa.remindme.RemindmeMainActivity;
import me.iamcxa.remindme.provider.GPSCallback;
import android.R.integer;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

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
	private static double Lat;
	private static double Lon;
	private Handler handler = null;
	 private GPSManager gpsManager = null;
	 private static boolean isGpsStrat= false;
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
		
		CommonUtils.debugMsg(0,"service pre-start");
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager nNotificationManager = (NotificationManager) getSystemService(ns);
		CharSequence tickertextr = "remindme is running";
		long when = System.currentTimeMillis();
		Intent intent = new Intent(this, RemindmeMainActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				intent, 0);
		Bitmap bm = BitmapFactory.decodeResource(getResources(),
				R.drawable.empty_flag);
		Notification noti = new Notification.Builder(getApplicationContext())
				.setContentTitle("remindme Task shorting service")
				.setContentText("remindme is running")
				.setSmallIcon(R.drawable.outline_star_act).setLargeIcon(bm)
				.setNumber(notifyID).setSubText("subtext").setWhen(when)
				.setContentIntent(contentIntent).build();
		
		handler.postDelayed(GpsTime, 5000);
		try {
			nNotificationManager.notify(notifyID,noti);
			CommonUtils.debugMsg(0,"service started");
		} catch (Exception e) {
			CommonUtils.debugMsg(0,"service start error="+e.toString()
					);
		}

	}
	
	
	private Runnable GpsTime = new Runnable() {
		  public void run() {
			  if(Lat!=0 && Lon!=0)
				{
					//Toast.makeText(getApplicationContext(),"當前速度:"+speed,Toast.LENGTH_SHORT).show();
					gpsManager.stopListening();
				    gpsManager.setGPSCallback(null);
				    isGpsStrat=false;
				    handler.postDelayed(this,10000);
//				    Toast.makeText(getApplicationContext(), "關閉GPS:"+Lat+","+Lon,Toast.LENGTH_SHORT).show();
				    double distances = DistanceProvider.Distance("22.65141212389,120.349236913", Lat, Lon);
					if(distances<1)
					{
					Toast.makeText(
							getApplicationContext(),
							Math.floor(distances*1000) + "公里",
									Toast.LENGTH_SHORT).show();
					}
					else
					{
						Toast.makeText(
								getApplicationContext(),
								Math.floor(distances) + "公里",
										Toast.LENGTH_SHORT).show();
					}
				    Lat=0;
				    Lon=0;
				}else
				{
					if(isGpsStrat)
					{
						handler.postDelayed(this,1000);
//						Toast.makeText(getApplicationContext(), "已經開啟GPS但是還沒拿到資料:"+Lat+","+Lon,Toast.LENGTH_SHORT).show();
					}
					else
					{
						gpsManager.startNetWorkListening(getApplicationContext());
						gpsManager.setGPSCallback(TaskSortingService.this);
						isGpsStrat=true;
						handler.postDelayed(this,1000);
//						Toast.makeText(getApplicationContext(), "開啟GPS:"+Lat+","+Lon,Toast.LENGTH_SHORT).show();
					}
				}
		  }
	};
	
	

}
