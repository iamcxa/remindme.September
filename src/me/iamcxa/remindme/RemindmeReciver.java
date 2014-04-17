package me.iamcxa.remindme;

<<<<<<< HEAD
import me.iamcxa.remindme.provider.AlarmProvider;
=======
>>>>>>> g
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author iamcxa 定時提醒廣播
 */
public class RemindmeReciver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {

		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

<<<<<<< HEAD
		intent.setClass(context, AlarmProvider.class);

		context.startActivity(intent);
		
		
=======
		intent.setClass(context, RemindmeReciver.class);

		context.startActivity(intent);
>>>>>>> g

	}
}