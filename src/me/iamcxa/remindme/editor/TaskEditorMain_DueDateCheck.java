package me.iamcxa.remindme.editor;

import common.MyDebug;

import common.CommonVar;

public class TaskEditorMain_DueDateCheck {

	private TaskEditorMain_DueDateCheck(){
	}	

	private static String[] basicStringArray=CommonVar.TASKEDITOR_DUEDATE_BASIC_STRING_ARRAY;
	private static String[] extraStringArray=CommonVar.TASKEDITOR_DUEDATE_EXTRA_STRING_ARRAY;
	private static String taskDueDateString="null";
	private static boolean dailyEvent=false;
	private static boolean weelkyEvent=false;

	public static boolean hasDueDateEmpty(String rawTaskDueDateString){
		MyDebug.MakeLog(0,"hasDueDateEmpty:"+ rawTaskDueDateString.isEmpty());
		if (rawTaskDueDateString.isEmpty()){
			return true;
		}else{
			return false;
		}
	}

	public static String checkDueDate(String rawTaskDueDateString) {
		int i=0;

		if(!hasDueDateEmpty(rawTaskDueDateString)){
			getWordMeaning(rawTaskDueDateString);

		}


		return taskDueDateString;
	}

	private static void getWordMeaning(String rawTaskDueDateString){
		checkRepeatability(rawTaskDueDateString);
		
	}
	
	private static void checkRepeatability(String rawTaskDueDateString) {
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
}
