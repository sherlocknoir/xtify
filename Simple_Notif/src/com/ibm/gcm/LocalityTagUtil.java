package com.ibm.gcm;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import com.xtify.sdk.api.XtifyLocation;
import com.xtify.sdk.api.XtifySDK;
import com.xtify.sdk.api.XtifyLocation.LocationUpdateListener;

public class LocalityTagUtil {
	private static final String PREFS_NAME = "lastLocalityFile";
	private static final String LAST_LOCALITY = "lastLocality";

 	public static void update(final Context context) {
		if (XtifySDK.getXidKey(context) != null) {
			LocationUpdateListener locationUpdateListener = new LocationUpdateListener() {

 				@Override
				public void onUpdateComplete(boolean success, Location location) {
					synchronized (LocalityTagUtil.class) {
						if (success && context != null) {
							Geocoder gcd = new Geocoder(context, Locale.FRANCE);
							try {
								List<Address> addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
								if (addresses.size() > 0 && addresses.get(0).getLocality() != null) {
									String locality = addresses.get(0).getLocality();
									String PostalCode = addresses.get(0).getPostalCode();
									String Country = addresses.get(0).getCountryName();
									
										XtifySDK.addTag(context, "city:" + locality);
										XtifySDK.addTag(context, "PostalCode:" + PostalCode);
										XtifySDK.addTag(context, "Country:" + Country);
										context.getSharedPreferences(PREFS_NAME, 0).edit().putString(LAST_LOCALITY, locality).commit();
									}
								
							} catch (IOException e) {
							}
						}
					}
				}
			};
			int timeout = 2 * 60 * 1000;
			new XtifyLocation(context).updateLocation(timeout, locationUpdateListener);
		}
	}
}