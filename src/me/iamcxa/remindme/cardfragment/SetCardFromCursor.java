package me.iamcxa.remindme.cardfragment;

import common.MyCalendar;
import common.MyCursor;
import common.MyDebug;
import common.CommonVar;

import me.iamcxa.remindme.R;
import me.iamcxa.remindme.cardfragment.MyCursorCardAdapter.MyCursorCard;
import android.content.Context;
import android.database.Cursor;

public class SetCardFromCursor {

	@SuppressWarnings("unused")
	private Context context;
	private Cursor cursor;
	private MyCursorCard card;

	public SetCardFromCursor(Context context, Cursor cursor, MyCursorCard card) {
		this.context = context;
		this.cursor = cursor;
		this.card = card;
	}

	public void setIt() {
		// 準備常數
		// card.setId(cursor.getString(0));
		card.setId(String.valueOf(cursor.getPosition()));
		

		MyDebug.MakeLog(0, "prepare data from cursor...");
		boolean Extrainfo = cursor
				.isNull(MyCursor.TaskCursor.KEY_INDEX.TAG);
		int CID = cursor.getInt(MyCursor.TaskCursor.KEY_INDEX.KEY_ID);
		String dintence = cursor
				.getString(MyCursor.TaskCursor.KEY_INDEX.DISTANCE);
		String created = cursor
				.getString(MyCursor.TaskCursor.KEY_INDEX.CREATED);
		String endTime = "";// cursor.getString(5);
		String endDate = cursor
				.getString(MyCursor.TaskCursor.KEY_INDEX.DUE_DATE);
		String LocationName = cursor
				.getString(MyCursor.TaskCursor.KEY_INDEX.LOCATION_NAME);
		String extraInfo = cursor
				.getString(MyCursor.TaskCursor.KEY_INDEX.TAG);
		String PriorityWeight = cursor
				.getString(MyCursor.TaskCursor.KEY_INDEX.PRIORITY);
		long dayLeft = MyCalendar.getDaysLeft(endDate, 2);
		// int dayLeft = Integer.parseInt("" + dayLeftLong);

		// give a ID.
		MyDebug.MakeLog(0, "set ID...Card id=" + CID);
		//card.setId("" + CID);

		// 卡片標題 - first line
		MyDebug.MakeLog(0, CID + " set Tittle...");
		card.mainHeader = cursor
				.getString(MyCursor.TaskCursor.KEY_INDEX.TITTLE);

		// 時間日期 - sec line
		MyDebug.MakeLog(0, CID + " set Date/Time...");
		MyDebug.MakeLog(0, CID + " dayleft=" + dayLeft);
		if ((180 > dayLeft) && (dayLeft > 14)) {
			card.DateTime = "再 " + (int) Math.floor(dayLeft) / 30 + " 個月 - "
					+ endDate + " - " + endTime;
		} else if ((14 > dayLeft) && (dayLeft > 0)) {
			card.DateTime = "再 " + dayLeft + " 天 - " + endDate + " - "
					+ endTime;
		} else if ((2 > dayLeft) && (dayLeft > 0)) {
			card.DateTime = "再 " + (int) Math.floor(dayLeft * 24) + "小時後 - "
					+ endDate + " - " + endTime;
		} else if (dayLeft == 0) {
			card.DateTime = "今天 - " + endDate + " - " + endTime;
		} else {
			card.DateTime = endDate + " - " + endTime;
		}

		// 小圖標顯示 - 判斷是否存有地點資訊
		MyDebug.MakeLog(0, "Location=\"" + LocationName + "\"");
		if (LocationName != null) {
			if ((LocationName.length()) > 1) {
				card.resourceIdThumb = R.drawable.map_marker;
			} else {
				card.resourceIdThumb = R.drawable.tear_of_calendar;
				card.LocationName = null;
			}
		}

		// 距離與地點資訊
		MyDebug.MakeLog(0, "dintence=" + dintence);
		if (dintence == null) {
			card.LocationName = LocationName;
		} else {
//			if (Double.valueOf(dintence) < 1) {
//				card.LocationName = LocationName + " - 距離 "
//						+ Double.valueOf(dintence) * 1000 + " 公尺";
//			} else {
				card.LocationName = LocationName + " - 距離 " + dintence + " 公里";

//			}
		}

		// 可展開額外資訊欄位
		MyDebug.MakeLog(0, "isExtrainfo=" + Extrainfo);
		// card.Notifications = "dbId="
		// + cursor.getString(0)
		// + ",w="
		// +
		cursor.getString(MyCursor.TaskCursor.KEY_INDEX.PRIORITY);
		if (!Extrainfo) {
			card.resourceIdThumb = R.drawable.outline_star_act;
			// 額外資訊提示 - 第四行

		}
		card.Notifications = cursor.getString(0);

		// 依照權重給予卡片顏色
		if (cursor.getInt(MyCursor.TaskCursor.KEY_INDEX.PRIORITY) > 6000) {
			card.setBackgroundResourceId(R.drawable.demo_card_selector_color5);
		} else if (cursor.getInt(MyCursor.TaskCursor.KEY_INDEX.PRIORITY) > 3000) {
			card.setBackgroundResourceId(R.drawable.demo_card_selector_color3);
		}
	}

}
