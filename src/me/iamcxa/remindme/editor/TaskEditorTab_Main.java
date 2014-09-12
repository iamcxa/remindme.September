package me.iamcxa.remindme.editor;


import common.MyCalendar;
import common.MyDebug;
import common.CommonVar;

import me.iamcxa.remindme.R;
import me.iamcxa.remindme.database.ColumnTask;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;

public class TaskEditorTab_Main extends Fragment  {

	private static MultiAutoCompleteTextView taskTitle; 	//任務標題
	private static EditText taskDueDate;					//任務到期日
	private static Spinner taskCategory;					//任務類別
	private static Spinner taskPriority;					//任務優先
	private static Spinner taskProject;					//任務優先
	private static Spinner taskTag;					//任務優先
	private ImageButton taskBtnDueDate;
	private Handler mHandler;
	public static TaskEditorTab_Main newInstance() {
		TaskEditorTab_Main fragment = new TaskEditorTab_Main();
		return fragment;
	}

	private static  CommonEditorVar mEditorVar=CommonEditorVar.GetInstance();	
	private Runnable mShowContentRunnable = new Runnable() {

		@Override
		public void run() {
			setupViewComponent();
			setupStringArray();
			init(getActivity().getIntent());
		}

	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.activity_task_editor_tab_main, container,false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		obtainData();
		if(savedInstanceState==null){

		}
	}

	private void setupStringArray(){
		String[] BasicStringArray =
				getResources().getStringArray(R.array.Array_Task_Editor_Date_Basic_Meaning_String);
		String[] RepeatStringArray =
				getResources().getStringArray(R.array.Array_Task_Editor_Date_Repeat_Meaning_String);
		CommonVar.TASKEDITOR_DUEDATE_BASIC_STRING_ARRAY=BasicStringArray;
		CommonVar.TASKEDITOR_DUEDATE_EXTRA_STRING_ARRAY=RepeatStringArray;
	}

	private void setupViewComponent(){
		//任務標題輸入框
		taskTitle =(MultiAutoCompleteTextView)getView().
				findViewById(R.id.multiAutoCompleteTextViewTitle);
		taskTitle.setHint(getString(R.string.Textview_Title_Hint));
		//taskTitle.setText(mEditorVar.Task.getTitle());

		//任務期限輸入框
		taskDueDate =(EditText)getView().findViewById(R.id.editTextDueDate);
		taskDueDate.setHint(getString(R.string.Textview_DueDate_Hint));
		//taskDueDate.setText(mEditorVar.Task.getDueDate());

		//任務期限選擇按鈕
		taskBtnDueDate=(ImageButton)getView().findViewById(R.id.imageButtonResetDate);
		//OnClickListener btnClcikListener;
		taskBtnDueDate.setOnClickListener(btnClcikListener);
		
		

		// spinner - 任務類別
		taskCategory=(Spinner)getView().findViewById(R.id.spinnerCategory);
		taskCategory.setPrompt(getString(R.string.spinner_category_prompt));
		

		// spinner - 任務優先
		taskPriority=(Spinner)getView().findViewById(R.id.spinnerPriority);
		taskPriority.setPrompt(getString(R.string.spinner_priority_prompt));
		
		// spinner - 專案
		taskProject=(Spinner)getView().findViewById(R.id.spinnerProject);
		taskProject.setPrompt(getString(R.string.spinner_project_prompt));

		// spinner - 任務優先
		taskPriority=(Spinner)getView().findViewById(R.id.spinnerPriority);
		taskPriority.setPrompt(getText(R.string.spinner_priority_prompt));

		// spinner - 任務標籤
		taskTag=(Spinner)getView().findViewById(R.id.spinnerTag);
		taskTag.setPrompt(getText(R.string.spinner_tag_prompt));


	}

