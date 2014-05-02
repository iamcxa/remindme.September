package me.iamcxa.remindme.cardfragment;

import it.gmariotti.cardslib.library.internal.Card;
import me.iamcxa.remindme.CommonUtils;
import me.iamcxa.remindme.CommonUtils.TaskCursor;
import me.iamcxa.remindme.editor.RemindmeTaskEditor;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

public class ReadCardOnClick {
	Context context;
	Card card;
	Cursor cursor;

	public ReadCardOnClick(Context context, Cursor cursor, Card card) {
		super();
		this.context = context;
		this.cursor = cursor;
		this.card = card;
	}

	public void readIt() {
		try {
			
			CommonUtils.debugMsg(0, "ReadCardonClick cursor moveToPosition="
						+ (card.getId()));
			
	
			cursor.moveToPosition(Integer.parseInt(card.getId()) - 1);
				readDatefromCursor();


		} catch (Exception e) {

			CommonUtils.debugMsg(0, "ReadCardonClick error=" + e);
		}

	}
	
	private void readDatefromCursor(){
		int taskId = Integer.parseInt(cursor.getString(0));
		String tittle = cursor.getString(1);
		String endDate = cursor.getString(3);
		String endTime = cursor.getString(TaskCursor.KeyIndex.EndDate);
		String isRepeat = cursor.getString(TaskCursor.KeyIndex.Is_Repeat);
		String IsFixed = cursor.getString(TaskCursor.KeyIndex.Is_Fixed);
		String isAllDay = cursor.getString(TaskCursor.KeyIndex.Is_AllDay);
		String LocationName = cursor
				.getString(TaskCursor.KeyIndex.LocationName);
		String Coordinate = cursor
				.getString(TaskCursor.KeyIndex.Coordinate);
		String Collaborator = cursor
				.getString(TaskCursor.KeyIndex.Collaborator);
		String CREATED = cursor.getString(TaskCursor.KeyIndex.CREATED);
		String CONTENT = cursor.getString(TaskCursor.KeyIndex.CONTENT);

		Bundle b = new Bundle();
		b.putInt("taskId", taskId);
		b.putString("tittle", tittle);
		b.putString("endDate", endDate);
		b.putString("endTime", endTime);
		b.putString("isRepeat", isRepeat);
		b.putString("IsFixed", IsFixed);
		b.putString("isAllDay", isAllDay);
		b.putString("LocationName", LocationName);
		b.putString("Coordinate", Coordinate);
		b.putString("Collaborator", Collaborator);
		b.putString("CREATED", CREATED);
		b.putString("CONTENT", CONTENT);

		// 將備忘錄資訊添加到Intent
		Intent intent = new Intent();
		intent.putExtra("b", b);
		// 啟動備忘錄詳細資訊Activity
		intent.setClass(context, RemindmeTaskEditor.class);
		context.startActivity(intent);
	}
}
