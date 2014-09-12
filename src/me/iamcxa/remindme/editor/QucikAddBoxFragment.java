/*
 * 
 */
package me.iamcxa.remindme.editor;

import me.iamcxa.remindme.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

/**
 * @author cxa
 * 
 */
public class QucikAddBoxFragment extends Fragment {
	
	private static EditText tittlEditText;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)  {
		View 		view = inflater.inflate(R.layout.activity_main_parts_quick_add_box, container, false);

		//listView = (ListView) view.findViewById(R.id.ListView);
		// 標題輸入欄位
		tittlEditText = (EditText)view.findViewById(R.id.editTexQuickAddBox);
		tittlEditText.setHint("您能輸入\"123 9. 星巴克 裝文青 \"快速設定");
		
		return view;

	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#getLayoutInflater(android.os.Bundle)
	 */
	@Override
	public LayoutInflater getLayoutInflater(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.getLayoutInflater(savedInstanceState);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#getLoaderManager()
	 */
	@Override
	public LoaderManager getLoaderManager() {
		// TODO Auto-generated method stub
		return super.getLoaderManager();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#getView()
	 */
	@Override
	public View getView() {
		// TODO Auto-generated method stub
		return super.getView();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	

}