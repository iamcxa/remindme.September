/**
 * 
 */
package common;

import java.util.Locale;

import android.net.Uri;

/**
 * @author cxa
 */
public class CommonVar {
	
	private CommonVar() {}

	public static final String BundleName = "Bundle"; 
	
	// 授權常數
	public static final String AUTHORITY = "me.iamcxa.remindme";

	// URI常數
	public static final String TASKLIST = "remindmetasklist";

	// 存取Uri
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TASKLIST);
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.iamcxa"
			+ "." + TASKLIST;
	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.iamcxa"
			+ "." + TASKLIST;

	// 預設排序常數
	public static final String DEFAULT_SORT_ORDER = "created DESC";

	// 廣播接收器
	public static final String BC_ACTION = "me.iamcxa.remindme.TaskReceiver";

	// 預設地區
	public static Locale DEFAULT_LOCAL=Locale.TAIWAN;

	// 
	public static String[] TASKEDITOR_DUEDATE_BASIC_STRING_ARRAY={""};
	public static String[] TASKEDITOR_DUEDATE_EXTRA_STRING_ARRAY={""};
	
}