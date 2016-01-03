package notifactionspecial.com.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by USER on 09-Nov-15.
 */
public class NavigationDrawerRight extends Fragment {
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    private ActionBarDrawerToggle mDrawerToggle;
    public static final String PREFERNCE_NAME = "testpref";
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;
    String Categories[];
    public NavigationDrawerRight() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserLearnedDrawer = Boolean.valueOf(ReadFromSharedPrefernce(getActivity(), PREF_USER_LEARNED_DRAWER, "false"));
        mFromSavedInstanceState = savedInstanceState != null ? true : false;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }
    String mycategories[]={"Insert Contact","Create Message","Insertthrough pref","InsertDataFragment"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mDrawerListView = (ListView) inflater.inflate(
                R.layout.drawer_main, container, false);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              if(position==0)
              {
                  Intent intent=new Intent(getActivity(),InsertDataActivity.class);
                  startActivity(intent);
              }
                if(position==1)
                {
                    Intent intent1=new Intent(getActivity(),CreateMessageActivity.class);
                    startActivity(intent1);
                }

                if(position==2)
                {
                    Intent intent3=new Intent(getActivity(),INsertDataByPreference.class);
                    startActivity(intent3);
                }
                if(position==3)
                {
                    Intent intent4=new Intent(getActivity(),InsertFragmentDataActivit.class);
                    startActivity(intent4);
                }

            }
        });

        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,mycategories
        );
        mDrawerListView.setAdapter(arrayAdapter);

        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
        return mDrawerListView;
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(final Toolbar toolbar, int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        String Categories[];
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener


        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        {


            @Override
            public void onDrawerOpened(View drawerView) {
                if (!mUserLearnedDrawer) {

                    super.onDrawerOpened(drawerView);
                    mUserLearnedDrawer = true;
                    SavetoSharedPrefernce(getActivity(), PREF_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                    getActivity().invalidateOptionsMenu();
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
Toolbar mtoolbar;
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                mtoolbar = toolbar;
                if (slideOffset < 0.6) {
                    mtoolbar.setAlpha(1 - slideOffset);
                }

            }
        };
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }




    public static void SavetoSharedPrefernce(Context context, String PreferenceName, String PrefernceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERNCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PreferenceName, PrefernceValue);
        editor.apply();

    }

    public static String ReadFromSharedPrefernce(Context context, String PreferenceName, String DefaultValue) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERNCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PreferenceName, DefaultValue);

    }

}



