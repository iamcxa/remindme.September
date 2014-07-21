package me.iamcxa.remindme.editor;

import common.CommonVar;
import common.MyTabListener;

import me.iamcxa.remindme.R;
import me.iamcxa.remindme.database.ColumnTask;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class TaskEditorTab extends FragmentActivity  {

	static CommonEditorVar mEditorVar=CommonEditorVar.GetInstance();
	static DbAction_SaveOrUpdate mSaveOrUpdate;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_editor_tab);
		setupViewComponent();
		//init(this.getIntent());
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
		Bundle b = intent.getBundleExtra(CommonVar.BundleName);
		if (b != null) {
			//參照 底部之TaskFieldContents/RemindmeVar.class等處, 確保變數欄位與順序都相同
			mEditorVar.Task.setTaskId(b.getInt(ColumnTask.KEY._id));
			mEditorVar.Task.setTitle(b.getString(ColumnTask.KEY.title));
			mEditorVar.Task.setContent(b.getString(ColumnTask.KEY.content));
			mEditorVar.Task.setCreated(b.getString(ColumnTask.KEY.created));
			mEditorVar.Task.setDueDateString(b.getString(ColumnTask.KEY.due_date_millis));
			mEditorVar.Task.setDueDateString(b.getString(ColumnTask.KEY.due_date_string));

			mEditorVar.TaskType.setCategory(b.getString(ColumnTask.KEY.category_id));
			mEditorVar.TaskType.setPriority(b.getInt(ColumnTask.KEY.priority));
			mEditorVar.TaskType.setTag(b.getString(ColumnTask.KEY.tag_id));

			//TaskEditorMain.setTaskTitle(mEditorVar.Task.getTitle());
			//TaskEditorMain.setTaskDueDate(mEditorVar.Task.getDueDate());


			//			if (b.getString("dueDate") != null && b.getString("dueDate").length() > 0) {
			//				String[] dateStr = mEditorVar.Task.getDueDate().split("/");
			//				mEditorVar.TaskDate.setmYear(Integer.parseInt(dateStr[0]));
			//				mEditorVar.TaskDate.setmMonth(Integer.parseInt(dateStr[1]) - 1);
			//				mEditorVar.TaskDate.setmDay(Integer.parseInt(dateStr[2]));
			//			}
			//
			//			if (b.getString("alertTime") != null && b.getString("alertTime").length() > 0) {
			//				String[] timeStr = mEditorVar.TaskAlert.getAlertTime().split(":");
			//				mEditorVar.TaskDate.setmHour (Integer.parseInt(timeStr[0]));
			//				mEditorVar.TaskDate.setmMinute(Integer.parseInt(timeStr[1]));
			//			}

		}
	}



	//設定tab
	private void setupViewComponent() {
		final ActionBar actBar = getActionBar();
		actBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		

		Fragment fragMarriSug = TaskEditorMain.newInstance();
		actBar.addTab(actBar.newTab()
				.setText("")
				.setIcon(getResources().getDrawable(R.drawable.tear_of_calendar))
				.setTabListener(new MyTabListener(fragMarriSug)));

		Fragment fragGame =  TaskEditorLocation.newInstance();
		actBar.addTab(actBar.newTab()
				.setText("")
				.setIcon(getResources().getDrawable(R.drawable.map_marker))
				.setTabListener(new MyTabListener(fragGame)));

		Fragment fragVideo = new TaskEditorMain();
		actBar.addTab(actBar.newTab()
				.setText("")
				.setIcon(getResources().getDrawable(android.R.drawable.ic_media_play))
				.setTabListener(new MyTabListener(fragVideo)));

	}




	// 按鈕監聽
	private MenuItem.OnMenuItemClickListener btnClickListener = new MenuItem.OnMenuItemClickListener() {

		@Override
		public boolean onMenuItemClick(MenuItem item) {
			// TODO Auto-generated method stub
			String itemName=String.valueOf(item.getTitle());
			//Toast.makeText(getApplicationContext(),item.getTitle(),Toast.LENGTH_SHORT).show();
			if (itemName.contentEquals( "action_add")){

				btnActionAdd();

			}else if (itemName.contentEquals( "action_add")) {
				//btnActionCancel();//暫時取消此功能
			}


			return false;
		}

	};



	private void btnActionAdd(){
		//檢查title是否為空
		boolean isEmpty=(TaskEditorMain.getTaskTitle().contentEquals("null"));
		if(!isEmpty){
			try {

				mSaveOrUpdate=new DbAction_SaveOrUpdate(getApplicationContext());
				finish();

			} catch (Exception e) {
				Toast.makeText(getApplicationContext(),"ERROR:"+e.toString() , Toast.LENGTH_SHORT).show();
			}
		}else {
			String EmptyMsg=getString(R.string.TaskEditor_Title_Is_Empty);
			Toast.makeText(getApplicationContext(),EmptyMsg , Toast.LENGTH_SHORT).show();
		}

	}


}
