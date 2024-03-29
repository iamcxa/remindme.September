/**
 * 
 */
package me.iamcxa.remindme.cardfragment;

import common.MyDebug;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardCursorAdapter;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.ViewToClickToExpand;
import it.gmariotti.cardslib.library.view.component.CardHeaderView;
import me.iamcxa.remindme.R;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import me.iamcxa.remindme.cardfragment.SetCardFromCursor;
import me.iamcxa.remindme.database.ColumnAlert;
import me.iamcxa.remindme.database.ColumnTask;

/**
 * @author cxa
 * 
 */

/*******************************/
/** Class MyCursorCardAdapter **/
/*******************************/
public class MyCursorCardAdapter extends CardCursorAdapter {
	private static SetCardFromCursor mSetCardFromCursor;
	private static OnClickCard mReadCardOnClick;

	private MyCursorCardAdapter(Context context) {
		super(context);
	}
	
	public static MyCursorCardAdapter newInstance(Context context) {
		MyCursorCardAdapter adapter = new MyCursorCardAdapter(context);
		return adapter;
	}

	@Override
	protected Card getCardFromCursor(final Cursor cursor) {
		if(cursor.getCount()>0){	

		}else {

		}
		MyCursorCard card = new MyCursorCard(getContext());

		mReadCardOnClick = new OnClickCard(getContext(), cursor, card);
		mReadCardOnClick.setMyCursorCardAdapter(this);

		mSetCardFromCursor = new SetCardFromCursor(getContext(), 
				cursor, 
				card);
		mSetCardFromCursor.setIt();

		card.setClickable(true);
		card.setExpanded(true);

		// Create a CardHeader
		CardHeader header = new CardHeader(getContext());

		// Set visible the expand/collapse button
		// header.setButtonExpandVisible(true);
		header.setOtherButtonVisible(true);
		header.setOtherButtonDrawable(R.drawable.ic_action_labels);

		// Add a callback
		header.setOtherButtonClickListener(new CardHeader.OnClickCardHeaderOtherButtonListener() {
			@Override
			public void onButtonItemClick(Card card, View view) {
				Toast.makeText(getContext(), "Click on Other Button",
						Toast.LENGTH_LONG).show();

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
				+ cursor.getString(ColumnTask.KEY_INDEX.priority)
				+ "cardID="+card.getId();

		expand.setTitle(aa);
		card.addCardExpand(expand);

		// Add Thumbnail to card
		final MyCardThumbnail thumb = new MyCardThumbnail(getContext());
		thumb.setDrawableResource(card.resourceIdThumb);
		card.addCardThumbnail(thumb);

		// Set on Click listener
		card.setOnClickListener(new Card.OnCardClickListener() {
			@Override
			public void onClick(Card card, View view) {

				ShowLongClickMenu(cursor.getInt(0));
				

			}
		});

		card.setOnLongClickListener(new Card.OnLongCardClickListener() {
			@Override
			public boolean onLongClick(Card card, View view) {
				// TODO Auto-generated method stubs

				mReadCardOnClick.readIt(card.getId());

				return false;

			}
		});

		return card;

	}

	// private void setCardFromCursor(MyCursorCard card, Cursor cursor) {
	//
	// setCardFromCursor(card, cursor);
	//
	// }

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

	/***********************/
	/** Class MyCardHeader **/
	/***********************/
	// implment the clickable card thumbnail.
	class MyCardHeader extends CardHeaderView {

		@Override
		@SuppressLint("NewApi")
		protected void setupButtons() {
			// TODO Auto-generated method stub
			super.setupButtons();
		}

		@Override
		protected void setupInnerView() {
			// TODO Auto-generated method stub
			super.setupInnerView();

		}

		@Override
		public OnClickListener getOnClickExpandCollapseActionListener() {
			// TODO Auto-generated method stub
			ViewToClickToExpand viewToClickToExpand = ViewToClickToExpand
					.builder().highlightView(true)
					.setupView(getImageButtonOther());
			getCardFromCursor(getCursor()).setViewToClickToExpand(
					viewToClickToExpand);

			return super.getOnClickExpandCollapseActionListener();
		}

		@Override
		public ImageButton getImageButtonOverflow() {
			// TODO Auto-generated method stub
			return super.getImageButtonOverflow();
		}

		@Override
		public ImageButton getImageButtonExpand() {
			// TODO Auto-generated method stub
			return super.getImageButtonExpand();
		}

		@Override
		public ImageButton getImageButtonOther() {
			// TODO Auto-generated method stub
			return super.getImageButtonOther();
		}

		public MyCardHeader(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

	}

	/************************/
	/** Class MyCursorCard **/
	/************************/
	class MyCursorCard extends Card {

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

	private void removeCard(int id) {

		// Use this code to delete items on DB
		ContentResolver resolverTask = getContext().getContentResolver();

		long taskDeleted = 0 ;
		resolverTask.delete(ColumnTask.URI,
				ColumnTask.KEY._id + " = ? ",
				//new String[] { this.getCardFromCursor(getCursor()).getId() });
		new String[] { String.valueOf(id) });

		// Alert PArt
		ContentResolver resolverAlert = getContext().getContentResolver();
		Cursor rowIDs=resolverAlert.query(ColumnAlert.URI, 
				null,
				ColumnAlert.KEY.task_id + " = ? ",
				new String[] { String.valueOf(id) }, 
				ColumnAlert.DEFAULT_SORT_ORDER);
		int rowCounter=rowIDs.getCount();
		rowIDs.moveToFirst();
		String[] IDs = {""};
		for (int i = 0; i < rowIDs.getCount(); i++) {
			IDs[i]=rowIDs.getString(i).toString();
			MyDebug.MakeLog(0, "rowOrder="+i+
					",rowID="+IDs);
			rowIDs.moveToNext();
		}

		MyDebug.MakeLog(0, "task_id="+ColumnAlert.KEY.task_id+
				",ColumnAlert rows="+rowCounter);
		long alertDeleted = 0;
		if(rowCounter > 0){
			alertDeleted =resolverAlert.delete(ColumnAlert.URI,
					ColumnAlert.KEY.task_id + " = ? ",
					IDs);
		}

		MyDebug.MakeLog(0, "taskDeleted="+taskDeleted+
				",alertDeleted="+alertDeleted);
		this.notifyDataSetChanged();


	}



	private AlertDialog ShowLongClickMenu(final int id) {

		return new AlertDialog.Builder(getContext())
		.setTitle("請選擇...")
		.setItems(R.array.Array_Task_List_Card_Long_Clcik_String,
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,
					int which) {

				switch (which) {
				case 0:// 修改
					mReadCardOnClick.readIt(String.valueOf(id));
					break;
				case 1:// 刪除
					removeCard(id);

					break;
				case 2:// 提高優先 - 提前一天

					break;
				case 3:// 降低優先 - 延後一天

					break;
				case 5:// 分享

					break;
				}
			}
		}).show();

	}

}
