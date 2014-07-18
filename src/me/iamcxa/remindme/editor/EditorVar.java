package me.iamcxa.remindme.editor;

public class EditorVar {

	// 顯示日期、時間對話方塊常數
	public final int DATE_DIALOG_ID = 0;
	public final int TIME_DIALOG_ID = 1;

	public static EditorVar EditorVarInstance = new EditorVar();
	//切割分類
	public DateVar TaskDate = new DateVar();
	public LocationVar TaskLocation = new LocationVar();
	public EditorFields Task = new EditorFields();
	public TaskTypeVar TaskType= new TaskTypeVar();
	public AlertVar TaskAlert= new AlertVar();
	public OtherVar TaskOther= new OtherVar();

	private EditorVar(){}

	public static EditorVar GetInstance(){
		return EditorVarInstance;
	}

}

//任務基本成員
class EditorFields {
	// 任務ID
	private int taskId=0;
	//任務標題/備註
	private String tittle ="null";
	private String content ="null";
	//任務到期日/建立日
	private String dueDate ="null";
	private String created ="null";

	//---------------Getter/Setter-----------------//
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getTittle() {
		return tittle;
	}
	public void setTittle(String tittle) {
		this.tittle = tittle;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}

}

//任務地點成員
class LocationVar {
	//任務地點名稱/座標
	private String locationName ="null";
	private String coordinate ="null";
	// gps使用時間
	private int GpsUseTime = 0;
	// 經緯度
	private Double Latitude=0.0;
	private Double Longitude=0.0;
	// 是否有搜尋過地點
	private Boolean isSearched = false;
	private Boolean isDropped = false;

	//---------------Getter/Setter-----------------//
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
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
		return isSearched;
	}
	public void setIsdidSearch(Boolean isdidSearch) {
		this.isSearched = isdidSearch;
	}
	public Boolean getIsDropped() {
		return isDropped;
	}
	public void setIsDropped(Boolean isDropped) {
		this.isDropped = isDropped;
	}

}

//任務提醒成員
class AlertVar {

	//任務是否提醒/提醒時間/提醒週期
	private boolean isAlertOn = false;
	private String alertTime ="null";
	private String alertInterval ="null";

	//---------------Getter/Setter-----------------//
	public boolean isAlertOn() {
		return isAlertOn;
	}
	public void setAlertOn(boolean isAlertOn) {
		this.isAlertOn = isAlertOn;
	}
	public String getAlertTime() {
		return alertTime;
	}
	public void setAlertTime(String alertTime) {
		this.alertTime = alertTime;
	}
	public String getAlertInterval() {
		return alertInterval;
	}
	public void setAlertInterval(String alertInterval) {
		this.alertInterval = alertInterval;
	}
}

//日期成員
class DateVar {

	// 日期
	private int mYear=0;
	private int mMonth=0;
	private int mDay=0;
	// 時間
	private int mHour=0;
	private int mMinute=0;
	//private int target;

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

}

//任務類型/標籤/優先成員
class TaskTypeVar{

	//任務優先等級
	private int priority=0;
	//任務分類
	private String category="null";
	//任務標籤
	private String tag="null";
	private String level="null";

	//---------------Getter/Setter-----------------//
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
}

// 其他成員
class OtherVar{

	//任務顏色
	private String collaborator="null";
	private String google_cal_sync_id="null";
	private int task_color=0;

	//---------------Getter/Setter-----------------//
	public int getTask_color() {
		return task_color;
	}
	public String getCollaborator() {
		return collaborator;
	}
	public String getGoogle_cal_sync_id() {
		return google_cal_sync_id;
	}
	public void setCollaborator(String collaborator) {
		this.collaborator = collaborator;
	}
	public void setGoogle_cal_sync_id(String google_cal_sync_id) {
		this.google_cal_sync_id = google_cal_sync_id;
	}
	public void setTask_color(int task_color) {
		this.task_color = task_color;
	}
}