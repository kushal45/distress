package notifactionspecial.com.myapplication;


import    android.support.v4.app.FragmentManager;
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
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeInsertDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeInsertDataFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView hospital;
    SearchView searchView;
    Boolean userIsInteracting = true;
    Boolean userConfirmedInteracting=false;
    MyeuseCustomAdapter myeuseCustomAdapter;

    ArrayList<MyReuseClass> myreuseList = new ArrayList<MyReuseClass>();
    String phonenumber;

    String contacts[];
    Cursor phones;

    ImageLoader imageloading;
    Spinner SpinnerCOntacts;
    MyReuseClass myReuseClass;

    ImageLoader imageload;
    ArrayAdapter<String> SpinnerAdapter;



    ListViewContactsLoader loadcantact;
    ImageLoader mImageLoader;

    Context context;


    public static HomeInsertDataFragment newInstance(int position) {
        HomeInsertDataFragment fragment = new HomeInsertDataFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public HomeInsertDataFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
         FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
            mImageLoader = new ImageLoader(getActivity(), getListPreferredItemHeight()) {
                @Override
                protected Bitmap processBitmap(Object data) {

                    return loadContactPhotoThumbnail((String) data, getImageSize());
                }
            };
            imageload=mImageLoader;
            imageloading=  getmImageLoagder();

            mImageLoader.setLoadingImage(R.drawable.ic_contact_picture_holo_light);
context=getActivity();
            // Add a cache to the image loader
            mImageLoader.addImageCache(fragmentManager, 0.1f);
            setHasOptionsMenu(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View homelayout = inflater.inflate(R.layout.insertion_main, container, false);
        SpinnerCOntacts = (Spinner)homelayout. findViewById(R.id.spinner);
        hospital= (ListView)homelayout. findViewById(R.id.dynamiclistview);
        phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        assert phones != null;
        loadPreferences(getActivity());
        contacts=new String[phones.getCount()];
        loadcantact=new ListViewContactsLoader();
        loadcantact.execute();
        SpinnerAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,contacts);


        myeuseCustomAdapter=new MyeuseCustomAdapter(this,myreuseList);




        SpinnerCOntacts.setAdapter(SpinnerAdapter);
        SpinnerCOntacts.setOnItemSelectedListener(this);
        registerForContextMenu(hospital);





        return homelayout;
    }
    public ImageLoader getmImageLoagder() {
        return imageload;
    }

    private int getListPreferredItemHeight() {
        final TypedValue typedValue = new TypedValue();

        // Resolve list item preferred height theme attribute into typedValue
        // Resolve list item preferred height theme attribute into typedValue
        getActivity().getTheme().resolveAttribute(
                android.R.attr.listPreferredItemHeight, typedValue, true);

        // Create a new DisplayMetrics object
        final DisplayMetrics metrics = new DisplayMetrics();

        // Populate the DisplayMetrics
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

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
            afd = context.getContentResolver().openAssetFileDescriptor(thumbUri, "r");

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

    private void loadPreferences(Context context) {
        SharedPreferences savepref = PreferenceManager.getDefaultSharedPreferences(context);
        String myJsonArray = savepref.getString("mycontactarrayhome", "Sorrynot availabel");
        try {
            JSONArray jsonArray = new JSONArray(myJsonArray);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj;
                obj = jsonArray.getJSONObject(i);


                String title = obj.getString("Title");
                String description = obj.getString("Description");
                if (obj.has("image")) {
                    String image = obj.getString("image");
                    myReuseClass = new MyReuseClass(title, description, image);
                } else {


                    myReuseClass = new MyReuseClass(title, description);
                }


                myreuseList.add(myReuseClass);
                myeuseCustomAdapter = new MyeuseCustomAdapter(this, myreuseList);
                hospital.setAdapter(myeuseCustomAdapter);

            }
        } catch (JSONException e) {
            Toast.makeText(getActivity(), e + " ", Toast.LENGTH_LONG).show();
        }
    }







    private void SaveSharedpreferences(Context context) {
        SharedPreferences savepref = PreferenceManager.getDefaultSharedPreferences(context);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < myreuseList.size(); i++) {
            jsonArray.put(myreuseList.get(i).getJSONObject());
        }
        Toast.makeText(getActivity(), jsonArray.toString(), Toast.LENGTH_LONG).show();
        SharedPreferences.Editor editor = savepref.edit();

        editor.putString("mycontactarrayhome", jsonArray.toString());
        editor.apply();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_settings:
                Delete(info.position);
        }
        return true;
    }

    public void Delete(int position) {
        myreuseList.remove(position);

        myeuseCustomAdapter.notifyDataSetChanged();
        hospital.setAdapter(myeuseCustomAdapter);

        SaveSharedpreferences(context);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (!userIsInteracting) {
            updateGUI(position);

        }
userIsInteracting=false;

    }





    private void updateGUI(int position) {
        String[] projection = {                                  // The columns to return for each row
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI
        };

        String selectionargs[] = {contacts[position]};
        String PhotoThumbNail = null;
        Cursor phonenum = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "=?", selectionargs, null);
        assert phonenum != null;
        while (phonenum.moveToNext()) {
            phonenumber = phonenum.getString(phonenum.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            PhotoThumbNail = phonenum.getString(phonenum.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
            myReuseClass = new MyReuseClass(contacts[position], phonenumber, PhotoThumbNail);




            myreuseList.add(myReuseClass);

        }


        phonenum.close();

        hospital.setAdapter(myeuseCustomAdapter);
        SaveSharedpreferences(context);
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    private class ListViewContactsLoader extends AsyncTask<Void, Void, Void> {




        @Override
        protected Void doInBackground(Void... params) {
            if (phones != null) {
                int i = 0;
                Log.e("count", "" + phones.getCount());

                if (phones.getCount() == 0) {
                    Toast.makeText(getActivity(), "No contacts in your contact list.", Toast.LENGTH_LONG).show();
                }

                while (phones.moveToNext()) {


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
