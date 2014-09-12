package common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.R.integer;

public class MyCalendar {

	private MyCalendar() {
	}

	/***********************/
	/** getDaysLeft **/
	/***********************/
	public static long getDaysLeft(String TaskDate, int Option) {
		// 定義時間格式
		// java.text.SimpleDateFormat sdf = new
		SimpleDateFormat sdf = null;
		if (Option == 1) {
			sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm",Locale.getDefault());
		} else if (Option == 2) {
			sdf = new SimpleDateFormat("yyyy/MM/dd",Locale.getDefault());
		}

		// 取得現在時間
		//Date now = new Date();
		String nowDate = sdf.format(getToday());
		MyDebug.MakeLog(0, "now:" + nowDate + ", task:" + TaskDate);
		try {
			// 取得事件時間與現在時間
			Date dt1 = sdf.parse(nowDate);
			//Date dt2 = sdf.parse(TaskDate);

			// 取得兩個時間的Unix時間
			Long ut1 = dt1.getTime();
			//Long ut2 = dt2.getTime();

			Long timeP = Long.valueOf(TaskDate) - ut1;// 毫秒差
			// 相減獲得兩個時間差距的毫秒
			// Long sec = timeP / 1000;// 秒差
			// Long min = timeP / 1000 * 60;// 分差
			// Long hr = timeP / 1000 * 60 * 60;// 時差
			Long day = timeP / (1000 * 60 * 60 * 24);// 日差
			MyDebug.MakeLog(0, "Get days left Sucessed! " + day);
			return day;
		} catch (Exception e) {
			// TODO: handle exception
			MyDebug.MakeLog(999, e.toString());
			return -1;
		}
	}

	public static long getNextFewDays(int Days) {
		long NextFewDays=System.currentTimeMillis()+( 60 * 60 * 24 * 1000 * Days);//N天的毫秒數;
		return NextFewDays;

	}

	public static long getToday() {
		long curDate=System.currentTimeMillis();
		return curDate;
	}
	
	public static String getDate_From_TimeMillis(boolean HasHourAndMins,long A_Time_In_Milliseconds ) {
		SimpleDateFormat sdf = null;
		if (HasHourAndMins) {
			sdf = new SimpleDateFormat("yyyy/MM/dd_HH:mm",getDefaultLocal());
		} else  {
			sdf = new SimpleDateFormat("yyyy/MM/dd",getDefaultLocal());
		}
		String nowDate = sdf.format(A_Time_In_Milliseconds);
		
		return nowDate;
	}

	public static long getTimeMillis_From_Date(String A_Date) {
		String[] mTimeMillis={""};
		MyDebug.MakeLog(0, "A_Date="+A_Date);
		if (A_Date.contains("_")) {
			String[] nTimeMillis = A_Date.split("_");
			MyDebug.MakeLog(0,"nTimeMillis="+ nTimeMillis[0]);
			mTimeMillis=nTimeMillis[0].split("/");
		}else {
		 mTimeMillis=A_Date.split("/");
		}
		MyDebug.MakeLog(0, "mTimeMillis[]='"+mTimeMillis[0]+","+mTimeMillis[1]+","+mTimeMillis[2]+"'");
		Calendar calendar=Calendar.getInstance();
		calendar.set(Integer.valueOf(mTimeMillis[0]),
				Integer.valueOf(mTimeMillis[1]), 
				Integer.valueOf(mTimeMillis[2]),
				0,0,0);
		
		return (calendar.getTimeInMillis())/1000;
	}
	
	public static String getTodayString(int ExtraDaysIfNeeded) {
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DAY_OF_MONTH, ExtraDaysIfNeeded);
		int dayOfMonth = today.get(Calendar.DAY_OF_MONTH);
		String month = String.valueOf(today.get(Calendar.MONTH)+1);
		int year=today.get(Calendar.YEAR);
		if (String.valueOf(month).length()==1){
			month="0"+month;
		}
		return year+"/"+month+"/"+dayOfMonth;
	}
	
	// Get the defaulf Local of android
	public static  Locale getDefaultLocal(){
		CommonVar.DEFAULT_LOCAL=Locale.getDefault();
		return CommonVar.DEFAULT_LOCAL;	
	}
}
