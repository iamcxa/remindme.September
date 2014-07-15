/*
 * 
 */
package me.iamcxa.remindme;


import me.iamcxa.remindme.cardfragment.ListCursorCardFragment;
import me.iamcxa.remindme.editor.TaskEditorTab;
import me.iamcxa.remindme.provider.LocationGetter;
import me.iamcxa.remindme.service.TaskSortingService;
import android.R.integer;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;   
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.StaticLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ShareActionProvider;
import android.widget.Toast;


/**
 * @author cxa Main Activity
 */
public class RemindmeMainActivity extends FragmentActivity {
	/**********************/
	/** Variables LOCALE **/
	/**********************/
	// Used in savedInstanceState
	private static ProgressDialog psDialog;
	private int threadID;
	private static android.app.FragmentManager fragmentManager;
	public static int layoutID = 0;

	//ListCursorCardFragment cardview = new ListCursorCardFragment();

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mPlanetTitles;

	/**********************/
	/** onCreate LOCALE **/
	/**********************/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLoding("ON");

		RemindmeVar.mPreferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());

		// 設定：layout視圖
		setContentView(R.layout.activity_main);
		setNavagationDrawer(savedInstanceState);
		
		RemindmeVar.debugMsg(0, "=========================");
		threadID = android.os.Process.getThreadPriority(android.os.Process
				.myTid());
		RemindmeVar.debugMsg(1, threadID + " onCreate");

		new Thread(new Runnable() {
			@Override
			public void run() {
				threadID = android.os.Process
						.getThreadPriority(android.os.Process.myTid());
				RemindmeVar.debugMsg(1, threadID + " pre-setViewComponent");
				// 設定：頁面元件
				setViewComponent();
			}
		}).start();

		StartService();

		if (savedInstanceState != null) {
			//setFragment(3, 0);
			setLoding("OFF");
		}
	}

	/**********************/
	/** onResume LOCALE **/
	/**********************/
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	/**********************/
	/** onSaveInstanceState **/
	/**********************/
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
	private void setNavagationDrawer(Bundle savedInstanceState){
		mPlanetTitles = getResources().getStringArray(R.array.drawer_array_CHT);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		//mTitle =mPlanetTitles[mDrawerIndex];
		mDrawerTitle = getTitle();

		// set a custom shadow that overlays the main content when the drawer opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mPlanetTitles));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(
				this,                  /* host Activity */
				mDrawerLayout,         /* DrawerLayout object */
				R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
				R.string.drawer_open,  /* "open drawer" description for accessibility */
				R.string.drawer_close  /* "close drawer" description for accessibility */
				) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(0);
		}
	}

	/**********************/
	/** StartService LOCALE **/
	/**********************/
	public void StartService() {
		if (RemindmeVar.IS_SERVICE_ON()) {
			Intent intent = new Intent(this, TaskSortingService.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startService(intent);
		}
	}

	/*************************/
	/** onCreateOptionsMenu **/
	/*************************/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 由main_activity_actionbar.xml讀取按鈕資訊
		getMenuInflater().inflate(R.menu.main_activity_actionbar, menu);

		// 定義：搜尋按鈕
		MenuItem actionSearch = menu.findItem(R.id.action_search);
		actionSearch.setVisible(false);
		// 設定：點擊接收器
		// mSearchView = (SearchView) actionSearch.getActionView();
		// setSearchView(actionSearch);

		// 定義：新增按鈕
		MenuItem actionAdd = menu.findItem(R.id.action_add);
		// 設定：點擊接收器
		actionAdd.setOnMenuItemClickListener(btnActionAddClick);

		// 定義：重整按鈕
		MenuItem actionRefresh = menu.findItem(R.id.action_refresh);
		// 設定：點擊接收器
		actionRefresh.setOnMenuItemClickListener(btnRefreshClick);

		/*
		 * // 定義：分享按鈕 MenuItem actionShare = menu.findItem(R.id.action_share);
		 * 設定：play上之app連結/分享給朋友的顯示字串 String playStoreLink =
		 * " https://play.google.com/store/apps/details?id=" + getPackageName();
		 * String yourShareText = getString(R.string.share_this) +
		 * playStoreLink; // 設定：分享intent Intent shareIntent =
		 * ShareCompat.IntentBuilder.from(this)
		 * .setType("text/plain").setText(yourShareText).getIntent(); //
		 * 設定：點擊提供者 mShareActionProvider = (ShareActionProvider) actionShare
		 * .getActionProvider();
		 * mShareActionProvider.setShareIntent(shareIntent);
		 */

		// 定義：設置按鈕
		MenuItem actionPref = menu.findItem(R.id.action_settings);
		// 設定：點擊接收器
		actionPref.setOnMenuItemClickListener(btnPrefClick);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action buttons
		switch(item.getItemId()) {
		case R.id.action_websearch:
			// create intent to perform web search for this planet
			Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
			intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
			// catch event that there's no activity to handle intent
			if (intent.resolveActivity(getPackageManager()) != null) {
				startActivity(intent);
			} else {
				Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	/**********************/
	/** setViewComponent **/
	/**********************/
	private void setViewComponent() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				threadID = android.os.Process
						.getThreadPriority(android.os.Process.myTid());
				RemindmeVar.debugMsg(1, threadID + " setViewComponent");


				setLoding("OFF");
			}
		}).start();
	}

	/**********************/
	/** setFragment **/
	/**********************/
	// To ensure the target frame will show the right fragment.
	public void setFragment(final int MODE, final int FragmentPosition) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				threadID = android.os.Process
						.getThreadPriority(android.os.Process.myTid());
				RemindmeVar.debugMsg(1, threadID + " setFragment "
						+ FragmentPosition);

				fragmentManager = getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager
						.beginTransaction();

				// 設定卡片
