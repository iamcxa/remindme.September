package me.iamcxa.remindme.editor;

import me.iamcxa.remindme.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.MultiAutoCompleteTextView;

public class TaskEditorMain extends Fragment {
	
	private static MultiAutoCompleteTextView taskTittle; //¼ÐÃD
	


	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setupViewComponent();
	}

	@Override
	public View getView() {
		// TODO Auto-generated method stub
		return super.getView();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	private void setupViewComponent(){
		taskTittle=(MultiAutoCompleteTextView)getView().findViewById(R.id.multiAutoCompleteTextViewTittle);
		

		
	}
}
