package common;

import android.util.Log;

public class MyDebug {
	
	private MyDebug(){}

	// debug msg TAG
	public static final String DEBUG_MSG_TAG= "debugmsg";

	public static final void MakeLog(int Tag, String msgs) {
		if (MyPreferences.IS_DEBUG_MSG_ON()) {
			switch (Tag) {
			case 0:
				Log.w(DEBUG_MSG_TAG, " " + msgs);
				break;
			case 1:
				Log.w(DEBUG_MSG_TAG, "thread ID=" + msgs);
				break;
			case 999:
				Log.w(DEBUG_MSG_TAG, "時間計算失敗!," + msgs);
				break;
			default:
				break;
			}
		}

	}
}
