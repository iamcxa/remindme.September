<<<<<<< HEAD
/**
 * 
 */
package me.iamcxa.remindme.editor;

import java.util.Calendar;
import java.util.Date;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import me.iamcxa.remindme.CommonUtils;
import me.iamcxa.remindme.R;
import me.iamcxa.remindme.CommonUtils.TaskCursor;
import me.iamcxa.remindme.provider.GPSCallback;
import me.iamcxa.remindme.provider.GPSManager;
import me.iamcxa.remindme.provider.GeocodingAPI;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import me.iamcxa.remindme.provider.WorkaroundMapFragment;

/**
 * @author cxa
 * 
 */

public class RemindmeTaskEditor extends FragmentActivity implements GPSCallback {

	private static SaveOrUpdate mSaveOrUpdate;

	// 宣告GPS模組
	private static GPSManager gpsManager = null;

	// 宣告pick
	private static GoogleMap map;

	// 顯示日期、時間對話方塊常數
	private static int mYear;
	private static int mMonth;
	private static int mDay;
	private static int mHour;
	private static int mMinute;
	static final int DATE_DIALOG_ID = 0;
	static final int TIME_DIALOG_ID = 1;

	// EditText
	private static EditText EditTextTittle;
	private static EditText SearchText;
	private static MultiAutoCompleteTextView boxTittle;
	private static MultiAutoCompleteTextView boxContent;

	private static TextView dateDesc;
	private static TextView timeDesc;
	private static TextView contentDesc;
	// Button
	private static Button Search;
	private static Button buttonPickFile;
	private static Button buttonSetDate;
	private static Button buttonSetTime;
	private static Button buttonSetLocation;
	private static Button buttonTakePhoto;

	private static ImageButton cancelLocation;
	private static ImageButton cancelDateTime;
	private static ImageButton cancelAttachment;

	// ImageButton
	private static ImageButton OK;
	// 是否開啟提醒
	private int on_off = 0;
	// String
	// 保存內容、日期與時間字串
	private static String tittle = null;
	private static String content = null;
	private static String switcher = null;
	private static String endDate = null, endTime = null;
	private static String locationName = null;
	private static String isRepeat = null;
	private static String isFixed = null;
	private static String coordinate = null;
	private static String collaborator = null;
	private static String created = null;
	private static String LastTimeSearchName = "";
	private static String is_Fixed = null;

	// 經緯度
	private static Double Latitude;
	private static Double Longitude;
	private static ScrollView main_scrollview;

	private static CheckBox checkBoxIsFixed;

	private Handler GpsTimehandler = new Handler();
	// gps使用時間
	private static int GpsUseTime = 0;
	// 是否有搜尋過地點
	private static Boolean isdidSearch = false;
	private static Boolean isDraped = false;
	// 備忘錄ID
	private int taskId;
	// 存取佈局實例
	private static LayoutInflater li;

