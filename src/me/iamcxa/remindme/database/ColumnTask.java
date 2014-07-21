package me.iamcxa.remindme.database;

import common.CommonVar;

import android.net.Uri;
import android.provider.BaseColumns;

public final class ColumnTask implements BaseColumns {

	private ColumnTask() {
	}
	// 預設排序常數
	public static final String DEFAULT_SORT_ORDER = "created DESC";

	// 資料表名稱常數
	public static final String TABLE_NAME = "tasks";
	
	// 存取Uri
	public static final Uri URI =
			Uri.parse("content://" + CommonVar.AUTHORITY + "/" + TABLE_NAME);

	public static final String exec_SQL_Statment=
			"CREATE TABLE "
					+ TABLE_NAME
					+ " ("
					// Task ID
					+KEY._id + " INTEGER PRIMARY KEY autoincrement,"
					//主要內容
					+KEY.title + " TEXT,"
					+KEY.status + " TEXT,"
					+KEY.content + " TEXT,"
					+KEY.due_date_millis + " INTEGER,"
					+KEY.due_date_string + " TEXT,"
					+KEY.color + " INTEGER,"
					+KEY.priority + " INTEGER,"
					+KEY.created + " INTEGER,"
					//分類 + " TEXT,"標籤與優先
					+KEY.category_id + " INTEGER,"
					+KEY.tag_id + " INTEGER,"
					+KEY.project_id + " INTEGER,"
					+KEY.collaborator_id + " INTEGER,"
					+KEY.sync_id + " INTEGER,"
					+KEY.location_id+ " INTEGER"
					+ ");";
	
	// 查詢欄位陣列
	public static final String[] PROJECTION = new String[] { 
		KEY._id,
		//主要內容
		KEY.title,
		KEY.status,
		KEY.content,
		KEY.due_date_millis,
		KEY.due_date_string,
		KEY.color,
		KEY.priority,
		KEY.created,
		//分類,標籤與優先
		KEY.category_id,
		KEY.tag_id,
		KEY.project_id,
		KEY.collaborator_id,
		KEY.sync_id,
		KEY.location_id
	};
	
	// 查詢欄位陣列
	public static final int[] ALL_COLUMN_INDEX = new int[] { 
		KEY_INDEX._id,
		//主要內容
		KEY_INDEX.title,
		KEY_INDEX.status,
		KEY_INDEX.content,
		KEY_INDEX.due_date_millis,
		KEY_INDEX.due_date_string,
		KEY_INDEX.color,
		KEY_INDEX.priority,
		KEY_INDEX.created,
		//分類,標籤與優先
		KEY_INDEX.category_id,
		KEY_INDEX.tag_id,
		KEY_INDEX.project_id,
		KEY_INDEX.collaborator_id,
		KEY_INDEX.sync_id,
		KEY_INDEX.location_id,
	};
	
	public static class KEY_INDEX {
		public static final int _id = 0;
		//主要內容
		public static final int title = 1;
		public static final int status = 2;
		public static final int content = 3;
		public static final int due_date_millis = 4;
		public static final int due_date_string = 5;
		public static final int color = 6;
		public static final int priority = 7;
		public static final int created = 8;
		//分類,標籤與優先
		public static final int category_id= 9;
		public static final int tag_id = 10;
		public static final int project_id = 11;
		public static final int collaborator_id = 12;
		public static final int sync_id = 13;
		public static final int location_id = 14;
	}

	// 其他欄位常數
	public static class KEY {
		public static final String _id = "_id";
		//主要內容
		public static final String title = "title";
		public static final String status = "status";
		public static final String content = "content";
		public static final String due_date_millis = "due_date_millis";
		public static final String due_date_string = "due_date_string";
		public static final String color = "color";
		public static final String priority = "priority";
		public static final String created = "created";
		//分類,標籤與優先
		public static final String category_id= "category_id";
		public static final String tag_id = "tag_id";
		public static final String project_id = "project_id";
		public static final String collaborator_id = "collaborator_id";
		public static final String sync_id = "sync_id";
		public static final String location_id = "location_id";
	}

	
}
