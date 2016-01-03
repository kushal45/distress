package notifactionspecial.com.myapplication;

import android.view.View;
import android.widget.QuickContactBadge;
import android.widget.TextView;

/**
 * Created by USER on 08-Nov-15.
 */

 public class ViewHolderReuse{
    TextView title;
    TextView desc;

    QuickContactBadge icon;

    ViewHolderReuse(View v) {
        title = (TextView) v.findViewById(R.id.textView);
        desc = (TextView) v.findViewById(R.id.textView2);
        icon= (QuickContactBadge) v.findViewById(R.id.iconPhotoUri);
    }
}


