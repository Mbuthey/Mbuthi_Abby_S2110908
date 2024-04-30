package org.me.gcu.s2110908;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import org.me.gcu.s2110908.adapter.WeatherForecastPagerAdapter;
import org.me.gcu.s2110908.fragments.WeatherForecastFragment;
import org.me.gcu.s2110908.services.FetchObservationDataService;
import org.me.gcu.s2110908.services.WeatherFetchService;

import java.util.Calendar;

public class MainActivity2 extends AppCompatActivity implements WeatherFetchService.WeatherFetchListener, FetchObservationDataService.WeatherFetchListener {
    private WeatherForecastPagerAdapter adapter;
    private ViewPager viewPager;
    private WeatherForecastFragment currentFragment;
    WeatherFetchService weatherFetchService;
    FetchObservationDataService fetchObservationDataService;
    String selectedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        scheduleWeatherFetch();

        viewPager = findViewById(R.id.viewPager);

        adapter = new WeatherForecastPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        selectedLocation = getIntent().getStringExtra("selectedLocation");
        if (selectedLocation != null) {
            // Set the current fragment to the selected location
            for (int i = 0; i < Constants.getLocations().length; i++) {
                if (Constants.getLocations()[i].equals(selectedLocation)) {
                    viewPager.setCurrentItem(i);
                    break;
                }
            }
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                System.out.println("onPageScrolled");
//                String urlSource = Constants.getUrls().get(position);
                selectedLocation = Constants.getLocations()[position];
                String urlSource = Constants.getLocationToUrl().get(selectedLocation);
                currentFragment = (WeatherForecastFragment) adapter.instantiateItem(viewPager, position);
//                WeatherForecastFragment fragment = (WeatherForecastFragment) adapter.getItem(position);
                int imageResource = 0;
                System.out.println("position: " + position);
                System.out.println("Location: " + Constants.getLocations()[position]);
                String location = Constants.getLocations()[position];
                switch (location) {
                    case "Glasgow":
                        imageResource = R.drawable.glasgow;
                        break;
                    case "Mauritius":
                        imageResource = R.drawable.mauritius;
                        break;
                    case "London":
                        imageResource = R.drawable.london;
                        break;
                    case "New York":
                        imageResource = R.drawable.newyork;
                        break;
                    case "Oman":
                        imageResource = R.drawable.oman;
                        break;
                    case "Bangladesh":
                        imageResource = R.drawable.bangladesh;
                        break;
                    default:
                        imageResource = R.drawable.pamplemousses;
                        break;
                }
                Drawable newBackground = new LayerDrawable(new Drawable[] {
                        new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), imageResource)),
                        new GradientDrawable(
                                GradientDrawable.Orientation.BOTTOM_TOP,
                                new int[] {0x66000000, 0xCC000000}
                        )
                });

                currentFragment.getView().setBackground(newBackground);

                if (weatherFetchService != null && weatherFetchService.getStatus() == AsyncTask.Status.RUNNING) {
                    weatherFetchService.cancel(true);
                }
                // Create and execute a new WeatherFetchService
                weatherFetchService = new WeatherFetchService(MainActivity2.this);
                weatherFetchService.execute(urlSource);

                if (fetchObservationDataService != null && fetchObservationDataService.getStatus() == AsyncTask.Status.RUNNING) {
                    fetchObservationDataService.cancel(true);
                }

                // Create and execute a new FetchObservationDataService
                fetchObservationDataService = new FetchObservationDataService(MainActivity2.this);
//                String observationUrl = Constants.getObservationUrls().get(position);

                String observationUrl = Constants.getObservationUrls().get(selectedLocation);
                fetchObservationDataService.execute(observationUrl);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    @Override
    public void onWeatherFetchComplete(String weatherData) {
        if (currentFragment != null) {
            // pass data and current context to fragment
            currentFragment.onWeatherDataFetched(weatherData, this);
        }
    }

    @Override
    public void onObservationDataFetchComplete(String weatherData) {
        if (currentFragment != null) {
            // pass data and current context to fragment
            currentFragment.onObservationDataFetched(weatherData, this);
        }
    }

    public void scheduleWeatherFetch() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(this, MainActivity2.class), PendingIntent.FLAG_IMMUTABLE);

        // Set the schedule at 8 am and 8 pm every day
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        // Set the alarm to repeat every 12 hours
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60 * 12, pendingIntent);

        calendar.set(Calendar.HOUR_OF_DAY, 20);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60 * 12, pendingIntent);

    }
}