package me.iamcxa.remindme.editor;

import me.iamcxa.remindme.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

public class TaskEditorMain extends Fragment {

	private MultiAutoCompleteTextView taskTittle; //標題
	
	//private EditText 

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
		taskTittle =(MultiAutoCompleteTextView)getView().
				findViewById(R.id.multiAutoCompleteTextViewTittle);
		taskTittle.setHint("任務");
		



	}
}
