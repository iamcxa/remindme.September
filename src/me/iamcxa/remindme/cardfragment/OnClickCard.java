package me.iamcxa.remindme.cardfragment;


import it.gmariotti.cardslib.library.internal.Card;
import me.iamcxa.remindme.RemindmeVar;
import me.iamcxa.remindme.RemindmeVar.TaskCursor;
import me.iamcxa.remindme.editor.TaskEditorTab;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

public class OnClickCard {
	Context context;
	Card card;
	Cursor cursor;
	MyCursorCardAdapter mMyCursorCardAdapter;

	public MyCursorCardAdapter getMyCursorCardAdapter() {
		return mMyCursorCardAdapter;
	}

	public void setMyCursorCardAdapter(MyCursorCardAdapter mMyCursorCardAdapter) {
		this.mMyCursorCardAdapter = mMyCursorCardAdapter;
	}

	public OnClickCard(Context context, Cursor cursor, Card card) {
		super();
		this.context = context;
		this.cursor = cursor;
		this.card = card;
	}

	public void readIt(String cardPosition) {
		try {


			RemindmeVar.debugMsg(0,
					"ReadCardonClick cursor moveToPosition cardIDfromclcikevent="
							+ cardPosition);
			String cardID = mMyCursorCardAdapter.getCardFromCursor(cursor)
					.getId();
			String cardID2=mMyCursorCardAdapter.getItem(Integer.parseInt(card.getId())).getId();		

			long cardID3=mMyCursorCardAdapter.getItemId(Integer.parseInt(cardID));



			cursor.moveToPosition(Integer.parseInt(cardPosition));

			ReadDatefromCursor();

		} catch (Exception e) {

			RemindmeVar.debugMsg(0, "ReadCardonClick error=" + e);
		}

	}

	private void ReadDatefromCursor() {
		int taskId = cursor.getInt(0);

		String[] DatefromCursor={
				//主要內容
				TaskCursor.KEY.TITTLE ,
				TaskCursor.KEY.CONTENT ,
				TaskCursor.KEY.CREATED,
				TaskCursor.KEY.DUE_DATE ,
				//提醒
				TaskCursor.KEY.ALERT_Interval ,
				TaskCursor.KEY.ALERT_TIME ,
				//位置
				TaskCursor.KEY.LOCATION_NAME ,
				TaskCursor.KEY.COORDINATE ,
				TaskCursor.KEY.DISTANCE ,
				//分類,標籤與優先
				TaskCursor.KEY.CATEGORY ,
				TaskCursor.KEY.PRIORITY ,
				TaskCursor.KEY.TAG ,
				TaskCursor.KEY.LEVEL ,
				//其他
				TaskCursor.KEY.COLLABORATOR ,
				TaskCursor.KEY.GOOGOLE_CAL_SYNC_ID ,
				TaskCursor.KEY.TASK_COLOR ,
		};

		Bundle b = new Bundle();
		b.putInt(TaskCursor.KEY._ID,taskId);
		b.putString(TaskCursor.KEY.TITTLE,DatefromCursor[0]);
		b.putString(TaskCursor.KEY.CONTENT,DatefromCursor[1]);
		b.putString(TaskCursor.KEY.CREATED,DatefromCursor[2]);
		b.putString(TaskCursor.KEY.DUE_DATE,DatefromCursor[3]);
		b.putString(TaskCursor.KEY.ALERT_Interval,DatefromCursor[4]);
		b.putString(TaskCursor.KEY.ALERT_TIME,DatefromCursor[5]);
		b.putString(TaskCursor.KEY.LOCATION_NAME,DatefromCursor[6]);
		b.putString(TaskCursor.KEY.COORDINATE,DatefromCursor[7]);
		b.putString(TaskCursor.KEY.CATEGORY,DatefromCursor[8]);
		b.putInt(TaskCursor.KEY.PRIORITY,Integer.valueOf(DatefromCursor[9]));
		b.putString(TaskCursor.KEY.TAG,DatefromCursor[10]);

		// 將備忘錄資訊添加到Intent
		Intent intent = new Intent();
		intent.putExtra(TaskCursor.KEY._Bundle, b);
		// 啟動備忘錄詳細資訊Activity
		intent.setClass(context, TaskEditorTab.class);
		context.startActivity(intent);
	}
}
