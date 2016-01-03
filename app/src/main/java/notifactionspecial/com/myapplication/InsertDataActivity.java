package notifactionspecial.com.myapplication;

import android.app.Activity;
import   android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;

import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;

import android.widget.QuickContactBadge;
import android.widget.SearchView;
import android.widget.Spinner;

import android.widget.Toast;
import android.util.Log;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;


/**
 * Created by USER on 13-Nov-15.
 */
public class InsertDataActivity extends FragmentActivity
        implements AdapterView.OnItemSelectedListener{
    Cursor phones;
    int SpinnerPosition = 0;
ListView hospital;
    SearchView searchView;
    Boolean userIsInteracting=false;
    MyeuseCustomAdapter myeuseCustomAdapter;

    ArrayList<MyReuseClass> myreuseList = new ArrayList<MyReuseClass>();
    String phonenumber;
    ArrayList<String> sortList = new ArrayList<String>();
    String contacts[];
    ImageLoader imageloading;
    Spinner SpinnerCOntacts;
    MyReuseClass myReuseClass;
    String popUpContents[];
    ImageLoader imageload;
    ArrayAdapter<String> SpinnerAdapter;
    String itemArr[];


    ListViewContactsLoader loadcantact;
    ImageLoader mImageLoader;
    // PopupWindow popupWindowDogs;

    int positionfromSpinForCursor;
    //  DropDownListenerInsert dropDownListenerInsert=new DropDownListenerInsert();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insertion_main);
        searchView = (SearchView) findViewById(R.id.searchView);
        SpinnerCOntacts = (Spinner) findViewById(R.id.spinner);
        hospital= (ListView) findViewById(R.id.dynamiclistview);





        FragmentManager fragmentManager =getSupportFragmentManager();
        mImageLoader = new ImageLoader(this, getListPreferredItemHeight()) {
            @Override
            protected Bitmap processBitmap(Object data) {
                // This gets called in a background thread and passed the data from
                // ImageLoader.loadImage().
                return loadContactPhotoThumbnail((String) data, getImageSize());
            }
        };
        imageload=mImageLoader;
      imageloading=  getmImageLoagder();

        mImageLoader.setLoadingImage(R.drawable.ic_contact_picture_holo_light);

        // Add a cache to the image loader
        mImageLoader.addImageCache(fragmentManager, 0.1f);
        phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        assert phones != null;
        loadPreferences(this);
        contacts=new String[phones.getCount()];
        loadcantact=new ListViewContactsLoader();
        loadcantact.execute();
        SpinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,contacts);


    myeuseCustomAdapter=new MyeuseCustomAdapter(this,myreuseList);




        SpinnerCOntacts.setAdapter(SpinnerAdapter);
        SpinnerCOntacts.setOnItemSelectedListener(this);
        registerForContextMenu(hospital);








    }

    public ImageLoader getmImageLoagder()
    {
        return imageload;
    }
    private int getListPreferredItemHeight() {
        final TypedValue typedValue = new TypedValue();

        // Resolve list item preferred height theme attribute into typedValue
        // Resolve list item preferred height theme attribute into typedValue
    getTheme().resolveAttribute(
                android.R.attr.listPreferredItemHeight, typedValue, true);

        // Create a new DisplayMetrics object
        final DisplayMetrics metrics = new DisplayMetrics();

        // Populate the DisplayMetrics
       getWindowManager().getDefaultDisplay().getMetrics(metrics);

        // Return theme value based on DisplayMetrics
        return (int) typedValue.getDimension(metrics);
    }


    private Bitmap loadContactPhotoThumbnail(String photoData, int imageSize) {

        // Ensures the Fragment is still added to an activity. As this method is called in a
        // background thread, there's the possibility the Fragment is no longer attached and
        // added to an activity. If so, no need to spend resources loading the contact photo.


        // Instantiates an AssetFileDescriptor. Given a content Uri pointing to an image file, the
        // ContentResolver can return an AssetFileDescriptor for the file.
        AssetFileDescriptor afd = null;

        // This "try" block catches an Exception if the file descriptor returned from the Contacts
        // Provider doesn't point to an existing file.
        try {
            Uri thumbUri;
            // If Android 3.0 or later, converts the Uri passed as a string to a Uri object.
            if (Utils.hasHoneycomb()) {
                thumbUri = Uri.parse(photoData);
            } else {
                // For versions prior to Android 3.0, appends the string argument to the content
                // Uri for the Contacts table.
                final Uri contactUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, photoData);

                // Appends the content Uri for the Contacts.Photo table to the previously
                // constructed contact Uri to yield a content URI for the thumbnail image
                thumbUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
            }
            // Retrieves a file descriptor from the Contacts Provider. To learn more about this
            // feature, read the reference documentation for
            // ContentResolver#openAssetFileDescriptor.
            afd = getContentResolver().openAssetFileDescriptor(thumbUri, "r");

            // Gets a FileDescriptor from the AssetFileDescriptor. A BitmapFactory object can
            // decode the contents of a file pointed to by a FileDescriptor into a Bitmap.
            assert afd != null;
            FileDescriptor fileDescriptor = afd.getFileDescriptor();

            if (fileDescriptor != null) {
                // Decodes a Bitmap from the image pointed to by the FileDescriptor, and scales it
                // to the specified width and height
                return ImageLoader.decodeSampledBitmapFromDescriptor(
                        fileDescriptor, imageSize, imageSize);
            }
        } catch (FileNotFoundException e) {
            // If the file pointed to by the thumbnail URI doesn't exist, or the file can't be
            // opened in "read" mode, ContentResolver.openAssetFileDescriptor throws a
            // FileNotFoundException.
            if (BuildConfig.DEBUG) {
                Log.d("HHHHHHHYUYUY", "Contact photo thumbnail not found for contact " + photoData
                        + ": " + e.toString());
            }
        } finally {
            // If an AssetFileDescriptor was returned, try to close it
            if (afd != null) {
                try {
                    afd.close();
                } catch (IOException e) {
                    // Closing a file descriptor might cause an IOException if the file is
                    // already closed. Nothing extra is needed to handle this.
                }
            }
        }

        // If the decoding failed, returns null
        return null;
    }
    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        userIsInteracting = true;
    }
    private void loadPreferences(Context context) {
        SharedPreferences savepref = PreferenceManager.getDefaultSharedPreferences(this);
        String myJsonArray = savepref.getString("mycontactarray", "Sorrynot availabel");
        try {
            JSONArray jsonArray = new JSONArray(myJsonArray);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj;
                obj = jsonArray.getJSONObject(i);


                String title = obj.getString("Title");
                String description = obj.getString("Description");
                if(obj.has("image"))
                {
                    String  image=obj.getString("image");
                    myReuseClass = new MyReuseClass(title, description,image);
                }


                else {


                    myReuseClass = new MyReuseClass(title, description);
                }


                myreuseList.add(myReuseClass);
                myeuseCustomAdapter=new MyeuseCustomAdapter(this,myreuseList);
                hospital.setAdapter(myeuseCustomAdapter);

            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e + " ", Toast.LENGTH_LONG).show();
        }
    }


    private void SaveSharedpreferences() {
        SharedPreferences savepref= PreferenceManager.getDefaultSharedPreferences(this);
        JSONArray jsonArray = new JSONArray();
        for (int i=0; i < myreuseList.size(); i++) {
            jsonArray.put(myreuseList.get(i).getJSONObject());
        }
        Toast.makeText(getApplicationContext(),jsonArray.toString(),Toast.LENGTH_LONG).show();
        SharedPreferences.Editor editor=savepref.edit();

        editor.putString("mycontactarray", jsonArray.toString());
        editor.apply();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("Kusss", "this called");
        outState.putParcelableArrayList("mycontacts", myreuseList);



    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId())
        {
            case R.id.action_settings:
                Delete(info.position);
        }
        return true;
    }

    public  void Delete(int position)
    {
        myreuseList.remove(position);

        myeuseCustomAdapter.notifyDataSetChanged();
        hospital.setAdapter(myeuseCustomAdapter);
        SaveSharedpreferences();
    }






    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (userIsInteracting) {
            updateGUI(position);
        }



    }


    public String[] getContacts() {
        return contacts;
    }

    QuickContactBadge photoThumbnail;
    private void updateGUI(int position) {
        String[] projection = {                                  // The columns to return for each row
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER,ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI
        };
        positionfromSpinForCursor=position;
        String selectionargs[]={contacts[position]};
        String PhotoThumbNail=null;
        Cursor phonenum = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "=?", selectionargs,null);
        assert phonenum != null;
        while (phonenum.moveToNext())
        {
            phonenumber = phonenum.getString(phonenum.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            PhotoThumbNail=phonenum.getString(phonenum.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
            myReuseClass = new MyReuseClass(contacts[position], phonenumber, PhotoThumbNail);





       //     photoThumbnail.setVisibility(View.VISIBLE);

            myreuseList.add(myReuseClass);

        }



        phonenum.close();

        hospital.setAdapter(myeuseCustomAdapter);
        SaveSharedpreferences();
    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }





    private class ListViewContactsLoader extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (phones != null) {
                int i = 0;
                Log.e("count", "" + phones.getCount());

                if (phones.getCount() == 0) {
                    Toast.makeText(InsertDataActivity.this, "No contacts in your contact list.", Toast.LENGTH_LONG).show();
                }

                while (phones.moveToNext()) {

                    String id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                    String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    contacts[i] = name;

                    i++;
                }

            }
            return null;
        }
    }

}