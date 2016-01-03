package notifactionspecial.com.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class PoliceFragment extends Fragment {
    ArrayList<Myimportant> employeeArrayList;
    ListView important;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_SECTION_NUMBER = "section_number";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;




    // TODO: Rename and change types and number of parameters
    public static PoliceFragment newInstance(int position ) {
        PoliceFragment fragment = new PoliceFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    public PoliceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View list=inflater.inflate(R.layout.fragment_blank, container, false);
        important= (ListView) list.findViewById(R.id.listView);
        important.setAdapter(new MyImportantAdapter1(getActivity()));
        return list;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
        }


    @Override
    public void onDetach() {
        super.onDetach();

    }
}


class Myimportant1
{
    String titles;
    String description;
    Myimportant1(String titles,String description)
    {
        this.titles=titles;
        this.description=description;
    }
}
class MyImportantAdapter1 extends BaseAdapter
{
    Context context;
    ArrayList<Myimportant1> list;
    public MyImportantAdapter1(Context c)
    {
        this.context=c;
        list=new ArrayList<Myimportant1>();
        Resources res=c.getResources();
        String titles[]=         res.getStringArray(R.array.policetitle);
        String[] desc=res.getStringArray(R.array.policedesc);
        for(int i=0;i<8;i++)
            list.add(new Myimportant1(titles[i],desc[i]));

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View single=convertView;
        ViewHolder1 holder=null;

        if (single == null) {


            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            single = inflater.inflate(R.layout.single_row, parent, false);
            holder=new ViewHolder1(single);
            single.setTag(holder);

        } else {
            holder= (ViewHolder1) single.getTag();


        }
        Myimportant1 temp=list.get(i);

        holder.title.setText(temp.titles);
        holder.desc.setText(temp.description);
        return single;
    }
}
class ViewHolder1
{
    TextView title;
    TextView desc;
    ViewHolder1(View v)
    {
        title= (TextView) v.findViewById(R.id.textView);
        desc= (TextView) v.findViewById(R.id.textView2);
    }
}


