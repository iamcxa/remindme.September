package me.iamcxa.remindme.editor;


public class EditorVar {

	// 顯示日期、時間對話方塊常數
	public final int DATE_DIALOG_ID = 0;
	public final int TIME_DIALOG_ID = 1;

	public static EditorVar EditorVarInstance = new EditorVar();
	public DateVar Date = new DateVar();
	public LocationVar Location = new LocationVar();
	public EditorFields Editor = new EditorFields();

	private EditorVar(){}

	public static EditorVar GetInstance(){
		return EditorVarInstance;
	}

}

class EditorFields {
	// 是否開啟提醒
	public int on_off = 0;
	// 是否聲音警告
	public int alarm = 0;
	// String
	// 備忘錄ID
	public int taskId;
	
	//任務標題/備註
	public String tittle = null;
	public String content = null;
	
	//任務到期日/建立日
	public String dueDate = null;
	public String created = null;
	
	//任務地點名稱/座標
	public String locationName = null;
	public String coordinate = null;

	//任務到期提醒/提醒週週期
	public String alertTime = null;
	public String alertCycle = null;
	
}


class LocationVar {
	// gps使用時間
	public int GpsUseTime = 0;
	// 經緯度
	public Double Latitude;
	public Double Longitude;

	// 是否有搜尋過地點
	public Boolean isdidSearch = false;
	public Boolean isDropped = false;
	
	//---------------Getter/Setter-----------------//
	public int getGpsUseTime() {
		return GpsUseTime;
	}
	public void setGpsUseTime(int gpsUseTime) {
		GpsUseTime = gpsUseTime;
	}
	public Double getLatitude() {
		return Latitude;
	}
	public void setLatitude(Double latitude) {
		Latitude = latitude;
	}
	public Double getLongitude() {
		return Longitude;
	}
	public void setLongitude(Double longitude) {
		Longitude = longitude;
	}
	public Boolean getIsdidSearch() {
		return isdidSearch;
	}
	public void setIsdidSearch(Boolean isdidSearch) {
		this.isdidSearch = isdidSearch;
	}


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
	
	//---------------Getter/Setter-----------------//
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