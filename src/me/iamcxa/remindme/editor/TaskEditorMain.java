package me.iamcxa.remindme.editor;

import me.iamcxa.remindme.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;

public class TaskEditorMain extends Fragment {

	private MultiAutoCompleteTextView taskTittle; //任務標題
	private EditText taskDuedate;//任務到期日
	private Spinner taskCategory;//任務類別

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
		taskDuedate =(EditText)getView().findViewById(R.id.editTextDueDate);
		taskDuedate.setHint("到期日");

		//任務類別選擇框
		taskCategory=(Spinner)getActivity().findViewById(R.id.spinnerCategory);



		//任務標籤輸入框





	}
}
