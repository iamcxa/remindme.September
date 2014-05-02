/**
 * 
 */
package me.iamcxa.remindme.cardfragment;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardCursorAdapter;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.ViewToClickToExpand;
import it.gmariotti.cardslib.library.internal.ViewToClickToExpand.CardElementUI;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import me.iamcxa.remindme.CommonUtils;
import me.iamcxa.remindme.R;
import me.iamcxa.remindme.CommonUtils.TaskCursor;
import me.iamcxa.remindme.editor.RemindmeTaskEditor;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author cxa
 * 
 */

/*******************************/
/** Class MyCursorCardAdapter **/
/*******************************/
public class MyCursorCardAdapter extends CardCursorAdapter {
	private Context thiscontext;

	public MyCursorCardAdapter(Context context) {
		super(context);
		thiscontext = (context);
	}

	@Override
	protected Card getCardFromCursor(final Cursor cursor) {
		MyCursorCard card = new MyCursorCard(super.getContext());
		setCardFromCursor(card, cursor);
		card.setClickable(true);
		card.setExpanded(true);

		// Create a CardHeader
		CardHeader header = new CardHeader(getContext());

		// Set visible the expand/collapse button
		header.setButtonExpandVisible(true);
		// header.setOtherButtonVisible(true);
		header.setOtherButtonDrawable(R.drawable.ic_action_labels);
		// Add a callback
		header.setOtherButtonClickListener(new CardHeader.OnClickCardHeaderOtherButtonListener() {
			@Override
			public void onButtonItemClick(Card card, View view) {
				Toast.makeText(getContext(), "Click on Other Button",
						Toast.LENGTH_LONG).show();
				ViewToClickToExpand viewToClickToExpand = ViewToClickToExpand
						.builder().highlightView(true)
						.setupView(getCardListView())
						.setupCardElement(CardElementUI.HEADER);
				card.setViewToClickToExpand(viewToClickToExpand);

			}
		});

		// Set the header title
		header.setTitle(card.mainHeader);

		// Add Header to card
		card.addCardHeader(header);

		// This provides a simple (and useless) expand area
		CardExpand expand = new CardExpand(getContext());
		// Set inner title in Expand Area
		String aa = "dbId=" + cursor.getString(0) + ",w="
				+ cursor.getString(CommonUtils.TaskCursor.KeyIndex.Priority);

		expand.setTitle(aa);
		card.addCardExpand(expand);

		// Add Thumbnail to card
		final CardThumbnail thumb = new CardThumbnail(getContext());
		thumb.setDrawableResource(card.resourceIdThumb);
		card.addCardThumbnail(thumb);

		// Set on Click listener
		card.setOnClickListener(new Card.OnCardClickListener() {
			@Override
			public void onClick(Card card, View view) {
				Toast.makeText(getContext(), "Clickable card",
						Toast.LENGTH_SHORT).show();
				OpenTaskReader(cursor, card.getId(), card);

			}
		});

		card.setOnLongClickListener(new Card.OnLongCardClickListener() {
			@Override
			public boolean onLongClick(Card card, View view) {
				// TODO Auto-generated method stubs
				Toast.makeText(getContext(), "setOnLongClickListener",
						Toast.LENGTH_SHORT).show();

				ShowLongClickMenu();

				return false;

			}
		});

		return card;
	}

	private void setCardFromCursor(MyCursorCard card, Cursor cursor) {

		// 準備常數
		CommonUtils.debugMsg(0, "prepare data from cursor...");
		boolean Extrainfo = cursor
				.isNull(CommonUtils.TaskCursor.KeyIndex.Other);
		int CID = cursor.getInt(CommonUtils.TaskCursor.KeyIndex.KEY_ID);
		String dintence = cursor
				.getString(CommonUtils.TaskCursor.KeyIndex.Distance);
		String startTime = cursor
				.getString(CommonUtils.TaskCursor.KeyIndex.StartTime);
		String endTime = "";// cursor.getString(5);
		String endDate = cursor
				.getString(CommonUtils.TaskCursor.KeyIndex.EndDate);
		String LocationName = cursor
				.getString(CommonUtils.TaskCursor.KeyIndex.LocationName);
		String extraInfo = cursor
				.getString(CommonUtils.TaskCursor.KeyIndex.Other);
		String PriorityWeight = cursor
				.getString(CommonUtils.TaskCursor.KeyIndex.Priority);
		long dayLeft = CommonUtils.getDaysLeft(endDate, 2);
		// int dayLeft = Integer.parseInt("" + dayLeftLong);

		// give a ID.
		CommonUtils.debugMsg(0, "set ID...Card id=" + CID);
		card.setId("" + CID);

		// 卡片標題 - first line
		CommonUtils.debugMsg(0, CID + " set Tittle...");
		card.mainHeader = cursor
				.getString(CommonUtils.TaskCursor.KeyIndex.Tittle);

		// 時間日期 - sec line
		CommonUtils.debugMsg(0, CID + " set Date/Time...");
		CommonUtils.debugMsg(0, CID + " dayleft=" + dayLeft);
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
		CommonUtils.debugMsg(0, "Location=\"" + LocationName + "\"");
		if ((LocationName.length()) > 1) {
			card.resourceIdThumb = R.drawable.map_marker;
		} else {
			card.resourceIdThumb = R.drawable.tear_of_calendar;
			card.LocationName = null;
		}

		// 距離與地點資訊
		CommonUtils.debugMsg(0, "dintence=" + dintence);
		if (dintence == null) {
			card.LocationName = LocationName;
		} else {
			// if (Double.valueOf(dintence) < 1) {
			// card.LocationName = LocationName + " - 距離 "
			// + Double.valueOf(dintence) * 1000 + " 公尺";
			// } else {
			card.LocationName = LocationName + " - 距離 " + dintence + " 公里";

			// }
		}

		// 可展開額外資訊欄位
		CommonUtils.debugMsg(0, "isExtrainfo=" + Extrainfo);
		// card.Notifications = "dbId="
		// + cursor.getString(0)
		// + ",w="
		// +
		// cursor.getString(CommonUtils.TaskCursor.KeyIndex.PriorityWeight);
		// if (!Extrainfo) {
		card.resourceIdThumb = R.drawable.outline_star_act;
		// 額外資訊提示 - 第四行

		// }
		// card.Notifications = cursor.getString(0);

		// 依照權重給予卡片顏色
		if (cursor.getInt(CommonUtils.TaskCursor.KeyIndex.Priority) > 6000) {
			card.setBackgroundResourceId(R.drawable.demo_card_selector_color5);
		} else if (cursor.getInt(CommonUtils.TaskCursor.KeyIndex.Priority) > 3000) {
			card.setBackgroundResourceId(R.drawable.demo_card_selector_color3);
		}

	}