	public View vv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_editor);

		setComponents();
		setGPS();
		// setMAP();

		// 取得Intent
		final Intent intent = getIntent();
		// 設定Uri
		if (intent.getData() == null) {
			intent.setData(CommonUtils.CONTENT_URI);
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		// 初始化列表
		init(getIntent());
	}

	private void setGPS() {
		gpsManager = new GPSManager();
		gpsManager.startGpsListening(getApplicationContext());
		gpsManager.setGPSCallback(RemindmeTaskEditor.this);
		CommonUtils.GpsSetting.GpsStatus = true;
		GpsUseTime = 0;
		GpsTimehandler.post(GpsTime);
	}

	private void setMAP() {
		// map = ((MapFragment) getFragmentManager()
		// .findFragmentById(R.id.map)).getMap();
		map = ((WorkaroundMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		main_scrollview = (ScrollView) findViewById(R.id.main_scrollview);

		((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(
				R.id.map))
				.setListener(new WorkaroundMapFragment.OnTouchListener() {
					@Override
					public void onTouch() {
						main_scrollview
								.requestDisallowInterceptTouchEvent(true);
					}
				});
		map.setMyLocationEnabled(true);
		map.clear();
		LatLng nowLoacation;
		if (gpsManager.LastLocation() != null) {
			nowLoacation = new LatLng(gpsManager.LastLocation().getLatitude(),
					gpsManager.LastLocation().getLongitude());
			map.moveCamera((CameraUpdateFactory.newLatLngZoom(nowLoacation,
					map.getMaxZoomLevel() - 4)));
		} else {
			nowLoacation = new LatLng(23.6978, 120.961);
			map.moveCamera((CameraUpdateFactory.newLatLngZoom(nowLoacation,
					map.getMinZoomLevel() + 7)));
		}
		map.addMarker(new MarkerOptions().title("當前位置").draggable(true)
				.position(nowLoacation));

		map.setOnCameraChangeListener(listener);
	}

	private void setComponents() {
		// 取得Calendar實例
		final Calendar c = Calendar.getInstance();

		// 取得目前日期、時間
		mYear = c.get(Calendar.YEAR);
		mMonth = (c.get(Calendar.MONTH));
		mDay = c.get(Calendar.DAY_OF_MONTH);
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);

		setEditorComponent();
		setLocationPicker();

	}

	// 編輯器主畫面物件
	private void setEditorComponent() {
		// 輸入欄位
		boxTittle = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextViewTittle);
		boxContent = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextViewContent);

		// 區塊： 時間選擇
		buttonSetDate = (Button) findViewById(R.id.buttonSetDate);
		buttonSetDate.setOnClickListener(btnActionEditorButton);
		buttonSetTime = (Button) findViewById(R.id.buttonSetTime);
		buttonSetTime.setOnClickListener(btnActionEditorButton);
		cancelDateTime = (ImageButton) findViewById(R.id.imageButtonCancelDateTime);

		// 區塊： 地點
		buttonSetLocation = (Button) findViewById(R.id.buttonSetLocation);
		buttonSetLocation.setOnClickListener(btnActionEditorButton);
		cancelLocation = (ImageButton) findViewById(R.id.imageButtonCancelLocation);

		// 區塊： 附件
		buttonPickFile = (Button) findViewById(R.id.buttonPickFile);
		buttonTakePhoto = (Button) findViewById(R.id.buttonTakePhoto);
		cancelAttachment = (ImageButton) findViewById(R.id.imageButtonCancelAttachment);

	}

	// 地點選擇器
	private void setLocationPicker() {

		checkBoxIsFixed = (CheckBox) findViewById(R.id.checkBoxIsFixed);
		SearchText = (EditText) findViewById(R.id.SearchText);

		Search = (Button) findViewById(R.id.Search);
		// Search.setOnClickListener(SearchPlace);

		OK = (ImageButton) findViewById(R.id.OK);
		// OK.setOnlickListener(SearchPlace);
	}

	private Button.OnClickListener btnActionEditorButton = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.buttonSetDate:
				// 設定提醒日期
				showDialog(DATE_DIALOG_ID);
				break;
			case R.id.buttonSetTime:
				// 設定提醒時間
				showDialog(TIME_DIALOG_ID);
				break;
			case R.id.buttonSetLocation:
				// 設定提醒時間
				showDialog1(null, null, TIME_DIALOG_ID);
				break;
			}
		}
	};

	// 初始化方法
	private void init(Intent intent) {
		Bundle b = intent.getBundleExtra("b");
		if (b != null) {
			taskId = b.getInt("taskId");
			tittle = b.getString("tittle");
			created = b.getString("created");
			endDate = b.getString("endDate");
			endTime = b.getString("endTime");
			content = b.getString("content");
			isRepeat = b.getString("isRepeat");
			isFixed = b.getString("isFixed");
			locationName = b.getString("locationName");
			coordinate = b.getString("coordinate");
			collaborator = b.getString("collaborator");

			if (endDate != null && endDate.length() > 0) {
				String[] strs = endDate.split("/");
				mYear = Integer.parseInt(strs[0]);
				mMonth = Integer.parseInt(strs[1]) - 1;
				mDay = Integer.parseInt(strs[2]);
			}

			if (endTime != null && endTime.length() > 0) {
				String[] strs = endTime.split(":");
				mHour = Integer.parseInt(strs[0]);
				mMinute = Integer.parseInt(strs[1]);
			}

			EditTextTittle.setText(tittle);

		}

		Toast.makeText(getApplicationContext(),
				taskId + "," + content + "," + endDate + "," + endTime,
				Toast.LENGTH_LONG).show();
	}

	// 顯示對話方塊
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		// 顯示日期對話方塊
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear,
					mMonth - 1, mDay);
			// 顯示時間對話方塊
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute,
					false);
		}
		return null;
	}

	// 設定通知提示
	private void setAlarm(boolean flag) {
		final String BC_ACTION = "com.amaker.ch17.TaskReceiver";
		// 取得AlarmManager實例
		final AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		// 實例化Intent
		Intent intent = new Intent();
		// 設定Intent action屬性
		intent.setAction(BC_ACTION);
		intent.putExtra("msg", content);
		// 實例化PendingIntent
		final PendingIntent pi = PendingIntent.getBroadcast(
				getApplicationContext(), 0, intent, 0);
		// 取得系統時間
		final long time1 = System.currentTimeMillis();
		Calendar c = Calendar.getInstance();
		c.set(mYear, mMonth, mDay, mHour, mMinute);
		long time2 = c.getTimeInMillis();
		if (flag && (time2 - time1) > 0 && on_off == 1) {
			am.set(AlarmManager.RTC_WAKEUP, time2, pi);
		} else {
			am.cancel(pi);
		}
	}

	/*
	 * 設定提示日期對話方塊
	 */
	private void showDialog1(String msg, String tittle, int target) {
		View v = li.inflate(
				R.layout.activity_task_editor_parts_dialog_location, null);
		final TextView editTextTittle = (TextView) v.findViewById(R.id.name);
		final EditText editTextbox = (EditText) v.findViewById(R.id.editTexbox);
		editTextTittle.setText(tittle + target);

		switch (target) {
		case 2:
			switcher = content;
			break;
		default:
			break;
		}

		editTextbox.setText(switcher);

		new AlertDialog.Builder(this).setView(v).setMessage(msg)
				.setCancelable(false)
				.setPositiveButton("確定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						content = editTextbox.getText().toString();

						contentDesc.setText(switcher);
						// locationDesc.setText(switcher);
					}
				}).show();

	}

	// 時間選擇對話方塊
	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMinute = minute;
			if (String.valueOf(mMinute).length() == 1) {
				buttonSetTime.setText(mHour + ":0" + mMinute);
			}else{
			buttonSetTime.setText(mHour + ":" + mMinute);
			}
		}
	};

	// 日期選擇對話方塊
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			buttonSetDate.setText(mYear + "/" + (mMonth + 1) + "/" + mDay);
		}
	};

	// 儲存或修改備忘錄資訊
	@Override
	protected void onPause() {
		super.onPause();
		locationName = null;
		content = null;
	};

	// This is the action bar menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 抓取editor_activity_actionbar.xml內容
		getMenuInflater().inflate(R.menu.editor_activity_actionbar, menu);

		// 啟用actionbar返回首頁箭頭
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle("建立待辦事項");

		// actionAdd
		MenuItem actionAdd = menu.findItem(R.id.action_add);
		actionAdd.setOnMenuItemClickListener(btnActionAddClick);

		// actionCancel
		MenuItem actionCancel = menu.findItem(R.id.action_cancel);
		actionCancel.setOnMenuItemClickListener(btnActionCancelClick);

		return true;
	}

	// actionbar箭頭返回首頁動作
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 * 
	 */
	private MenuItem.OnMenuItemClickListener btnActionAddClick = new MenuItem.OnMenuItemClickListener() {
		@Override
		public boolean onMenuItemClick(MenuItem item) {
			// Toast.makeText(getApplicationContext(),
			// dateDesc.getText()+"2"+timeDesc.getText(),
			// Toast.LENGTH_SHORT).show();
			// if (dateDesc.getText().equals("") &&
			// timeDesc.getText().equals("")
			// && contentDesc.getText().equals("")
			// && SearchText.getText().toString().equals("")) {
			// String[] StringArray = EditTextTittle.getText().toString()
			// .split(" ");
			// try {
			// int i = Integer.parseInt(StringArray[0]);
			// // System.out.println(i);
			// } catch (Exception e) {
			// EditTextTittle.setText("3 " + StringArray[0]);
			// }
			// // String[] QuickTitle =
			// // QuickInput.QuickInput(EditTextTittle.getText().toString());
			// // for (int a=0 ;a<QuickTitle.length;a++) {
			// // if(QuickTitle[a]!=null){
			// // switch (a) {
			// // case 1:
			// // String[] Time =QuickInput.TimeQuickInput(QuickTitle[1]);
			// // try {
			// // mHour = Integer.parseInt(Time[0]);
			// // mMinute = Integer.parseInt(Time[1]);
			// // timeDesc.setText(mHour + ":" + mMinute);
			// //
			// // } catch (Exception e) {
			// // Toast.makeText(getApplicationContext(), e.toString(),
			// // Toast.LENGTH_SHORT).show();
			// // }
			// // break;
			// // case 2:
			// // SearchText.setText(QuickTitle[2]);
			// // break;
			// // case 3:
			// // EditTextTittle.setText(QuickTitle[3]);
			// // break;
			// // case 4:
			// // contentDesc.setText(QuickTitle[4]);
			// // break;
			// // default:
			// // break;
			// // }
			// // }
			// // }
			// }

			if (!isdidSearch && !SearchText.getText().toString().equals("")) {
				// SearchPlace();
				GeocodingAPI LoacationAddress = new GeocodingAPI(
						getApplicationContext(), SearchText.getText()
								.toString());
				if (LoacationAddress.GeocodingApiLatLngGet() != null) {
					Longitude = LoacationAddress.GeocodingApiLatLngGet().longitude;
					Latitude = LoacationAddress.GeocodingApiLatLngGet().latitude;
				}
			}
			if (isDraped && !SearchText.getText().toString().equals("")) {
				Longitude = map.getCameraPosition().target.longitude;
				Latitude = map.getCameraPosition().target.latitude;
				GeocodingAPI LoacationAddress = new GeocodingAPI(
						getApplicationContext(), Latitude + "," + Longitude);
				if (LoacationAddress.GeocodingApiAddressGet() != null) {
					SearchText.setText(LoacationAddress
							.GeocodingApiAddressGet());
				}
			}

			if (checkBoxIsFixed != null) {
				is_Fixed = String.valueOf(checkBoxIsFixed.isChecked());
				endDate = dateDesc.getText().toString();
				// endTime = timeDesc.getText().toString();
				// content = contentDesc.getText().toString();
				tittle = EditTextTittle.getText().toString();
				coordinate = Latitude + "," + Longitude;
				locationName = SearchText.getText().toString();
			}

			mSaveOrUpdate = new SaveOrUpdate(getApplicationContext());
			mSaveOrUpdate.DoTaskEditorAdding(taskId, tittle, endDate, endTime,
					content, locationName, coordinate, "1", is_Fixed, "1");
			finish();
			return true;
		}

	};

	/*
	 * 
	 */
	private MenuItem.OnMenuItemClickListener btnActionCancelClick = new MenuItem.OnMenuItemClickListener() {

		@Override
		public boolean onMenuItemClick(MenuItem item) {
			// TODO Auto-generated method stub

			// Intent EventEditor = new Intent();
			// EventEditor.setClass(getApplication(),RemindmeTaskEditorActivity.class);
			// EventEditor.setClass(getApplication(), IconRequest.class);
			// startActivity(EventEditor);

			// saveOrUpdate();
			finish();

			return false;
		}

	};

	// GPS位置抓到時會更新位置
	@Override
	public void onGPSUpdate(Location location) {
		// TODO Auto-generated method stub
		Double Longitude = location.getLongitude();
		// 緯度
		Double Latitude = location.getLatitude();

		// textView1.setText("經緯度:"+Latitude+","+Longitude);
		// 拿到經緯度後馬上關閉
		// Toast.makeText(getApplicationContext(), "關閉GPS"+location,
		// Toast.LENGTH_LONG).show();

		if (CommonUtils.GpsSetting.GpsStatus) {
			CommonUtils.GpsSetting.GpsStatus = false;
			gpsManager.stopListening();
			gpsManager.setGPSCallback(null);
			gpsManager = null;
		} else {
			CommonUtils.GpsSetting.GpsStatus = false;
		}
		LatLng nowLoacation = new LatLng(Latitude, Longitude);

		map.setMyLocationEnabled(true);

		map.clear();

		map.animateCamera((CameraUpdateFactory.newLatLngZoom(nowLoacation,
				map.getMaxZoomLevel() - 4)));

		map.addMarker(new MarkerOptions().title("目前位置").position(nowLoacation));

		// GeocodingAPI LoacationAddress = new
		// GeocodingAPI(getApplicationContext(),Latitude+","+Longitude);
		// textView2.setText(textView2.getText()+" "+LoacationAddress.GeocodingApiAddressGet());
	}

	private Button.OnClickListener SearchPlace = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			// 宣告GPSManager
			switch (v.getId()) {
			case R.id.OK:
				// textView2.setText(textView2.getText()+"\n"+LoacationAddress.GeocodingApiAddressGet());
				// //獲取地址
				// textView2.setText(textView2.getText()+"\n"+LoacationAddress.GeocodingApiLatLngGet());
				// //獲取經緯度
				if (!isdidSearch
						|| !SearchText.getText().toString()
								.equals(LastTimeSearchName)) {
					SearchPlace();
					isdidSearch = true;
					LastTimeSearchName = SearchText.getText().toString();
				}
				GeocodingAPI LoacationAddress = new GeocodingAPI(
						getApplicationContext(),
						map.getCameraPosition().target.latitude + ","
								+ map.getCameraPosition().target.longitude);
				if (LoacationAddress.GeocodingApiLatLngGet() != null) {
					Longitude = LoacationAddress.GeocodingApiLatLngGet().longitude;
					Latitude = LoacationAddress.GeocodingApiLatLngGet().latitude;
				}
				// locationDesc = LoacationAddress.GeocodingApiAddressGet();
				// Toast.makeText(getApplicationContext(),
				// "獲取經緯度"+map.getCameraPosition().target.latitude+","+map.getCameraPosition().target.longitude+"\n地址:"+locationName,
				// Toast.LENGTH_SHORT).show();

				break;
			case R.id.Search:
				// textView2.setText(map.getMyLocation().toString());
				// //可用網路抓到GPS位置
				if (!SearchText.getText().toString().equals(LastTimeSearchName)) {
					SearchPlace();
					isdidSearch = true;
					LastTimeSearchName = SearchText.getText().toString();
				}
				break;

			default:
				break;
			}
		}
	};

	// 地圖移動時更新指針位置
	private GoogleMap.OnCameraChangeListener listener = new GoogleMap.OnCameraChangeListener() {

		@Override
		public void onCameraChange(CameraPosition position) {
			// TODO Auto-generated method stub
			map.clear();
			LatLng now = new LatLng(position.target.latitude,
					position.target.longitude);
			map.addMarker(new MarkerOptions().title("目的地").position(now));
			if (isdidSearch)
				isDraped = true;
		}
	};

	private Runnable GpsTime = new Runnable() {
		@Override
		public void run() {
			GpsUseTime++;
			// Timeout Sec, 超過TIMEOUT設定時間後,直接設定FLAG使得getCurrentLocation抓取
			// lastlocation.
			if (GpsUseTime > CommonUtils.GpsSetting.TIMEOUT_SEC) {
				if (CommonUtils.GpsSetting.GpsStatus) {
					gpsManager.stopListening();
					gpsManager.startNetWorkListening(getApplicationContext());
					CommonUtils.GpsSetting.GpsStatus = true;
					// Toast.makeText(getApplicationContext(), "關閉GPS",
					// Toast.LENGTH_LONG).show();
				}
			} else {
				GpsTimehandler.postDelayed(this, 1000);
			}
		}
	};

	private void SearchPlace() {
		if (!SearchText.getText().toString().equals("")) {
			GeocodingAPI LoacationAddress2 = null;
			LatLng SearchLocation = null;
			LoacationAddress2 = new GeocodingAPI(getApplicationContext(),
					SearchText.getText().toString());
			// textView2.setText("");
			// locationName=LoacationAddress2.GeocodingApiAddressGet();
			// textView2.setText(textView2.getText()+"\n"+Address);
			SearchLocation = LoacationAddress2.GeocodingApiLatLngGet();
			// textView2.setText(textView2.getText()+"\n"+SearchLocation);
			if (SearchLocation != null) {
				map.animateCamera((CameraUpdateFactory.newLatLngZoom(
						SearchLocation, map.getMaxZoomLevel() - 4)));
				map.addMarker(new MarkerOptions().title("搜尋的位置")
						.snippet(locationName).position(SearchLocation));
			} else {
				Toast.makeText(getApplicationContext(), "查無地點哦,換個詞試試看",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

}

// * CLASS JUST FOR THE CUSTOM ALERT DIALOG
class CustomAlertDialog extends AlertDialog {
	public CustomAlertDialog(Context context) {
		super(context);
	}
}
=======
/**
 * 
 */
package me.iamcxa.remindme.editor;

import java.util.Calendar;
import java.util.Date;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import me.iamcxa.remindme.CommonUtils;
import me.iamcxa.remindme.R;
import me.iamcxa.remindme.CommonUtils.TaskCursor;
import me.iamcxa.remindme.provider.GPSCallback;
import me.iamcxa.remindme.provider.GPSManager;
import me.iamcxa.remindme.provider.GeocodingAPI;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import me.iamcxa.remindme.provider.WorkaroundMapFragment;

/**
 * @author cxa
 * 
 */

public class RemindmeTaskEditor extends FragmentActivity implements GPSCallback {

	/************************************** 變常數區塊開始 *******************************************/
	private static SaveOrUpdate mSaveOrUpdate;

	// 宣告GPS模組
	private static GPSManager gpsManager = null;

	// 宣告pick
	private static GoogleMap map;

	// 顯示日期、時間對話方塊常數
	private static int mYear;
	private static int mMonth;
	private static int mDay;
	private static int mHour;
	private static int mMinute;
	static final int DATE_DIALOG_ID = 0;
	static final int TIME_DIALOG_ID = 1;

	// EditText
	private static EditText EditTextTittle;
	private static EditText SearchText;
	private static MultiAutoCompleteTextView boxTittle;
	private static MultiAutoCompleteTextView boxContent;

	private static TextView dateDesc;
	private static TextView timeDesc;
	private static TextView contentDesc;
	
	// Button
	private static Button Search;
	private static Button buttonPickFile;
	private static Button buttonSetDate;
	private static Button buttonSetTime;
	private static Button buttonSetLocation;
	private static Button buttonTakePhoto;

	private static ImageButton cancelLocation;
	private static ImageButton cancelDateTime;
	private static ImageButton cancelAttachment;

	// ImageButton
	private static ImageButton OK;
	// 是否開啟提醒
	private int on_off = 0;
	// String
	// 保存內容、日期與時間字串
	private static String tittle = null;
	private static String content = null;
	private static String switcher = null;
	private static String endDate = null, endTime = null;
	private static String locationName = null;
	private static String isRepeat = null;
	private static String isFixed = null;
	private static String coordinate = null;
	private static String collaborator = null;
	private static String created = null;
	private static String LastTimeSearchName = "";
	private static String is_Fixed = null;

	// 經緯度
	private static Double Latitude;
	private static Double Longitude;
	private static ScrollView main_scrollview;

	private static CheckBox checkBoxIsFixed;

	private Handler GpsTimehandler = new Handler();
	// gps使用時間
	private static int GpsUseTime = 0;
	// 是否有搜尋過地點
	private static Boolean isdidSearch = false;
	private static Boolean isDraped = false;
	// 備忘錄ID
	private int taskId;
	/************************************** 變常數區塊結束 *******************************************/

	/************************************* Activity週期開始 ******************************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_editor);

		setComponents();
		setGPS();
		// setMAP();

		// 取得Intent
		final Intent intent = getIntent();
		// 設定Uri
		if (intent.getData() == null) {
			intent.setData(CommonUtils.CONTENT_URI);
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		// 初始化列表
		init(getIntent());
	}

	// 儲存或修改備忘錄資訊
	@Override
	protected void onPause() {
		super.onPause();
		locationName = null;
		content = null;
	};

	/************************************* Activity週期結束 ******************************************/

	/************************************* 設定畫面元件開始 ******************************************/
	private void setComponents() {
		// 取得Calendar實例
		final Calendar c = Calendar.getInstance();

		// 取得目前日期、時間
		mYear = c.get(Calendar.YEAR);
		mMonth = (c.get(Calendar.MONTH));
		mDay = c.get(Calendar.DAY_OF_MONTH);
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);

		setEditorComponent();
		setLocationPicker();

	}

	// 編輯器主畫面物件
	private void setEditorComponent() {
		// 輸入欄位
		boxTittle = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextViewTittle);
		boxContent = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextViewContent);

		// 區塊： 時間選擇
		buttonSetDate = (Button) findViewById(R.id.buttonSetDate);
		buttonSetDate.setOnClickListener(btnActionEditorButton);
		buttonSetTime = (Button) findViewById(R.id.buttonSetTime);
		buttonSetTime.setOnClickListener(btnActionEditorButton);
		cancelDateTime = (ImageButton) findViewById(R.id.imageButtonCancelDateTime);

		// 區塊： 地點
		buttonSetLocation = (Button) findViewById(R.id.buttonSetLocation);
		buttonSetLocation.setOnClickListener(btnActionEditorButton);
		cancelLocation = (ImageButton) findViewById(R.id.imageButtonCancelLocation);

		// 區塊： 附件
		buttonPickFile = (Button) findViewById(R.id.buttonPickFile);
		buttonTakePhoto = (Button) findViewById(R.id.buttonTakePhoto);
		cancelAttachment = (ImageButton) findViewById(R.id.imageButtonCancelAttachment);

	}

	// 地點選擇器
	private void setLocationPicker() {

		checkBoxIsFixed = (CheckBox) findViewById(R.id.checkBoxIsFixed);
		SearchText = (EditText) findViewById(R.id.SearchText);

		Search = (Button) findViewById(R.id.Search);
		// Search.setOnClickListener(SearchPlace);

		OK = (ImageButton) findViewById(R.id.OK);
		// OK.setOnlickListener(SearchPlace);
	}
	/************************************* 設定畫面元件結束 ******************************************/

	/************************************** 副程式區塊開始 ******************************************/
	private void setGPS() {
		gpsManager = new GPSManager();
		gpsManager.startGpsListening(getApplicationContext());
		gpsManager.setGPSCallback(RemindmeTaskEditor.this);
		CommonUtils.GpsSetting.GpsStatus = true;
		GpsUseTime = 0;
		GpsTimehandler.post(GpsTime);
	}

	private void setMAP() {
		// map = ((MapFragment) getFragmentManager()
		// .findFragmentById(R.id.map)).getMap();
		map = ((WorkaroundMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		main_scrollview = (ScrollView) findViewById(R.id.main_scrollview);

		((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(
				R.id.map))
				.setListener(new WorkaroundMapFragment.OnTouchListener() {
					@Override
					public void onTouch() {
						main_scrollview
								.requestDisallowInterceptTouchEvent(true);
					}
				});
		map.setMyLocationEnabled(true);
		map.clear();
		LatLng nowLoacation;
		if (gpsManager.LastLocation() != null) {
			nowLoacation = new LatLng(gpsManager.LastLocation().getLatitude(),
					gpsManager.LastLocation().getLongitude());
			map.moveCamera((CameraUpdateFactory.newLatLngZoom(nowLoacation,
					map.getMaxZoomLevel() - 4)));
		} else {
			nowLoacation = new LatLng(23.6978, 120.961);
			map.moveCamera((CameraUpdateFactory.newLatLngZoom(nowLoacation,
					map.getMinZoomLevel() + 7)));
		}
		map.addMarker(new MarkerOptions().title("當前位置").draggable(true)
				.position(nowLoacation));

		map.setOnCameraChangeListener(listener);
	}

	/************************************** 副程式區塊結束 ******************************************/

	private Button.OnClickListener btnActionEditorButton = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.buttonSetDate:
				// 設定提醒日期
				showDialog(DATE_DIALOG_ID);
				break;
			case R.id.buttonSetTime:
				// 設定提醒時間
				showDialog(TIME_DIALOG_ID);
				break;
			case R.id.buttonSetLocation:
				// 設定提醒時間
				showDialogLocationPicker();
				break;
			}
		}
	};

	// 初始化方法
	private void init(Intent intent) {
		Bundle b = intent.getBundleExtra("b");
		if (b != null) {
			taskId = b.getInt("taskId");
			tittle = b.getString("tittle");
			created = b.getString("created");
			endDate = b.getString("endDate");
			endTime = b.getString("endTime");
			content = b.getString("content");
			isRepeat = b.getString("isRepeat");
			isFixed = b.getString("isFixed");
			locationName = b.getString("locationName");
			coordinate = b.getString("coordinate");
			collaborator = b.getString("collaborator");

			if (endDate != null && endDate.length() > 0) {
				String[] strs = endDate.split("/");
				mYear = Integer.parseInt(strs[0]);
				mMonth = Integer.parseInt(strs[1]) - 1;
				mDay = Integer.parseInt(strs[2]);
			}

			if (endTime != null && endTime.length() > 0) {
				String[] strs = endTime.split(":");
				mHour = Integer.parseInt(strs[0]);
				mMinute = Integer.parseInt(strs[1]);
			}

			EditTextTittle.setText(tittle);

		}

		Toast.makeText(getApplicationContext(),
				taskId + "," + content + "," + endDate + "," + endTime,
				Toast.LENGTH_LONG).show();
	}

	// 設定通知提示
	private void setAlarm(boolean flag) {
		// 取得AlarmManager實例
		final AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		// 實例化Intent
		Intent intent = new Intent();
		// 設定Intent action屬性
		intent.setAction(CommonUtils.BC_ACTION);
		intent.putExtra("msg", content);
		// 實例化PendingIntent
		final PendingIntent pi = PendingIntent.getBroadcast(
				getApplicationContext(), 0, intent, 0);
		// 取得系統時間
		final long time1 = System.currentTimeMillis();
		Calendar c = Calendar.getInstance();
		c.set(mYear, mMonth, mDay, mHour, mMinute);
		long time2 = c.getTimeInMillis();
		if (flag && (time2 - time1) > 0 && on_off == 1) {
			am.set(AlarmManager.RTC_WAKEUP, time2, pi);
		} else {
			am.cancel(pi);
		}
	}

	/************************************** 對話方塊開始 *******************************************/
	// 顯示對話方塊
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		// 顯示日期對話方塊
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear,
					mMonth - 1, mDay);
			// 顯示時間對話方塊
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute,
					false);
		}
		return null;
	}

	// 時間選擇對話方塊
	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMinute = minute;
			if (String.valueOf(mMinute).length() == 1) {
				buttonSetTime.setText(mHour + ":0" + mMinute);
			} else {
				buttonSetTime.setText(mHour + ":" + mMinute);
			}
		}
	};

	// 日期選擇對話方塊
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			buttonSetDate.setText(mYear + "/" + (mMonth + 1) + "/" + mDay);
		}
	};
	/************************************** 對話方塊結束 *******************************************/

	/************************************** ActionBar開始 ******************************************/
	// This is the action bar menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 抓取editor_activity_actionbar.xml內容
		getMenuInflater().inflate(R.menu.editor_activity_actionbar, menu);

		// 啟用actionbar返回首頁箭頭
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle("建立待辦事項");

		// actionAdd
		MenuItem actionAdd = menu.findItem(R.id.action_add);
		actionAdd.setOnMenuItemClickListener(btnActionAddClick);

		// actionCancel
		MenuItem actionCancel = menu.findItem(R.id.action_cancel);
		actionCancel.setOnMenuItemClickListener(btnActionCancelClick);

		return true;
	}

	// actionbar箭頭返回首頁動作
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// 按鈕監聽器:btnActionAddClick
	private MenuItem.OnMenuItemClickListener btnActionAddClick = new MenuItem.OnMenuItemClickListener() {
		@Override
		public boolean onMenuItemClick(MenuItem item) {
			// Toast.makeText(getApplicationContext(),
			// dateDesc.getText()+"2"+timeDesc.getText(),
			// Toast.LENGTH_SHORT).show();
			// if (dateDesc.getText().equals("") &&
			// timeDesc.getText().equals("")
			// && contentDesc.getText().equals("")
			// && SearchText.getText().toString().equals("")) {
			// String[] StringArray = EditTextTittle.getText().toString()
			// .split(" ");
			// try {
			// int i = Integer.parseInt(StringArray[0]);
			// // System.out.println(i);
			// } catch (Exception e) {
			// EditTextTittle.setText("3 " + StringArray[0]);
			// }
			// // String[] QuickTitle =
			// // QuickInput.QuickInput(EditTextTittle.getText().toString());
			// // for (int a=0 ;a<QuickTitle.length;a++) {
			// // if(QuickTitle[a]!=null){
			// // switch (a) {
			// // case 1:
			// // String[] Time =QuickInput.TimeQuickInput(QuickTitle[1]);
			// // try {
			// // mHour = Integer.parseInt(Time[0]);
			// // mMinute = Integer.parseInt(Time[1]);
			// // timeDesc.setText(mHour + ":" + mMinute);
			// //
			// // } catch (Exception e) {
			// // Toast.makeText(getApplicationContext(), e.toString(),
			// // Toast.LENGTH_SHORT).show();
			// // }
			// // break;
			// // case 2:
			// // SearchText.setText(QuickTitle[2]);
			// // break;
			// // case 3:
			// // EditTextTittle.setText(QuickTitle[3]);
			// // break;
			// // case 4:
			// // contentDesc.setText(QuickTitle[4]);
			// // break;
			// // default:
			// // break;
			// // }
			// // }
			// // }
			// }

			if (!isdidSearch && !SearchText.getText().toString().equals("")) {
				// SearchPlace();
				GeocodingAPI LoacationAddress = new GeocodingAPI(
						getApplicationContext(), SearchText.getText()
								.toString());
				if (LoacationAddress.GeocodingApiLatLngGet() != null) {
					Longitude = LoacationAddress.GeocodingApiLatLngGet().longitude;
					Latitude = LoacationAddress.GeocodingApiLatLngGet().latitude;
				}
			}
			if (isDraped && !SearchText.getText().toString().equals("")) {
				Longitude = map.getCameraPosition().target.longitude;
				Latitude = map.getCameraPosition().target.latitude;
				GeocodingAPI LoacationAddress = new GeocodingAPI(
						getApplicationContext(), Latitude + "," + Longitude);
				if (LoacationAddress.GeocodingApiAddressGet() != null) {
					SearchText.setText(LoacationAddress
							.GeocodingApiAddressGet());
				}
			}

			if (checkBoxIsFixed != null) {
				is_Fixed = String.valueOf(checkBoxIsFixed.isChecked());
				endDate = dateDesc.getText().toString();
				// endTime = timeDesc.getText().toString();
				// content = contentDesc.getText().toString();
				tittle = EditTextTittle.getText().toString();
				coordinate = Latitude + "," + Longitude;
				locationName = SearchText.getText().toString();
			}

			mSaveOrUpdate = new SaveOrUpdate(getApplicationContext());
			mSaveOrUpdate.DoTaskEditorAdding(taskId, tittle, endDate, endTime,
					content, locationName, coordinate, "1", is_Fixed, "1");
			finish();
			return true;
		}

	};

	// 按鈕監聽器:btnActionCancelClick
	private MenuItem.OnMenuItemClickListener btnActionCancelClick = new MenuItem.OnMenuItemClickListener() {

		@Override
		public boolean onMenuItemClick(MenuItem item) {
			// TODO Auto-generated method stub

			// Intent EventEditor = new Intent();
			// EventEditor.setClass(getApplication(),RemindmeTaskEditorActivity.class);
			// EventEditor.setClass(getApplication(), IconRequest.class);
			// startActivity(EventEditor);

			// saveOrUpdate();
			finish();

			return false;
		}

	};
	/************************************** ActionBar結束 ******************************************/

	/************************************** 地點選擇器開始 ******************************************/
	// GPS位置抓到時會更新位置
	@Override
	public void onGPSUpdate(Location location) {
		// TODO Auto-generated method stub
		Double Longitude = location.getLongitude();
		// 緯度
		Double Latitude = location.getLatitude();

		// textView1.setText("經緯度:"+Latitude+","+Longitude);
		// 拿到經緯度後馬上關閉
		// Toast.makeText(getApplicationContext(), "關閉GPS"+location,
		// Toast.LENGTH_LONG).show();

		if (CommonUtils.GpsSetting.GpsStatus) {
			CommonUtils.GpsSetting.GpsStatus = false;
			gpsManager.stopListening();
			gpsManager.setGPSCallback(null);
			gpsManager = null;
		} else {
			CommonUtils.GpsSetting.GpsStatus = false;
		}
		LatLng nowLoacation = new LatLng(Latitude, Longitude);

		// map.setMyLocationEnabled(true);

		map.clear();

		map.animateCamera((CameraUpdateFactory.newLatLngZoom(nowLoacation,
				map.getMaxZoomLevel() - 4)));

		map.addMarker(new MarkerOptions().title("目前位置").position(nowLoacation));

		// GeocodingAPI LoacationAddress = new
		// GeocodingAPI(getApplicationContext(),Latitude+","+Longitude);
		// textView2.setText(textView2.getText()+" "+LoacationAddress.GeocodingApiAddressGet());
	}

	// 按鈕按下監聽器
	private Button.OnClickListener SearchPlace = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			// 宣告GPSManager
			switch (v.getId()) {
			case R.id.OK:
				// textView2.setText(textView2.getText()+"\n"+LoacationAddress.GeocodingApiAddressGet());
				// //獲取地址
				// textView2.setText(textView2.getText()+"\n"+LoacationAddress.GeocodingApiLatLngGet());
				// //獲取經緯度
				if (!isdidSearch
						|| !SearchText.getText().toString()
								.equals(LastTimeSearchName)) {
					SearchPlace();
					isdidSearch = true;
					LastTimeSearchName = SearchText.getText().toString();
				}
				GeocodingAPI LoacationAddress = new GeocodingAPI(
						getApplicationContext(),
						map.getCameraPosition().target.latitude + ","
								+ map.getCameraPosition().target.longitude);
				if (LoacationAddress.GeocodingApiLatLngGet() != null) {
					Longitude = LoacationAddress.GeocodingApiLatLngGet().longitude;
					Latitude = LoacationAddress.GeocodingApiLatLngGet().latitude;
				}
				// locationDesc = LoacationAddress.GeocodingApiAddressGet();
				// Toast.makeText(getApplicationContext(),
				// "獲取經緯度"+map.getCameraPosition().target.latitude+","+map.getCameraPosition().target.longitude+"\n地址:"+locationName,
				// Toast.LENGTH_SHORT).show();

				break;
			case R.id.Search:
				// textView2.setText(map.getMyLocation().toString());
				// //可用網路抓到GPS位置
				if (!SearchText.getText().toString().equals(LastTimeSearchName)) {
					SearchPlace();
					isdidSearch = true;
					LastTimeSearchName = SearchText.getText().toString();
				}
				break;

			default:
				break;
			}
		}
	};

	// 地圖移動時更新指針位置
	private GoogleMap.OnCameraChangeListener listener = new GoogleMap.OnCameraChangeListener() {

		@Override
		public void onCameraChange(CameraPosition position) {
			// TODO Auto-generated method stub
			map.clear();
			LatLng now = new LatLng(position.target.latitude,
					position.target.longitude);
			map.addMarker(new MarkerOptions().title("目的地").position(now));
			if (isdidSearch)
				isDraped = true;
		}
	};

	// gps事件
	private Runnable GpsTime = new Runnable() {
		@Override
		public void run() {
			GpsUseTime++;
			// Timeout Sec, 超過TIMEOUT設定時間後,直接設定FLAG使得getCurrentLocation抓取
			// lastlocation.
			if (GpsUseTime > CommonUtils.GpsSetting.TIMEOUT_SEC) {
				if (CommonUtils.GpsSetting.GpsStatus) {
					gpsManager.stopListening();
					gpsManager.startNetWorkListening(getApplicationContext());
					CommonUtils.GpsSetting.GpsStatus = true;
					// Toast.makeText(getApplicationContext(), "關閉GPS",
					// Toast.LENGTH_LONG).show();
				}
			} else {
				GpsTimehandler.postDelayed(this, 1000);
			}
		}
	};

	// 搜尋地點
	private void SearchPlace() {
		if (!SearchText.getText().toString().equals("")) {
			GeocodingAPI LoacationAddress2 = null;
			LatLng SearchLocation = null;
			LoacationAddress2 = new GeocodingAPI(getApplicationContext(),
					SearchText.getText().toString());
			// textView2.setText("");
			// locationName=LoacationAddress2.GeocodingApiAddressGet();
			// textView2.setText(textView2.getText()+"\n"+Address);
			SearchLocation = LoacationAddress2.GeocodingApiLatLngGet();
			// textView2.setText(textView2.getText()+"\n"+SearchLocation);
			if (SearchLocation != null) {
				map.animateCamera((CameraUpdateFactory.newLatLngZoom(
						SearchLocation, map.getMaxZoomLevel() - 4)));
				map.addMarker(new MarkerOptions().title("搜尋的位置")
						.snippet(locationName).position(SearchLocation));
			} else {
				Toast.makeText(getApplicationContext(), "查無地點哦,換個詞試試看",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	// 地圖對話方塊
	private void showDialogLocationPicker() {
		// 存取佈局實例
		LayoutInflater li = getLayoutInflater();

		View v = li.inflate(
				R.layout.activity_task_editor_parts_dialog_location, null);

		new AlertDialog.Builder(this).setView(v).setMessage("jj")
				.setCancelable(false)
				.setPositiveButton("確定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

					}
				}).show();

	}
	/************************************** 地點選擇器結束 ******************************************/
}

// * CLASS JUST FOR THE CUSTOM ALERT DIALOG
class CustomAlertDialog extends AlertDialog {
	public CustomAlertDialog(Context context) {
		super(context);
	}
}
>>>>>>> 90f650a3bd13712ec20e73f59bc083ca452f4916
