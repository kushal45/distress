package notifactionspecial.com.myapplication;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks,ImportantFragment.OnFragmentInteractionListener1,SearchView.OnQueryTextListener
{
ImportantFragment importantFragment;
SearchView search;
ListView setFilterText;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    int ActivityPOsition;
    private CharSequence mTitle;
    RelativeLayout relativeLayout;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private NavigationDrawerRight mNavigationDrawerFragmentRight;
private Toolbar toolbar;
             private static final String ARG_SECTION_NUMBER = "section_number";
    /**

     */

String mycategories[]={"Police",
               "Fire",
              "Hospital" ,
             "Ambulance" ,
              "Blood  Banks" ,
               " Food Stuffs" ,
            "    Chemist and Drug Dealers",
            "    Media  " ,
            "    Women  " ,
            "   Airport",
            "   Railway" ,
            "   Electricity"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout= (RelativeLayout) findViewById(R.id.maincontainer);

        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
getSupportActionBar().setTitle("/my NAME");

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragmentRight = (NavigationDrawerRight)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawerright);
       FragmentManager fragmentmanager=getSupportFragmentManager();
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(toolbar,
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }
             public void onSectionAttached(int number) {
                 String mycat[]=getResources().getStringArray(R.array.Categories);
mTitle=mycat[number];
                 getSupportActionBar().setTitle(mTitle);
             }

             @Override
             public void onNavigationDrawerItemSelected(int position) {
                ImportantFragment fragment;
                 FragmentManager fManager = getSupportFragmentManager();
                 Bundle args = new Bundle();
FragmentTransaction fTransaction ;

                fTransaction  = fManager.beginTransaction();

 ActivityPOsition=position;


                 if (position == 0) {
                 fTransaction
                             .replace(R.id.container,ImportantFragment.newInstance(position)).commit();
                     importantFragment=ImportantFragment.newInstance(position);
                 } else if (position == 1) {
                    fManager.beginTransaction()
                             .replace(R.id.container,PoliceFragment.newInstance(position)).commit();

                 }else if (position ==2) {
                     fManager.beginTransaction()
                             .replace(R.id.container,FireFragment.newInstance(position))
                             .commit();
                 }
                 else if (position ==3) {
                     fManager.beginTransaction()
                             .replace(R.id.container,HospitalFragment.newInstance(position))
                             .commit();
                 }

             }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getSystemService( Context.SEARCH_SERVICE );
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            //use the query to search
            //for toolbar containg searchView which is inflated in onCreateOptionsMenu(Menu menu)
        }
    }


    @Override
    public boolean onQueryTextChange(String newText)
    {
        if(ActivityPOsition==1)
        {
            if (TextUtils.isEmpty(newText)) {
                setFilterText.clearTextFilter();

            } else {
                setFilterText.setFilterText(newText);
            }
        }

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public void onFragmentInteraction(ListView view) {
     view=   ImportantFragment.getimport();
        setFilterText=view;

    }


    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

                 public PlaceholderFragment() {

                 }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }


        @Override
                 public void onAttach(Activity activity) {
                     super.onAttach(activity);
                     ((MainActivity) activity).onSectionAttached(
                             getArguments().getInt(ARG_SECTION_NUMBER));
                 }
             }

    /**
     * A placeholder fragment containing a simple view.
     */
}




