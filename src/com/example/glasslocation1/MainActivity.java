package com.example.glasslocation1;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.widget.Toast;

import com.google.android.glass.app.Card;

public class MainActivity extends Activity  
{
	Card c;
	LocationManager locationManager;
	public static String DEBUG_TAG = MainActivity.class.getSimpleName();
	
	double lat, lon;
	String add;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		getLocation();
		
		c = new Card(this);
		c.setText("Hello");
		setContentView(c.getView());

	}


	public void getLocation()
	{
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		String provider = mLocationManager.getBestProvider(criteria, true);
		boolean isEnabled = mLocationManager.isProviderEnabled(provider);
		if(isEnabled) 
		{
			LocationListener locationListener = new LocationListener() 
			{
				@Override
				public void onLocationChanged(Location location)
				{
					if (location != null)
					{
						Geocoder geocoder = new Geocoder(MainActivity.this.getBaseContext(),
								Locale.getDefault());

						List<Address> addresses = null;

						try 
						{
							addresses = geocoder.getFromLocation(
									location.getLatitude(),
									location.getLongitude(), 1);
							add = addresses.get(0).getCountryName();
							
							lat = location.getLatitude();
							lon = location.getLongitude();
							
							Log.d(DEBUG_TAG, "lat:"+ location.getLatitude());
							Log.d(DEBUG_TAG, "long:"+ location.getLongitude());
							Log.d(DEBUG_TAG, "addresses:" + addresses);
							//c.setFootnote("lat:"+location.getLatitude());
							//c.setFootnote("\nlong:"+location.getLongitude());
						} 
						catch (IOException e)
						{
							System.out.println(e);
							e.printStackTrace();
						}

						if (addresses != null && addresses.size() != 0)
						{
							// This is how you will get Postal Code
//							 String postalCode = addresses.get(0).getPostalCode();
//							 Log.d(DEBUG_TAG, "postalCode " + postalCode);
							// Now, further you could use this postalCode to
							// make a REST API call for getting Weather etc..
						}

					}

				}

				public void onStatusChanged(String provider, int status,
						Bundle extras)
				{

				}

				@Override
				public void onProviderEnabled(String provider) 
				{

				}

				@Override
				public void onProviderDisabled(String provider) 
				{

				}

			};

			// Register the listener with the Location Manager to receive
			// location updates
			mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1,
					locationListener);
			
//			c.setText("lat:"+a);
		}
	}
	
}
