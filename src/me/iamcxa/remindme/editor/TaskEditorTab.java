package me.iamcxa.remindme.editor;

import me.iamcxa.remindme.R;
import android.R.string;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class TaskEditorTab extends Activity {


	private static EditorVar mEditorVar ;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_editor_tab);

		setupViewComponent();
	}
	// This is the action bar menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 抓取editor_activity_actionbar.xml內容
		getMenuInflater().inflate(R.menu.editor_activity_actionbar, menu);

		// 啟用action bar返回首頁箭頭
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle("建立待辦事項");

		// actionAdd
		MenuItem actionAdd = menu.findItem(R.id.action_add);
		actionAdd.setOnMenuItemClickListener(btnClickListener);

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

	//  由資料庫初始化變數
	public static void init(Intent intent) {
		Bundle b = intent.getBundleExtra("b");
		if (b != null) {
			mEditorVar.Editor.taskId = b.getInt("taskId");
			mEditorVar.Editor.tittle = b.getString("tittle");
			mEditorVar.Editor.created = b.getString("created");
			mEditorVar.Editor.dueDate = b.getString("dueDate");
			mEditorVar.Editor.alertTime = b.getString("alertTime");
			mEditorVar.Editor.content = b.getString("content");
			mEditorVar.Editor.alertCycle = b.getString("alertCycle");
			mEditorVar.Editor.locationName = b.getString("locationName");
			mEditorVar.Editor.coordinate = b.getString("coordinate");

			if (mEditorVar.Editor.dueDate != null && mEditorVar.Editor.dueDate.length() > 0) {
				String[] strs = mEditorVar.Editor.dueDate.split("/");
				mEditorVar.Date.setmYear(Integer.parseInt(strs[0]));
				mEditorVar.Date.setmMonth (Integer.parseInt(strs[1]) - 1);
				mEditorVar.Date.setmDay ( Integer.parseInt(strs[2]));
			}

			if (mEditorVar.Editor.alertTime != null && mEditorVar.Editor.alertTime.length() > 0) {
				String[] strs = mEditorVar.Editor.alertTime.split(":");
				mEditorVar.Date.setmHour (Integer.parseInt(strs[0]));
				mEditorVar.Date.setmMinute(Integer.parseInt(strs[1]));
			}
		}
	}



	//設定tab
	private void setupViewComponent() {
		final ActionBar actBar = getActionBar();
		actBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Fragment fragMarriSug = new TaskEditorMain();
		actBar.addTab(actBar.newTab()
				//.setText("任務")
				.setIcon(getResources().getDrawable(R.drawable.tear_of_calendar))
				.setTabListener(new MyTabListener(fragMarriSug)));

		Fragment fragGame = new TaskEditorLocation();
		actBar.addTab(actBar.newTab()
				//.setText("位置")
				.setIcon(getResources().getDrawable(R.drawable.map_marker))
				.setTabListener(new MyTabListener(fragGame)));

		Fragment fragVideo = new TaskEditorMain();
		actBar.addTab(actBar.newTab()
				//.setText("播放影片")
				.setIcon(getResources().getDrawable(android.R.drawable.ic_media_play))
				.setTabListener(new MyTabListener(fragVideo)));
	}




	/*
	 * 
	 */
	private MenuItem.OnMenuItemClickListener btnClickListener = new MenuItem.OnMenuItemClickListener() {

		@Override
		public boolean onMenuItemClick(MenuItem item) {
			// TODO Auto-generated method stub
			String itemName=String.valueOf(item.getTitle());
			Toast.makeText(getApplicationContext(),item.getTitle(),Toast.LENGTH_SHORT).show();
			if (itemName.contentEquals( "action_add")){
				btnActionAdd();

			}else if (itemName.contentEquals( "action_add")) {
				btnActionCancel();
			}


			return false;
		}

	};

	private void btnActionAdd(){
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

		//			if (!mEditorVar.isdidSearch && !SearchText.getText().toString().equals("")) {
		//				// SearchPlace();
		//				GeocodingAPI LoacationAddress = new GeocodingAPI(
		//						getApplicationContext(), SearchText.getText()
		//						.toString());
		//				if (LoacationAddress.GeocodingApiLatLngGet() != null) {
		//					mEditorVar.Longitude = LoacationAddress.GeocodingApiLatLngGet().longitude;
		//					mEditorVar.Latitude = LoacationAddress.GeocodingApiLatLngGet().latitude;
		//				}
		//			}
		//			if (mEditorVar.isDraped && !SearchText.getText().toString().equals("")) {
		//				mEditorVar.Longitude = map.getCameraPosition().target.longitude;
		//				mEditorVar.Latitude = map.getCameraPosition().target.latitude;
		//				GeocodingAPI LoacationAddress = new GeocodingAPI(
		//						getApplicationContext(), mEditorVar.Latitude + "," + mEditorVar.Longitude);
		//				if (LoacationAddress.GeocodingApiAddressGet() != null) {
		//					SearchText.setText(LoacationAddress
		//							.GeocodingApiAddressGet());
		//				}
		//			}
		//	

		//			// 存入標題
		//			values.put(TaskCursor.KeyColumns.Tittle, EditTextTittle.getText()
		//					.toString());
		//			// 存入日期
		//			values.put(TaskCursor.KeyColumns.StartDate, curDate.toString());
		//			values.put(TaskCursor.KeyColumns.Editor.dueDate, dateDesc.getText()
		//					.toString());
		//			// save the selected value of time
		//			values.put(TaskCursor.KeyColumns.StartTime, curDate.toString());
		//			values.put(TaskCursor.KeyColumns.Editor.alertTime, timeDesc.getText()
		//					.toString());
		//			// save contents
		//			values.put(TaskCursor.KeyColumns.CONTENT, contentDesc.getText()
		//					.toString());
		//			// save the name string of location
		//			values.put(TaskCursor.KeyColumns.LocationName, SearchText.getText()
		//					.toString());
		//			values.put(TaskCursor.KeyColumns.Coordinate, Latitude + ","
		//					+ Longitude);
		//			values.put(TaskCursor.KeyColumns.Priority, 1000);

		//			if (checkBoxIsFixed != null) {
		//				mEditorVar.is_Fixed = String.valueOf(checkBoxIsFixed.isChecked());
		//				mEditorVar.Editor.dueDate = dateDesc.getText().toString();
		//				//Editor.alertTime = timeDesc.getText().toString();
		//				//content = contentDesc.getText().toString();
		//				mEditorVar.tittle = EditTextTittle.getText().toString();
		//				mEditorVar.coordinate = mEditorVar.Latitude + "," + mEditorVar.Longitude;
		//				mEditorVar.locationName=SearchText.getText()
		//						.toString();
		//			}
		//	
		//			mSaveOrUpdate = new SaveOrUpdate(getApplicationContext());
		//			mSaveOrUpdate.DoTaskEditorAdding(mEditorVar.taskId, mEditorVar.tittle, mEditorVar.Editor.dueDate, mEditorVar.Editor.alertTime,
		//					mEditorVar.content, mEditorVar.locationName, mEditorVar.coordinate, "1", mEditorVar.is_Fixed, "1");
					finish();
	}

	private void btnActionCancel(){
		// Intent EventEditor = new Intent();
		// EventEditor.setClass(getApplication(),RemindmeTaskEditorActivity.class);
		// EventEditor.setClass(getApplication(), IconRequest.class);
		// startActivity(EventEditor);

		// saveOrUpdate();
		finish();
	}

}