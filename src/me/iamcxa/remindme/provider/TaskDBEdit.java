package me.iamcxa.remindme.provider;
import me.iamcxa.remindme.CommonUtils;
import me.iamcxa.remindme.CommonUtils.RemindmeTaskCursor;
import me.iamcxa.remindme.provider.TaskDBProvider.DatabaseHelper;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class TaskDBEdit {
	private Context context;
	private DatabaseHelper DH = null;
	private SQLiteDatabase db;
	public TaskDBEdit(Context context){
		this.context=context;
	}
	
	public void openDB(){
    	DH = new TaskDBProvider.DatabaseHelper(context);  
    }
	
    public void closeDB(){
    	DH.close();    	
    }
    

	public Cursor query(String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		db = DH.getWritableDatabase();
		return db.query(TaskDBProvider.TASK_LIST_TABLE_NAME,		//資料表名稱
				projection,	//欄位名稱
				selection, // WHERE
				selectionArgs, // WHERE 的參數
				sortOrder, // GROUP BY
				null, // HAVING
				null  // ORDOR BY
		);
	}

	public void update(ContentValues values, Object object,
			Object object2) {
		db = DH.getWritableDatabase();
		db.update(TaskDBProvider.TASK_LIST_TABLE_NAME, values, null	, null);
	}
    
}
