package notifactionspecial.com.myapplication;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Context;

import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by USER on 08-Nov-15.
 */
public class MyeuseCustomAdapter extends BaseAdapter  implements Filterable {

    MyeuseCustomAdapter myeuseCustomAdapter;
    Context context=null;
    ImageLoader mimageLoader;
    ArrayList<MyReuseClass> orig;
    int positiontoInsert;
    int mCounter;


    InsertDataActivity myinsert;
    HomeInsertDataFragment home;
    WorkInsertFragment work;
    ViewHolderReuse holder;

    HashMap<MyReuseClass, Integer> mIdMap = new HashMap<>();

    public ArrayList<MyReuseClass> employeeArrayList;

    MyeuseCustomAdapter(Context c, ArrayList<MyReuseClass> employeeArrayList) {

context=c;




        this.employeeArrayList = employeeArrayList;


    }
MyeuseCustomAdapter(Fragment fragment, ArrayList<MyReuseClass> employeeArrayList)
{

    this.employeeArrayList = employeeArrayList;

 if(fragment instanceof HomeInsertDataFragment)
 {
     home= (HomeInsertDataFragment) fragment;
     mimageLoader=home.getmImageLoagder();
     context=home.getActivity();
 }
   else
 {
     work= (WorkInsertFragment) fragment;
     mimageLoader=work.getmImageLoagder();
     context=work.getActivity();
 }

}



    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getCount() {
        return employeeArrayList.size();
    }

    @Override

    public Object getItem(int position) {
        positiontoInsert = position;
        return employeeArrayList.get(position);

    }

    @Override
    public long getItemId(int position) {

return  position;
    }



    private void updateStableIds() {
        mIdMap.clear();
        mCounter = 0;
        for (int i = 0; i < employeeArrayList.size(); ++i) {
            mIdMap.put(employeeArrayList.get(i), mCounter++);
        }
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View single = convertView;


        if (single == null) {


    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    single = inflater.inflate(R.layout.single_row, parent, false);
    holder = new ViewHolderReuse(single);

    single.setTag(holder);

}



         else {
            holder = (ViewHolderReuse) single.getTag();


        }



        holder.title.setText(employeeArrayList.get(i).getName());
        holder.desc.setText(employeeArrayList.get(i).getAge());






if(context instanceof  InsertDataActivity)
{
    myinsert= (InsertDataActivity) context;
    mimageLoader=myinsert.getmImageLoagder();
}





    mimageLoader.loadImage(employeeArrayList.get(i).getImage(), holder.icon);


        return single;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<MyReuseClass> results = new ArrayList<>();
                if (orig == null)
                    orig = employeeArrayList;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final MyReuseClass g : orig) {
                            if (g.getName()
                                    .contains(constraint.toString())||g.getAge().contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                employeeArrayList = (ArrayList<MyReuseClass>) results.values;
                notifyDataSetChanged();
            }
        };
    }
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }





}




