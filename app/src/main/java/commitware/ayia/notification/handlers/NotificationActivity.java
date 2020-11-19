package commitware.ayia.notification.handlers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import commitware.ayia.notification.receiver.MyNotificationPublisher;
import commitware.ayia.notification.R;
import commitware.ayia.notification.mock.MockData;
import commitware.ayia.notification.model.Member;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        TextView tvID  = findViewById(R.id.tvId);
        TextView tvName = findViewById(R.id.tvName);
        TextView tvSub = findViewById(R.id.tvSubscription);



        int id = getIntent().getIntExtra(MyNotificationPublisher.NOTIFICATION_ID, 0);

        Toast.makeText(this, ""+id,Toast.LENGTH_SHORT).show();

        Member currMember = null;

        for(Member member: new MockData().getMemberList())
        {
            if(member.getId()==id)
            {
                currMember = member;
            }
        }

        if (currMember != null) {
            String getId = String.valueOf(currMember.getId());
            tvID.setText(getId);
            tvName.setText(currMember.getName());
            tvSub.setText(currMember.getSubscription());
        }


        // Cancel Notification
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        assert notificationManager != null;
        notificationManager.cancel(id);


    }
}