//				ListCursorCardFragment.setSelection( RemindmeVar.TaskCursor.KEY.PRIORITY
//						+ " DESC");
				//	ListCursorCardFragmentTime.sortOrder = CommonUtils.TaskCursor.KeyColumns.EndDate;
				//ListCursorCardFragmentLocal.sortOrder = CommonUtils.TaskCursor.KeyColumns.Distance;
//				ListCursorCardFragment.setSelection(null);
				//ListCursorCardFragmentTime.selection = "TaskLocationName = \"\"";
				//ListCursorCardFragmentLocal.selection = "TaskLocationName <> \"\"";

				// 模式 - 移除或新增Fragment
				switch (MODE) {
				case 0:
					// Remove Fragment
					switch (FragmentPosition) {
					case 0:
						//fragmentTransaction.remove(cardview);
						break;
					case 1:
						//	fragmentTransaction.remove(cardview1);
						break;
					case 2:
						//fragmentTransaction.remove(cardview2);
						break;
					}
					fragmentTransaction.commit();
					break;

				case 1:
					// Replace Fragment
					switch (FragmentPosition) {
					case 0:
						//						fragmentTransaction.replace(R.id.fragment_local,
						//								cardview2, "cardview2");
						break;
					case 1:
						//						fragmentTransaction.replace(R.id.fragment_time,
						//								cardview1, "cardview1");
						break;
					case 2:
						//fragmentTransaction.replace(R.id.fragment_main,cardview, "cardview");
						break;
					}
					fragmentTransaction.commit();
					break;
				case 3:
					// int i;
					// for (i = 0; i < (adapter.getCount())-1; i++) {
					// setFragment(0, i);
					// setFragment(1, i);
					// }
					break;
				}

				setLoding("OFF");
			}
		}).start();

	}

	/**********************/
	/** setViewComponent **/
	/**********************/
	private void setDataToEditor() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				threadID = android.os.Process
						.getThreadPriority(android.os.Process.myTid());
				RemindmeVar.debugMsg(1, +threadID + " setDatatoEditor");

			}
		}).start();
	}

	/************************/
	/** set Lode dialog On **/
	/************************/
	private void setLoding(String MODE) {
		if (MODE == "ON") {
			psDialog = ProgressDialog.show(this, "", "......");
			/*
			 * if (!(pBar == null)) { pBar.setVisibility(View.VISIBLE); }
			 */
		} else if (MODE == "OFF") {
			psDialog.dismiss();
			/*
			 * if (!(pBar == null)) { pBar.setVisibility(View.GONE); }
			 */
		}

	}

	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectItem(position);
		}
	}
	
	private void selectItem(int position) {
		// update the main content by replacing fragments
		//Fragment fragment = new RemindmeFragment();

		Fragment fragment = new ListCursorCardFragment();
		Bundle args = new Bundle();
		//args.putInt(RemindmeFragment.MyFragment.ARG_PLANET_NUMBER, position);
	
		args.putInt(ListCursorCardFragment.FILTER_STRING,(position));
		fragment.setArguments(args);

		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

		Toast.makeText(getBaseContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
		
		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		this.setTitle(mPlanetTitles[position]);
		this.getActionBar().setTitle(mPlanetTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
		mTitle =mPlanetTitles[position];
		
	}
	
	/**********************/
	/** btnActionAddClick **/
	/**********************/
	private MenuItem.OnMenuItemClickListener btnActionAddClick = new MenuItem.OnMenuItemClickListener() {
		@Override
		public boolean onMenuItemClick(MenuItem item) {
			// Toast.makeText(getApplication(), item.getTitle(),
			// Toast.LENGTH_SHORT).show();
			Intent intent = new Intent();
			intent.setClass(getApplication(), TaskEditorTab.class);
			startActivity(intent);
			return false;
		}
	};
	/************************/
	/** btnRefreshAddClick **/
	/************************/
	private MenuItem.OnMenuItemClickListener btnRefreshClick = new MenuItem.OnMenuItemClickListener() {
		@Override
		public boolean onMenuItemClick(MenuItem item) {
			Toast.makeText(getApplication(), item.getTitle(),
					Toast.LENGTH_SHORT).show();

			LocationGetter UpdataLocation = new LocationGetter(getApplicationContext());
			UpdataLocation.UpdateOncePriority();

			ListCursorCardFragment.getmAdapter().notifyDataSetChanged();

			return false;
		}
	};

	/**********************/
	/** menuActionPrefClick **/
	/**********************/
	private MenuItem.OnMenuItemClickListener btnPrefClick = new MenuItem.OnMenuItemClickListener() {
		@Override
		public boolean onMenuItemClick(MenuItem item) {
			// Display the fragment as the main content.

			Toast.makeText(getApplication(), item.getTitle(),
					Toast.LENGTH_SHORT).show();

			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), RemindmePreference.class);
			startActivity(intent);

			return false;
		}
	};


}