package common;

import android.provider.BaseColumns;

public class MyCursor {
	
	private MyCursor(){}
	
	// 內部類別
		public static final class TaskCursor implements BaseColumns {

			private TaskCursor() {
			}

			// 查詢欄位陣列
			public static final String[] PROJECTION = new String[] { 
				KEY._ID ,
				//主要內容
				KEY.TITTLE ,
				KEY.CONTENT ,
				KEY.CREATED ,
				KEY.DUE_DATE ,
				//提醒
				KEY.ALERT_Interval ,
				KEY.ALERT_TIME ,
				//位置
				KEY.LOCATION_NAME ,
				KEY.COORDINATE ,
				KEY.DISTANCE ,
				//分類,標籤與優先
				KEY.CATEGORY ,
				KEY.PRIORITY ,
				KEY.TAG ,
				KEY.LEVEL ,
				//其他
				KEY.COLLABORATOR ,
				KEY.GOOGOLE_CAL_SYNC_ID ,
				KEY.TASK_COLOR ,};

			// 查詢欄位陣列
			public static final String[] PROJECTION_GPS = new String[] {
				KEY._ID, // 0
				KEY.LOCATION_NAME, // 1
				KEY.COORDINATE,// 2
				KEY.DISTANCE,// 3
				KEY.PRIORITY,// 4
				KEY.DUE_DATE,// 5
				KEY.ALERT_TIME,// 6
				KEY.CREATED,// 6
			};

			public static class KEY_INDEX {
				public static final int KEY_ID = 0;
				//主要內容
				public static final int TITTLE = 1;
				public static final int CONTENT = 2;
				public static final int CREATED = 3;
				public static final int DUE_DATE = 4;
				//提醒
				public static final int ALERT_Interval = 5;
				public static final int ALERT_TIME = 6;
				//位置
				public static final int LOCATION_NAME = 7;
				public static final int COORDINATE = 8;
				public static final int DISTANCE = 9;
				//分類,標籤與優先
				public static final int CATEGORY = 10;
				public static final int PRIORITY = 11;
				public static final int TAG = 12;
				public static final int LEVEL = 13;
				//其他
				public static final int COLLABORATOR = 14;
				public static final int GOOGOLE_CAL_SYNC_ID = 15;
				public static final int TASK_COLOR = 16;
			}

			// 其他欄位常數
			public static class KEY {


				public static final String _ID = "_id";
				//主要內容
				public static final String TITTLE = "TITTLE";
				public static final String CONTENT = "CONTENT";
				public static final String CREATED = "CREATED";
				public static final String DUE_DATE = "DUE_DATE";
				//提醒
				public static final String ALERT_Interval = "ALERT_Interval";
				public static final String ALERT_TIME = "ALERT_TIME";
				//位置
				public static final String LOCATION_NAME = "LOCATION_NAME";
				public static final String COORDINATE = "COORDINATE";
				public static final String DISTANCE = "DISTANCE";
				//分類,標籤與優先
				public static final String CATEGORY = "CATEGORY";
				public static final String PRIORITY = "PRIORITY";
				public static final String TAG = "TAG";
				public static final String LEVEL = "LEVEL";
				//其他
				public static final String COLLABORATOR = "COLLABORATOR";
				public static final String GOOGOLE_CAL_SYNC_ID = "GOOGOLE_CAL_SYNC_ID";
				public static final String TASK_COLOR = "TASK_COLOR";

			}
		}
}
