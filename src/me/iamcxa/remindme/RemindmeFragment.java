package me.iamcxa.remindme;

import java.util.Locale;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * Fragment that appears in the "content_frame", shows a planet
 */

public class RemindmeFragment extends Fragment {
	public static class MyFragment extends Fragment {
		public static final String ARG_PLANET_NUMBER = "planet_number";

		public MyFragment() {
			// Empty constructor required for fragment subclasses
			
		}

		@Override
		public View onCreateView(
				LayoutInflater inflater,
				ViewGroup container,
				Bundle savedInstanceState) {
			
			View rootView = inflater.inflate(R.layout.activity_main, container, false);
			
			int i = getArguments().getInt(ARG_PLANET_NUMBER);
			String planet = getResources().getStringArray(R.array.drawer_array_CHT)[i];
			getActivity().setTitle(planet);

//			int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
//					"drawable", getActivity().getPackageName());
			//((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
		
			
			
			
			
			
			return rootView;
		}
	}
}

