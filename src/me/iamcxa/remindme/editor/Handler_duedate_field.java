<<<<<<< HEAD:src/me/iamcxa/remindme/editor/DbAction_CheckDueDateField.java
package me.iamcxa.remindme.editor;

import common.MyDebug;

import common.CommonVar;

public class DbAction_CheckDueDateField {

	private DbAction_CheckDueDateField(){
	}	

	private  String[] basicStringArray=CommonVar.TASKEDITOR_DUEDATE_BASIC_STRING_ARRAY;
	private  String[] extraStringArray=CommonVar.TASKEDITOR_DUEDATE_EXTRA_STRING_ARRAY;
	private static  String rawTaskDueDateString="null";
	private static  String processedTaskDueDateString="null";
	private  boolean dailyEvent=false;
	private  boolean weelkyEvent=false;

	//-------------------- Public method --------------------//
	public String getProcessedTaskDueDateString(String rawTaskDueDateString){
		switch (checkDueDateFormat(rawTaskDueDateString)) {
		case 0:// 純日期：yyyy/mm/dd
			
			break;
		
		case 1:// ：yyyy/mm/dd
			
			break;

		default:
			break;
		}checkDueDateFormat(rawTaskDueDateString);


		return processedTaskDueDateString;
	}

	//-------------------- Private method --------------------//
	private  boolean hasDueDateEmpty(String rawTaskDueDateString){
		MyDebug.MakeLog(0,"hasDueDateEmpty:"+ rawTaskDueDateString.isEmpty());
		if (rawTaskDueDateString.isEmpty()){
			return true;
		}else{
			return false;
		}
	}

	private  int checkDueDateFormat(String rawTaskDueDateString) {
		int rawTaskDueDateStringFormat=0;

		if(!hasDueDateEmpty(rawTaskDueDateString)){
			getWordMeaning(rawTaskDueDateString);

		}


		return rawTaskDueDateStringFormat;
	}

	private  void getWordMeaning(String rawTaskDueDateString){
		checkRepeatability(rawTaskDueDateString);

	}

	private  void checkRepeatability(String rawTaskDueDateString) {
		for (int j = 0; j < extraStringArray.length; j++) {
			// 檢查字義是否含有重複flag
			// [0]="下"（週一,週二...）
			// [1]="每"
			if(rawTaskDueDateString.startsWith(extraStringArray[0])){
				if(j==0)weelkyEvent=true;
				else if (j==1) dailyEvent=true;
			}




			for (String string : extraStringArray) {

			}
		}
	}

	public  String getRawTaskDueDateString() {
		return rawTaskDueDateString;
	}

	public static void setRawTaskDueDateString(String rawTaskDueDateString) {
		DbAction_CheckDueDateField.rawTaskDueDateString = rawTaskDueDateString;
	}

	public static String getProcessedTaskDueDateString() {
		return processedTaskDueDateString;
	}

	public static void setProcessedTaskDueDateString(
			String processedTaskDueDateString) {
		DbAction_CheckDueDateField.processedTaskDueDateString = processedTaskDueDateString;
	}
}
=======
package me.iamcxa.remindme.editor;

import common.MyDebug;

import common.CommonVar;

public class Handler_duedate_field {

	private Handler_duedate_field(){
	}	

	private  String[] basicStringArray=CommonVar.TASKEDITOR_DUEDATE_BASIC_STRING_ARRAY;
	private  String[] extraStringArray=CommonVar.TASKEDITOR_DUEDATE_EXTRA_STRING_ARRAY;
	private static  String rawTaskDueDateString="null";
	private static  String processedTaskDueDateString="null";
	private  boolean dailyEvent=false;
	private  boolean weelkyEvent=false;

	//-------------------- Public method --------------------//
	public String getProcessedTaskDueDateString(String rawTaskDueDateString){
		switch (checkDueDateFormat(rawTaskDueDateString)) {
		case 0:// 純日期：yyyy/mm/dd
			
			break;
		
		case 1:// ：yyyy/mm/dd
			
			break;

		default:
			break;
		}checkDueDateFormat(rawTaskDueDateString);


		return processedTaskDueDateString;
	}

	//-------------------- Private method --------------------//
	private  boolean hasDueDateEmpty(String rawTaskDueDateString){
		MyDebug.MakeLog(0,"hasDueDateEmpty:"+ rawTaskDueDateString.isEmpty());
		if (rawTaskDueDateString.isEmpty()){
			return true;
		}else{
			return false;
		}
	}

	private  int checkDueDateFormat(String rawTaskDueDateString) {
		int rawTaskDueDateStringFormat=0;

		if(!hasDueDateEmpty(rawTaskDueDateString)){
			getWordMeaning(rawTaskDueDateString);

		}


		return rawTaskDueDateStringFormat;
	}

	private  void getWordMeaning(String rawTaskDueDateString){
		checkRepeatability(rawTaskDueDateString);

	}

	private  void checkRepeatability(String rawTaskDueDateString) {
		for (int j = 0; j < extraStringArray.length; j++) {
			// 檢查字義是否含有重複flag
			// [0]="下"（週一,週二...）
			// [1]="每"
			if(rawTaskDueDateString.startsWith(extraStringArray[0])){
				if(j==0)weelkyEvent=true;
				else if (j==1) dailyEvent=true;
			}




			for (String string : extraStringArray) {

			}
		}
	}

	public  String getRawTaskDueDateString() {
		return rawTaskDueDateString;
	}

	public static void setRawTaskDueDateString(String rawTaskDueDateString) {
		Handler_duedate_field.rawTaskDueDateString = rawTaskDueDateString;
	}

	public static String getProcessedTaskDueDateString() {
		return processedTaskDueDateString;
	}

	public static void setProcessedTaskDueDateString(
			String processedTaskDueDateString) {
		Handler_duedate_field.processedTaskDueDateString = processedTaskDueDateString;
	}
}
>>>>>>> dce7deaa85890e28fba875b170e627a10bdf36be:src/me/iamcxa/remindme/editor/Handler_duedate_field.java
