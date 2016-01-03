package notifactionspecial.com.myapplication;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FireFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FireFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FireFragment extends Fragment implements SearchView.OnQueryTextListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<Myimportant2> employeeArrayList1;
   ListView important;
    // TODO: Rename and change types of parameters

    SearchView  search;
    private OnFragmentInteractionListener mListener;
    private static final String ARG_SECTION_NUMBER = "section_number";
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     */
    // TODO: Rename and change types and number of parameters
    public static FireFragment newInstance(int position) {
        FireFragment fragment=new FireFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    public FireFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View list=inflater.inflate(R.layout.fragment_fire, container, false);
        important = (ListView) list.findViewById(R.id.listView3);
        search= (SearchView) list.findViewById(R.id.searchView);
employeeArrayList1=new ArrayList<>();
        // Inflate the layout for this fragment employeeArrayList= new ArrayList<>();
        employeeArrayList1.add(new Myimportant2("Alifnager Fire Station", "+911"));
        employeeArrayList1.add(new Myimportant2("BainabGhata", "101"));
        employeeArrayList1.add(new Myimportant2("Fire", "102"));
        employeeArrayList1.add(new Myimportant2("HowrahEnquiry", "102"));
        employeeArrayList1.add(new Myimportant2("SealdahEnquiry", "+913326603542"));
        employeeArrayList1.add(new Myimportant2("MetroRailways", "+913323503535"));
        employeeArrayList1.add(new Myimportant2("WBSEDCLEnquiry", "913322261054"));
        employeeArrayList1.add(new Myimportant2(" CESCnquiry", "1912"));

        important.setAdapter(new MyImportantAdapter2(getActivity(), employeeArrayList1));
        Log.d("KUSSS", "onCreateView called here now ");

        setupSearchView();
        return list;
    }
    private void setupSearchView()
    { important.setTextFilterEnabled(true);
        search.setIconifiedByDefault(false);
        search.setOnQueryTextListener( this);
        search.setSubmitButtonEnabled(true);
        search.setQueryHint("Search Here");
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {

        if (TextUtils.isEmpty(newText)) {
            important.clearTextFilter();
        } else {
            important.setFilterText(newText);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        return false;
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
class Myimportant2 {

    String titles;
    String description;

    Myimportant2(String titles, String description) {
        this.titles = titles;
        this.description = description;
    }

    public String getName() {
        return titles;
    }

    public String getAge() {
        return description;
    }
}

class MyImportantAdapter2 extends BaseAdapter implements Filterable {

    Context context;
    ArrayList<Myimportant2> orig;

    public ArrayList<Myimportant2> employeeArrayList;

    public MyImportantAdapter2(Context c,ArrayList<Myimportant2> employeeArrayList) {
        super();
        this.context = c;

        this.employeeArrayList=employeeArrayList;


    }

    @Override
    public int getCount() {
        return employeeArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return employeeArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View single = convertView;
        ViewHolder2 holder = null;

        if (single == null) {


            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            single = inflater.inflate(R.layout.single_row, parent, false);
            holder = new ViewHolder2(single);
            single.setTag(holder);

        } else {
            holder = (ViewHolder2) single.getTag();


        }


        holder.title.setText(employeeArrayList.get(i).getName());
        holder.desc.setText(employeeArrayList.get(i).getAge());
        return single;
    }


    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<Myimportant2> results = new ArrayList<>();
                if (orig == null)
                    orig = employeeArrayList;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final Myimportant2 g : orig) {
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
                employeeArrayList = (ArrayList<Myimportant2>) results.values;
                notifyDataSetChanged();
            }
        };
    }
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}


class ViewHolder2 {
    TextView title;
    TextView desc;

    ViewHolder2(View v) {
        title = (TextView) v.findViewById(R.id.textView);
        desc = (TextView) v.findViewById(R.id.textView2);
    }


}
