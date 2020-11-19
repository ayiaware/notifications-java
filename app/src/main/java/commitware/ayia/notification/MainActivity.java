package commitware.ayia.notification;

import android.app.AlarmManager;
import android.app.DatePickerDialog;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.os.SystemClock;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import commitware.ayia.notification.mock.MockData;
import commitware.ayia.notification.model.Channel;
import commitware.ayia.notification.model.Member;
import commitware.ayia.notification.receiver.MyNotificationPublisher;
import commitware.ayia.notification.util.NotificationUtil;


public class MainActivity extends AppCompatActivity {

    private NotificationManagerCompat mNotificationManagerCompat;

    Button btnDate ;

    private static Calendar myCalendar;

    private RelativeLayout mMainRelativeLayout;

    int uniqueId = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        btnDate = findViewById(R.id.buttonDate);

        mMainRelativeLayout = (RelativeLayout) findViewById(R.id.mainRelativeLayout);

        mNotificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());

        myCalendar = Calendar.getInstance(Locale.getDefault());
        //create on firstStart

        Channel channel = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

            channel = new Channel("channel_member_1","Subscription Notification 1",
                    "Member Subscription Expiration Notifications", NotificationManager.IMPORTANCE_DEFAULT,
                    true, NotificationCompat.VISIBILITY_PRIVATE);
        }

        final String notificationChannelId = NotificationUtil.createNotificationChannel(MainActivity.this, channel);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean areNotificationsEnabled = mNotificationManagerCompat.areNotificationsEnabled();

                if (!areNotificationsEnabled) {
                    // Because the user took an action to create a notification, we create a prompt to let
                    // the user re-enable notifications for this application again.
                    Snackbar snackbar = Snackbar
                            .make(
                                    mMainRelativeLayout,
                                    "You need to enable notifications for this app",
                                    Snackbar.LENGTH_LONG)
                            .setAction("ENABLE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Links to this app's notification settings
                                    openNotificationSettingsForApp();
                                }
                            });
                    snackbar.show();
                    return;
                }

                for (Member member: new MockData().getMemberList())
                {
                    scheduleNotification(member.getName(), member.getExpDate(), member.getId(), notificationChannelId);
                }

        }
        });

        final DatePickerDialog.OnDateSetListener dateSetListenerReturn = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                // set new date
                c.set(year, monthOfYear, dayOfMonth); // set new date

                String myFormat = "dd/MM/yy" ; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat , Locale.getDefault ()) ;

                long scheduleExpiry = System.currentTimeMillis()-c.getTimeInMillis() ;

                Toast.makeText(MainActivity.this,sdf.format(c.getTime()),Toast.LENGTH_LONG).show();

                Channel channel = null;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

                    channel = new Channel("channel_member_2","Subscription Notification 2",
                            "Member Subscription Expiration Notifications", NotificationManager.IMPORTANCE_DEFAULT,
                            true, NotificationCompat.VISIBILITY_PRIVATE);
                }

                final String notificationChannelId = NotificationUtil.createNotificationChannel(MainActivity.this, channel);

                scheduleNotification("Member "+uniqueId, scheduleExpiry, uniqueId, notificationChannelId);

                uniqueId++;
            }
        };

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this,dateSetListenerReturn,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DATE));
                dialog.getDatePicker().setMinDate(new Date().getTime());
                dialog.show();

            }
        });



    }




    private void openNotificationSettingsForApp() {
        // Links to this app's notification settings.
        Intent intent = new Intent();
        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
        intent.putExtra("app_package", getPackageName());
        intent.putExtra("app_uid", getApplicationInfo().uid);
        startActivity(intent);
    }



    private void scheduleNotification(String notification ,long delay, int id, String channelID) {

        Intent notificationIntent = new Intent(this, MyNotificationPublisher.class);

        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, id);

        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_CHANNEL_ID, channelID);

        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_CONTENT, notification);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
        }

    }

    /*
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet (DatePicker view , int year , int monthOfYear , int dayOfMonth) {
                myCalendar .set(Calendar. YEAR , year) ;
                myCalendar .set(Calendar. MONTH , monthOfYear) ;
                myCalendar .set(Calendar. DAY_OF_MONTH , dayOfMonth) ;
                updateLabel() ;
            }
        } ;



    public void setDate (View view) {
            new DatePickerDialog(MainActivity. this, date ,
                    myCalendar .get(Calendar. YEAR ) ,
                    myCalendar .get(Calendar. MONTH ) ,
                    myCalendar .get(Calendar. DAY_OF_MONTH )
            ).show() ;
        }
*/



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