	public Context getThiscontext() {
		return thiscontext;
	}

	public void setThiscontext(Context thiscontext) {
		this.thiscontext = thiscontext;
	}

	@SuppressWarnings("unused")
	private void removeCard(Card card, Context context,
			MyCursorCardAdapter mAdapter) {

		// Use this code to delete items on DB
		ContentResolver resolver = context.getContentResolver();
		long noDeleted = resolver.delete(CommonUtils.CONTENT_URI,
				CommonUtils.TaskCursor.KeyColumns.KEY_ID + " = ? ",
				new String[] { card.getId() });

		mAdapter.notifyDataSetChanged();

	}

	/***********************/
	/** Class MyThumbnail **/
	/***********************/
	// implment the clickable card thumbnail.
	class MyCardThumbnail extends CardThumbnail {

		public MyCardThumbnail(Context context) {
			super(context);
		}

		@Override
		public void setupInnerViewElements(ViewGroup parent, View imageView) {
			ViewToClickToExpand viewToClickToExpand = ViewToClickToExpand
					.builder().highlightView(true).setupView(imageView);
			getParentCard().setViewToClickToExpand(viewToClickToExpand);
		}
	}

	/************************/
	/** Class MyCursorCard **/
	/************************/
	private static class MyCursorCard extends Card {

		String DateTime;
		String LocationName;
		String Notifications;
		String mainHeader;

		int resourceIdThumb;

		public MyCursorCard(Context context) {
			super(context, R.layout.card_cursor_inner_content);

		}

		@Override
		public void setupInnerViewElements(ViewGroup parent, View view) {

			// Retrieve elements
			TextView mTitleTextView = (TextView) parent
					.findViewById(R.id.card_cursor_main_inner_title);
			TextView mSecondaryTitleTextView = (TextView) parent
					.findViewById(R.id.card_cursor_main_inner_subtitle);
			TextView mThirdTitleTextView = (TextView) parent
					.findViewById(R.id.card_cursor_main_inner_thirdtitle);

			if (mTitleTextView != null)
				mTitleTextView.setText(DateTime);

			if (mSecondaryTitleTextView != null)
				mSecondaryTitleTextView.setText(LocationName);

			if (mThirdTitleTextView != null)
				mThirdTitleTextView.setText(Notifications);

		}
	}

	private void OpenTaskReader(Cursor cursor, String ID, Card card) {
		cursor.moveToPosition(Integer.parseInt(card.getId()) - 1);

		int id = Integer.parseInt(cursor.getString(0));
		String tittle = cursor.getString(1);
		String endDate = cursor.getString(3);
		String endTime = cursor.getString(TaskCursor.KeyIndex.EndDate);
		String isRepeat = cursor.getString(TaskCursor.KeyIndex.Is_Repeat);
		String IsFixed = cursor.getString(TaskCursor.KeyIndex.Is_Fixed);
		String isAllDay = cursor.getString(TaskCursor.KeyIndex.Is_AllDay);
		String LocationName = cursor
				.getString(TaskCursor.KeyIndex.LocationName);
		String Coordinate = cursor.getString(TaskCursor.KeyIndex.Coordinate);
		String Collaborator = cursor
				.getString(TaskCursor.KeyIndex.Collaborator);
		String CREATED = cursor.getString(TaskCursor.KeyIndex.CREATED);
		String CONTENT = cursor.getString(TaskCursor.KeyIndex.CONTENT);

		Bundle b = new Bundle();
		b.putInt("_id", id);
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
		intent.setClass(getContext(), RemindmeTaskEditor.class);
		getContext().startActivity(intent);

	}

	private AlertDialog ShowLongClickMenu() {

		return new AlertDialog.Builder(getContext())
				.setTitle("請選擇...")
				.setItems(R.array.CardOnLongClickDialogString,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								switch (which) {
								case 0:// 修改

									break;
								case 1:// 刪除

									break;
								case 2:// 提高優先

									break;
								case 3:// 降低優先

									break;
								case 5:// 分享

									break;
								}

								//
								/* User clicked so do some stuff */
								String[] items = getContext().getResources().getStringArray(
										R.array.CardOnLongClickDialogString);
								new AlertDialog.Builder(getContext())
										.setMessage(
												"You selected: " + which
														+ " , " + items[which])
										.show();
								//

							}
						}).show();

	}

}