	//  由資料庫初始化變數
	public static void init(Intent intent) {
		Bundle b = intent.getBundleExtra(CommonVar.BundleName);
		if (b != null) {
			//參照 底部之TaskFieldContents/RemindmeVar.class等處, 確保變數欄位與順序都相同
			mEditorVar.Task.setTaskId(b.getInt(ColumnTask.KEY._id));
			//			mEditorVar.Task.setTitle(b.getString(TaskCursor.KEY.Title));
			//			mEditorVar.Task.setContent(b.getString(TaskCursor.KEY.CONTENT));
			//			mEditorVar.Task.setCreated(b.getString(TaskCursor.KEY.CREATED));
			//			mEditorVar.Task.setDueDate(b.getString(TaskCursor.KEY.DUE_DATE_TIME));
			//			mEditorVar.Task.setDueDateString(b.getString(TaskCursor.KEY.DUE_DATE_STRING));
			//			mEditorVar.TaskAlert.setAlertInterval(b.getString(TaskCursor.KEY.ALERT_Interval));
			//			mEditorVar.TaskAlert.setAlertTime(b.getString(TaskCursor.KEY.ALERT_TIME));
			//			mEditorVar.TaskLocation.setLocationName(b.getString(TaskCursor.KEY.LOCATION_NAME));
			//			mEditorVar.TaskLocation.setCoordinate(b.getString(TaskCursor.KEY.COORDINATE));;
			//			mEditorVar.TaskLocation.setDistance(Double.valueOf((TaskCursor.KEY.DISTANCE)));
			//			mEditorVar.TaskType.setCategory(b.getString(TaskCursor.KEY.CATEGORY));
			//			mEditorVar.TaskType.setPriority(b.getInt(TaskCursor.KEY.PRIORITY));
			//			mEditorVar.TaskType.setTag(b.getString(TaskCursor.KEY.TAG));

			TaskEditorTab_Main.setTaskTitle(b.getString(ColumnTask.KEY.title));
			TaskEditorTab_Main.setTaskDueDate(b.getString(ColumnTask.KEY.due_date_string));


		}
	}


	private  OnClickListener btnClcikListener=new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.imageButtonResetDate:
				ShowTaskDueDateSelectMenu();
				break;

			default:
				break;
			}
		}

	};

	@SuppressLint("InflateParams")
	private TaskEditorTab_Main ShowTaskDueDateSelectMenu() {
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View mview = inflater.inflate(
				R.layout.activity_task_editor_parts_dialog_duedate,
				null);

		new AlertDialog.Builder(getActivity())
		.setTitle("請選擇...")
		.setView(mview)
		.setItems(R.array.Array_TaskEditor_btnTaskDueDate_String,
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,
					int which) {

				switch (which) {
				case 0:// 今天
					taskDueDate.setText(
							MyCalendar.getTodayString(0));
					break;
				case 1:// 明天
					taskDueDate.setText(
							MyCalendar.getTodayString(1));
					break;
				case 2:// 下週
					taskDueDate.setText(
							MyCalendar.getTodayString(7));
					break;
				case 3:// 一個月內
					taskDueDate.setText(
							MyCalendar.getTodayString(30));
					break;
				case 4:// 選擇日期

					break;
				case 5:// 說明

					break;
				}
			}
		}).show();
		return this;
	}

	private void obtainData() {
		// Show indeterminate progress
		mHandler = new Handler();
		mHandler.postDelayed(mShowContentRunnable, 5);
	}
	//-----------------TaskTitle------------------//
	// 確保 Title 欄位不為空
	public static String getTaskTitle() {
		String TaskTitleString = "null";
		// 如果TaskTitle欄位不為空則放入使用者輸入數值
		if (!(taskTitle.getText().toString().isEmpty())){
			TaskTitleString= taskTitle.getText().toString().trim();
		}
		MyDebug.MakeLog(0,"getTaskTitle:"+ TaskTitleString+",TaskTitle.len="+TaskTitleString.length());
		return TaskTitleString;
	}
	public static void setTaskTitle(String taskTitle) {
		TaskEditorTab_Main.taskTitle.setText(taskTitle);
	}


	//-----------------TaskDueDate------------------//
	// 確認  DueDate 欄位數值
	public static String getTaskDueDate() {
		String taskDueDateString="null";
		// 如果TaskTitle欄位不為空則放入使用者輸入數值, 然後進行下一步判斷
		if (!(taskDueDate.getText().toString().isEmpty())){
			taskDueDateString= taskDueDate.getText().toString().trim();
		}
		return taskDueDateString;
	}	
	public static void setTaskDueDate(String taskDueDate) {
		TaskEditorTab_Main.taskDueDate.setText(taskDueDate);
	}


	//-----------------TaskCategory------------------//
	public static Spinner getTaskCategory() {
		return taskCategory;
	}
	public static void setTaskCategory(Spinner taskCategory) {
		TaskEditorTab_Main.taskCategory = taskCategory;
	}


	//-----------------TaskPriority------------------//
	public static Spinner getTaskPriority() {
		return taskPriority;
	}
	public static void setTaskPriority(Spinner taskPriority) {
		TaskEditorTab_Main.taskPriority = taskPriority;
	}







}
