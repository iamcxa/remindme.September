package me.iamcxa.remindme.editor;

import me.iamcxa.remindme.R;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class TaskEditorTab extends Activity {
    /*
	 * 
	 */





	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_editor);

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
	
	
	
	
	
	private void setupViewComponent() {
	        final ActionBar actBar = getActionBar();
	        actBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	        
	//        Fragment fragMarriSug = new RemindmeTaskEditor();
	//        actBar.addTab(actBar.newTab()
	//        		.setText("婚姻建議")
	//        		.setIcon(getResources().getDrawable(android.R.drawable.ic_lock_idle_alarm))
	//        		.setTabListener(new MyTabListener(fragMarriSug)));
	//
	//        Fragment fragGame = new GameFragment();
	//        actBar.addTab(actBar.newTab()
	//        		.setText("電腦猜拳遊戲")
	//        		.setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_alert))
	//        		.setTabListener(new MyTabListener(fragGame)));
	//
	//        Fragment fragVideo = new VideoFragment();
	//        actBar.addTab(actBar.newTab()
	//        		.setText("播放影片")
	//        		.setIcon(getResources().getDrawable(android.R.drawable.ic_media_play))
	//        		.setTabListener(new MyTabListener(fragVideo)));
	//
	//        Fragment fragList = new ExpList();
	//        actBar.addTab(actBar.newTab()
	//        		.setText("ExpandableListView")
	//        		.setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_info))
	//        		.setTabListener(new MyTabListener(fragList)));
	    }



	/*
	 * 
	 */
	private MenuItem.OnMenuItemClickListener btnActionAddClick = new MenuItem.OnMenuItemClickListener() {
		@Override
		public boolean onMenuItemClick(MenuItem item) {
			 Toast.makeText(getApplicationContext(),"btnActionAddClick",Toast.LENGTH_SHORT).show();
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
	
//			if (!EditorVar.isdidSearch && !SearchText.getText().toString().equals("")) {
//				// SearchPlace();
//				GeocodingAPI LoacationAddress = new GeocodingAPI(
//						getApplicationContext(), SearchText.getText()
//						.toString());
//				if (LoacationAddress.GeocodingApiLatLngGet() != null) {
//					EditorVar.Longitude = LoacationAddress.GeocodingApiLatLngGet().longitude;
//					EditorVar.Latitude = LoacationAddress.GeocodingApiLatLngGet().latitude;
//				}
//			}
//			if (EditorVar.isDraped && !SearchText.getText().toString().equals("")) {
//				EditorVar.Longitude = map.getCameraPosition().target.longitude;
//				EditorVar.Latitude = map.getCameraPosition().target.latitude;
//				GeocodingAPI LoacationAddress = new GeocodingAPI(
//						getApplicationContext(), EditorVar.Latitude + "," + EditorVar.Longitude);
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
			//			values.put(TaskCursor.KeyColumns.EndDate, dateDesc.getText()
			//					.toString());
			//			// save the selected value of time
			//			values.put(TaskCursor.KeyColumns.StartTime, curDate.toString());
			//			values.put(TaskCursor.KeyColumns.EndTime, timeDesc.getText()
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
//				EditorVar.is_Fixed = String.valueOf(checkBoxIsFixed.isChecked());
//				EditorVar.endDate = dateDesc.getText().toString();
//				//endTime = timeDesc.getText().toString();
//				//content = contentDesc.getText().toString();
//				EditorVar.tittle = EditTextTittle.getText().toString();
//				EditorVar.coordinate = EditorVar.Latitude + "," + EditorVar.Longitude;
//				EditorVar.locationName=SearchText.getText()
//						.toString();
//			}
//	
//			mSaveOrUpdate = new SaveOrUpdate(getApplicationContext());
//			mSaveOrUpdate.DoTaskEditorAdding(EditorVar.taskId, EditorVar.tittle, EditorVar.endDate, EditorVar.endTime,
//					EditorVar.content, EditorVar.locationName, EditorVar.coordinate, "1", EditorVar.is_Fixed, "1");
//			finish();
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
	

			 Toast.makeText(getApplicationContext(),"btnActionAddClick",Toast.LENGTH_SHORT).show();
			// Intent EventEditor = new Intent();
			// EventEditor.setClass(getApplication(),RemindmeTaskEditorActivity.class);
			// EventEditor.setClass(getApplication(), IconRequest.class);
			// startActivity(EventEditor);
	
			// saveOrUpdate();
			finish();
	
			return false;
		}
	
	};
    

}