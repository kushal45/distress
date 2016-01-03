package notifactionspecial.com.myapplication;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by USER on 13-Nov-15.
 */
public class INsertDataByPreference extends Activity  {
    ListPreference listpref;
    EditText text1;
    Button buttonmessage;
    Context contextglob;
    String contacts[];
    MyeuseCustomAdapter myPhoneadapter;
    ArrayList<MyReuseClass> myReuseList;
    MyReuseClass myReuseClass;
    ;
    StringBuilder builder;
   static String  message;
    int requestco = 78;
    private static final String My_Pref_File = "Myname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_message);
        text1 = (EditText) findViewById(R.id.editTextMessage);
        buttonmessage = (Button) findViewById(R.id.buttonMessage);
        buttonmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                message=text1.getText().toString();
                Intent intent=new Intent();
                intent.putExtra("MESSAGE",message);
                setResult(requestco,intent);
                finish();//finishing activity
            }
        });

    }

}









