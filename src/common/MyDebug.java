package common;

import android.util.Log;

public class MyDebug {
	
	private MyDebug(){}
	
	public static final String DEBUG_MSG_TAG= CommonVar.TASKLIST;

	/**
	 * @param Log.v  ==>Log_Level=0
	 * @param Log.w  ==>Log_Level=1
	 * @param Log.wtf =>Log_Level=2
	 * @param msgs
	 */
	public static void MakeLog(int Log_Level, String msgs) {
		if (MyPreferences.IS_DEBUG_MSG_ON()) {
			switch (Log_Level) {
			case 0:
				Log.v(DEBUG_MSG_TAG,  msgs);
				break;
			case 1:
				Log.w(DEBUG_MSG_TAG,  msgs);
				break;
			case 2:
				Log.wtf(DEBUG_MSG_TAG,  msgs);
				break;
			default:
				break;
			}
		}

	}

	public static void MakeLog(int Log_Level, String[] msgString) {
		if (MyPreferences.IS_DEBUG_MSG_ON()) {
			String msgs="[START]";
			for (String string : msgString) {
				msgs=","+msgs+","+string;
			}
			switch (Log_Level) {
			case 0:
				Log.v(DEBUG_MSG_TAG,  msgs);
				break;
			case 1:
				Log.w(DEBUG_MSG_TAG,  msgs);
				break;
			case 2:
				Log.wtf(DEBUG_MSG_TAG,  msgs);
				break;
			default:
				break;
			}
		}
		
	}
}
