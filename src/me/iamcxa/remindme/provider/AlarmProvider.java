package me.iamcxa.remindme.provider;

<<<<<<< HEAD
import me.iamcxa.remindme.CommonUtils;
=======
>>>>>>> g
import me.iamcxa.remindme.R;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
<<<<<<< HEAD
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
=======
import android.net.Uri;
import android.os.Bundle;
>>>>>>> g
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author iamcxa 提醒方法
 */
public class AlarmProvider extends Activity {
	public static final int ID = 1;
<<<<<<< HEAD
	
=======

>>>>>>> g
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

<<<<<<< HEAD
		super.onCreate(savedInstanceState);		
=======
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alertdialoglayout);
>>>>>>> g

		setContentView(R.layout.alertdialoglayout);
		// 取得Button﹜TextView實例
		Button btn = (Button) findViewById(R.id.buttonSubmit);
		TextView tv = (TextView) findViewById(R.id.textView1);

		// 取得NotificationManager實例
		String service = Context.NOTIFICATION_SERVICE;
		final NotificationManager nm = (NotificationManager) getSystemService(service);
		// 實例化Notification
		Notification n = new Notification();
		// 設定顯示提示訊息，同時顯示在狀態列
		String msg = getIntent().getStringExtra("msg");
		// 顯示時間
		n.tickerText = msg;
		tv.setText(msg);

		// 設定語音提示
<<<<<<< HEAD
		CommonUtils.mPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		CommonUtils.debugMsg(0, this.getFilesDir().getAbsolutePath()+"/fallbackring.ogg");
		n.sound = Uri.parse(CommonUtils.mPreferences.getString("ringtonePref", this.getFilesDir().getAbsolutePath()+"/fallbackring.ogg"));
		
=======
		n.sound = Uri.parse("file:///sdcard/fallbackring.ogg");
>>>>>>> g
		// 發出通知
		nm.notify(ID, n);
		// 取消通知
		btn.setOnClickListener(new OnClickListener() {
			// @Override
			@Override
			public void onClick(View v) {
				nm.cancel(ID);
				finish();
			}
		});

	}

}
