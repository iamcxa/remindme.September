package me.iamcxa.remindme.editor;

import android.content.Intent;
import android.os.Bundle;

public class EditorMethod {

	// 初始化方法
	public static void init(Intent intent) {
		Bundle b = intent.getBundleExtra("b");
		if (b != null) {
			EditorVar.taskId = b.getInt("taskId");
			EditorVar.tittle = b.getString("tittle");
			EditorVar.created = b.getString("created");
			EditorVar.endDate = b.getString("endDate");
			EditorVar.endTime = b.getString("endTime");
			EditorVar.content = b.getString("content");
			EditorVar.isRepeat = b.getString("isRepeat");
			EditorVar.isFixed = b.getString("isFixed");
			EditorVar.locationName = b.getString("locationName");
			EditorVar.coordinate = b.getString("coordinate");
			EditorVar.collaborator = b.getString("collaborator");

			if (EditorVar.endDate != null && EditorVar.endDate.length() > 0) {
				String[] strs = EditorVar.endDate.split("/");
				EditorVar.mYear = Integer.parseInt(strs[0]);
				EditorVar.mMonth = Integer.parseInt(strs[1]) - 1;
				EditorVar.mDay = Integer.parseInt(strs[2]);
			}

			if (EditorVar.endTime != null && EditorVar.endTime.length() > 0) {
				String[] strs = EditorVar.endTime.split(":");
				EditorVar.mHour = Integer.parseInt(strs[0]);
				EditorVar.mMinute = Integer.parseInt(strs[1]);
			}
		}
	}
}
