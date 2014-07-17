package me.iamcxa.remindme.editor;

import me.iamcxa.remindme.R;
import me.iamcxa.remindme.RemindmeVar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;

public class TaskEditorMain extends Fragment {

	private static MultiAutoCompleteTextView taskTittle; //任務標題
	private static EditText taskDueDate;//任務到期日
	private static Spinner taskCategory;//任務類別
	private static Spinner taskPriority;//任務優先

	private EditorVar mEditorVar ;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.activity_task_editor, container, false);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		setupViewComponent();
	}


	private void setupViewComponent(){
		//任務標題輸入框
		taskTittle =(MultiAutoCompleteTextView)getView().
				findViewById(R.id.multiAutoCompleteTextViewTittle);
		taskTittle.setHint("任務");

		//任務日期輸入框
		taskDueDate =(EditText)getView().findViewById(R.id.editTextDueDate);
		taskDueDate.setHint("到期日");

		//任務類別選擇框
		taskCategory=(Spinner)getActivity().findViewById(R.id.spinnerCategory);



		//任務標籤輸入框





	}


	public static String getTaskDueDate() {
		String taskDueDateString = "null";
		if (!(taskDueDate.getText().toString().isEmpty())){
			taskDueDateString=taskDueDate.getText().toString().trim();
		}
		
		RemindmeVar.debugMsg(0,"getTaskDueDate:"+ taskDueDateString.isEmpty());
		return taskDueDateString;
	}
	public static String getTaskTittle() {
		String TaskTittleString = "null";
		if (!(taskTittle.getText().toString().isEmpty())){
			TaskTittleString= taskTittle.getText().toString().trim();
		}
		RemindmeVar.debugMsg(0,"getTaskTittle:"+ TaskTittleString+",TaskTittle.len="+TaskTittleString.length());
		return TaskTittleString;
	}

}
