package notifactionspecial.com.myapplication;

import android.app.Activity;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


public class InsertFragmentDataActivit extends FragmentActivity implements MaterialTabListener {
    ViewPager pager;
    MaterialTabHost tabHost;
    private ScreenSlidePagerAdapter mPagerAdapter;
HomeInsertDataFragment homeInsertDataFragment= new HomeInsertDataFragment();
    WorkInsertFragment workInsertFragment=new WorkInsertFragment();
Context context;
    Fragment fragmentchild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
context=this;

        setContentView(R.layout.activity_insert_fragment_data);
        pager= (ViewPager) findViewById(R.id.pager);
        tabHost= (MaterialTabHost) findViewById(R.id.materialTabHost);

   mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
       pager.setAdapter(mPagerAdapter);
        pager.setPageTransformer(true, new  DepthPageTransformer(this));
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
tabHost.setSelectedNavigationItem(position);
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

for(int i=0;i<mPagerAdapter.getCount();i++)
{
    tabHost.addTab(tabHost.newTab().setText(mPagerAdapter.getPageTitle(i)).setTabListener(this));
}

    }

    @Override
    public void onTabSelected(MaterialTab tab) {
        pager.setCurrentItem(tab.getPosition());


    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }
    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }
    class ScreenSlidePagerAdapter  extends FragmentPagerAdapter
     {

         public ScreenSlidePagerAdapter(FragmentManager fm) {
             super(fm);
         }

         @Override
         public Fragment getItem(int position) {
             Fragment fragment=null;


             if (position == 0) {
                 fragment = HomeInsertDataFragment.newInstance(position);



             } else {
                 fragment = WorkInsertFragment.newInstance(position);


             }
             return fragment;


         }



         String tabstext[]={"Home","Work"};

         @Override
         public CharSequence getPageTitle(int position) {
             return tabstext[position];
         }

         @Override
         public int getCount() {
             return tabstext.length;
         }
     }
}

