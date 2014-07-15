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

public class TaskEditorLocation extends Fragment {

	private MultiAutoCompleteTextView taskTittle; //任務標題
	private EditText taskDuedate;//任務到期日
	private Spinner taskCategory;//任務類別

	private EditorVar mEditorVar ;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.activity_task_editor_parts_dialog_location, container, false);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		setupViewComponent();
	}


	private void setupViewComponent(){





	}
}
