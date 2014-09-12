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

	private static MultiAutoCompleteTextView taskTitle; 	//���ȼ��D
	private static EditText taskDueDate;					//���Ȩ����
	private static Spinner taskCategory;					//�������O
	private static Spinner taskPriority;					//�����u��
	private static Spinner taskProject;					//�����u��
	private static Spinner taskTag;					//�����u��
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
		//���ȼ��D��J��
		taskTitle =(MultiAutoCompleteTextView)getView().
				findViewById(R.id.multiAutoCompleteTextViewTitle);
		taskTitle.setHint(getString(R.string.Textview_Title_Hint));
		//taskTitle.setText(mEditorVar.Task.getTitle());

		//���ȴ�����J��
		taskDueDate =(EditText)getView().findViewById(R.id.editTextDueDate);
		taskDueDate.setHint(getString(R.string.Textview_DueDate_Hint));
		//taskDueDate.setText(mEditorVar.Task.getDueDate());

		//���ȴ�����ܫ��s
		taskBtnDueDate=(ImageButton)getView().findViewById(R.id.imageButtonResetDate);
		//OnClickListener btnClcikListener;
		taskBtnDueDate.setOnClickListener(btnClcikListener);
		
		

		// spinner - �������O
		taskCategory=(Spinner)getView().findViewById(R.id.spinnerCategory);
		taskCategory.setPrompt(getString(R.string.spinner_category_prompt));
		

		// spinner - �����u��
		taskPriority=(Spinner)getView().findViewById(R.id.spinnerPriority);
		taskPriority.setPrompt(getString(R.string.spinner_priority_prompt));
		
		// spinner - �M��
		taskProject=(Spinner)getView().findViewById(R.id.spinnerProject);
		taskProject.setPrompt(getString(R.string.spinner_project_prompt));

		// spinner - �����u��
		taskPriority=(Spinner)getView().findViewById(R.id.spinnerPriority);
		taskPriority.setPrompt(getText(R.string.spinner_priority_prompt));

		// spinner - ���ȼ���
		taskTag=(Spinner)getView().findViewById(R.id.spinnerTag);
		taskTag.setPrompt(getText(R.string.spinner_tag_prompt));


	}

	//  �Ѹ�Ʈw��l���ܼ�
	public static void init(Intent intent) {
		Bundle b = intent.getBundleExtra(CommonVar.BundleName);
		if (b != null) {
			//�ѷ� ������TaskFieldContents/RemindmeVar.class���B, �T�O�ܼ����P���ǳ��ۦP
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
		.setTitle("�п��...")
		.setView(mview)
		.setItems(R.array.Array_TaskEditor_btnTaskDueDate_String,
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,
					int which) {

				switch (which) {
				case 0:// ����
					taskDueDate.setText(
							MyCalendar.getTodayString(0));
					break;
				case 1:// ����
					taskDueDate.setText(
							MyCalendar.getTodayString(1));
					break;
				case 2:// �U�g
					taskDueDate.setText(
							MyCalendar.getTodayString(7));
					break;
				case 3:// �@�Ӥ뤺
					taskDueDate.setText(
							MyCalendar.getTodayString(30));
					break;
				case 4:// ��ܤ��

					break;
				case 5:// ����

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
	// �T�O Title ��줣����
	public static String getTaskTitle() {
		String TaskTitleString = "null";
		// �p�GTaskTitle��줣���ūh��J�ϥΪ̿�J�ƭ�
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
	// �T�{  DueDate ���ƭ�
	public static String getTaskDueDate() {
		String taskDueDateString="null";
		// �p�GTaskTitle��줣���ūh��J�ϥΪ̿�J�ƭ�, �M��i��U�@�B�P�_
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
