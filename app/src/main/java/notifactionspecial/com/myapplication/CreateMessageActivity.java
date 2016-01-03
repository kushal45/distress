package notifactionspecial.com.myapplication;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.telephony.SmsManager;
import android.util.ArraySet;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by USER on 11-Nov-15.
 */
@TargetApi(Build.VERSION_CODES.M)
public class CreateMessageActivity  extends FragmentActivity  implements  Communicator{
    EditText text;
    int positionDelete;
    ListView listView;
    ArrayList<String> listmessage = new ArrayList<String>();
    ArrayList<MyReuseClass> myreuseList = new ArrayList<MyReuseClass>();
    SharedPreferences preferences;
    MyReuseClass myReuseClass;
    String listArray[];
    FloatingActionButton fabImageButton;
    String PREFERENCE_mESSAGE = "mylistmessagepref";
    boolean SavedPreferennce = false;
    int requestco = 78;




    static String datamessage;
    ArrayAdapter<String> adapterList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_message);





        // convert to simple array
       // popUpContents = new String[sortList.size()];
    //    sortList.toArray(popUpContents);
        listView = (ListView) findViewById(R.id.listmessage);
        readItems(this);
        fabImageButton = (FloatingActionButton) findViewById(R.id.fab);

        registerForContextMenu(listView);

        fabImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //here
                startActivityForResult(new Intent(CreateMessageActivity.this, INsertDataByPreference.class), requestco);
            }
        });
        if (datamessage != null) {

            writeItems(this);
adapterList.remove(adapterList.getItem(positionDelete));
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.global, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId())
        {
            case R.id.action_settings:
                Delete(info.position);
                break;
            case R.id.Sendsms:
           // SendMessage(info.position);
              FragmentManager fragmentManager=getSupportFragmentManager();
                MyDialog myDialog=new MyDialog();
                myDialog.show(fragmentManager,"Mydialog");

                break;
            case R.id.SortSms:
                break;

        }


        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == resultCode) {
            datamessage = data.getStringExtra("MESSAGE");
            init(this);
            Toast.makeText(getApplicationContext(), datamessage, Toast.LENGTH_LONG).show();
        }

    }


    FileInputStream fileInputStream = null;

    private void readItems(Context context) {
        //return path where files can be created for android

        try {
            fileInputStream = openFileInput("tomessage.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            String read;
            String readBefore = null;

            while ((read = reader.readLine()) != null) {
                if (!(read.equals(readBefore)))
                    listmessage.add(read);
                readBefore = read;
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Toast.makeText(getApplicationContext(), listmessage.toString(), Toast.LENGTH_LONG).show();
            adapterList = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, listmessage);
            listView.setAdapter(adapterList);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    FileOutputStream fileOut = null;

    private void writeItems(Context context) {


        //return path where files can be created for android
        File filesDir = getExternalFilesDir("kushal");
        File todoFile = new File(filesDir, "tomessage.txt");

        try {
            if (datamessage != null) {

                fileOut = openFileOutput("tomessage.txt", Context.MODE_APPEND);
                fileOut.write(datamessage.getBytes());
                fileOut.write(System.getProperty("line.separator").getBytes());
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    public void Delete(int position) {
        FileOutputStream fileOutputStream = null;
        listmessage.remove(position);
        adapterList.notifyDataSetChanged();
        deleteFile("tomessage.txt");
        try {
            fileOutputStream = openFileOutput("tomessage.txt", Context.MODE_APPEND);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (String s : listmessage) {
            try {
                assert fileOutputStream != null;
                fileOutputStream.write(s.getBytes());
                fileOutputStream.write(System.getProperty("line.separator").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    ArrayList<String> MobNumber = new ArrayList<>();

    public void SendMessage(int position) {

        SharedPreferences savepref = PreferenceManager.getDefaultSharedPreferences(this);
        String myJsonArray = savepref.getString("mycontactarray", "Sorrynot availabel");
        try {
            JSONArray jsonArray = new JSONArray(myJsonArray);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = new JSONObject();
                obj = jsonArray.getJSONObject(i);
                String title = obj.getString("Title");

                String description = obj.getString("Description");
                MobNumber.add(description);


            }
            int size = MobNumber.size();

            for (int i = 0; i < size; i++) {

                String tempMobileNumber = MobNumber.get(i);
                sendSMS(tempMobileNumber, listmessage.get(positionDelete));
            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e + " ", Toast.LENGTH_LONG).show();
        }


    }

    protected void sendSMS(String tempMobileNumber, String message) {

        String smsMessage = message;
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(tempMobileNumber, null, smsMessage, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "Sending SMS failed.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void init(Context context) {

        listmessage.add(datamessage);

        adapterList = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, listmessage);
        listView.setAdapter(adapterList);


    }


    @Override
    public void Sendmessage(int which) {
        SharedPreferences savepref = PreferenceManager.getDefaultSharedPreferences(this);
        String myJsonArray = savepref.getString("mycontactarray", "Sorrynot availabel");
        try {
            JSONArray jsonArray = new JSONArray(myJsonArray);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = new JSONObject();
                obj = jsonArray.getJSONObject(i);
                String title = obj.getString("Title");

                String description = obj.getString("Description");
                MobNumber.add(description);


            }
            int size = MobNumber.size();

            for (int i = 0; i < size; i++) {

                String tempMobileNumber = MobNumber.get(i);
                sendSMS(tempMobileNumber, listmessage.get(positionDelete));
            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e + " ", Toast.LENGTH_LONG).show();
        }

    }
}