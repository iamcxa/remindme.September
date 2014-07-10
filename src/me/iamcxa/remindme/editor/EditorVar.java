package me.iamcxa.remindme.editor;


public class EditorVar {


	// 顯示日期、時間對話方塊常數
	public final int DATE_DIALOG_ID = 0;
	public final int TIME_DIALOG_ID = 1;
	
	// 是否開啟提醒
	public int on_off = 0;
	// 是否聲音警告
	public int alarm = 0;
	// String
	// 保存內容、日期與時間字串
	public String tittle = null;
	public String content = null;
	public String switcher = null;
	public String endDate = null, endTime = null;
	public String locationName = null;
	public String isRepeat = null;
	public String isFixed = null;
	public String isAllDay = null;
	public String isHide = null;
	public String isPW = null;
	public String coordinate = null;
	public String collaborator = null;
	public String created = null;
	public String LastTimeSearchName = "";
	public String is_Fixed = null;
	// 備忘錄ID
	public int taskId;
	
	
	public static EditorVar EditorVarInstance = new EditorVar();
	public DateVar Date = new DateVar();
	public LocationVar Location = new LocationVar();

	private EditorVar(){}

	public static EditorVar GetInstance(){
		return EditorVarInstance;
	}


}


class LocationVar {
	// gps使用時間
	public int GpsUseTime = 0;
	// 經緯度
	public Double Latitude;
	public Double Longitude;

	// 是否有搜尋過地點
	public Boolean isdidSearch = false;
	public Boolean isDraped = false;
}


class DateVar {

	// 日期
	private int mYear;
	private int mMonth;
	private int mDay;
	// 時間
	private int mHour;
	private int mMinute;
	private int target;

	public int getmYear() {
		return mYear;
	}
	public void setmYear(int mYear) {
		this.mYear = mYear;
	}
	public int getmMonth() {
		return mMonth;
	}
	public void setmMonth(int mMonth) {
		this.mMonth = mMonth;
	}
	public int getmDay() {
		return mDay;
	}
	public void setmDay(int mDay) {
		this.mDay = mDay;
	}
	public int getmHour() {
		return mHour;
	}
	public void setmHour(int mHour) {
		this.mHour = mHour;
	}
	public int getmMinute() {
		return mMinute;
	}
	public void setmMinute(int mMinute) {
		this.mMinute = mMinute;
	}
	public int getTarget() {
		return target;
	}
	public void setTarget(int target) {
		this.target = target;
	}

}