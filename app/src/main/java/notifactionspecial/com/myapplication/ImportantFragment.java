package notifactionspecial.com.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;


public class ImportantFragment extends Fragment implements SearchView.OnQueryTextListener {

  ArrayList<Myimportant> employeeArrayList2;
    static ListView important;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    SearchView search;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener1 mListener;
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    // TODO: Rename and change types and number of parameters
    public static ImportantFragment newInstance(int position) {
        ImportantFragment fragment = new ImportantFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, position);
        fragment.setArguments(args);
        return fragment;


    }


    public ImportantFragment() {
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
        setHasOptionsMenu(true);
        View list = inflater.inflate(R.layout.fragment_blank, container, false);
        important = (ListView) list.findViewById(R.id.listView);
        search= (SearchView) list.findViewById(R.id.searchView2);

        employeeArrayList2= new ArrayList<Myimportant>();
        employeeArrayList2.add(new Myimportant("Police", "100"));
        employeeArrayList2.add(new Myimportant("Fire", "101"));
        employeeArrayList2.add(new Myimportant("Fire", "102"));
        employeeArrayList2.add(new Myimportant("Howrah\u0020Enquiry", "102"));
        employeeArrayList2.add(new Myimportant("Sealdah\u0020Enquiry", "+913326603542"));
        employeeArrayList2.add(new Myimportant("Metro\u0020Railways", "+913323503535"));
        employeeArrayList2.add(new Myimportant("WBSEDCL\u0020Enquiry", "913322261054"));
        employeeArrayList2.add(new Myimportant(" CESC\u0020Enquiry", "1912"));
      important.setTextFilterEnabled(true);
        important.setAdapter(new MyImportantAdapter(getActivity(), employeeArrayList2));
        Log.d("KUSSS", "onCreateView called here now ");
        setupSearchView();

        return list;

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here


        super.onCreateOptionsMenu(menu, inflater);
    }
    private void setupSearchView()
    {
        search.setIconifiedByDefault(false);
        search.setOnQueryTextListener(this);
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    public static ListView getimport() {
        return important;
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    interface OnFragmentInteractionListener1 {
        // TODO: Update argument type and name
        public void onFragmentInteraction(ListView view);

    }
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




class Myimportant {

        String titles;
        String description;

        Myimportant(String titles, String description) {
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

        class MyImportantAdapter extends BaseAdapter implements Filterable {

            Context context;
            ArrayList<Myimportant> orig;
            ArrayList<Myimportant> list;
            public ArrayList<Myimportant> employeeArrayList;

            public MyImportantAdapter(Context c,ArrayList<Myimportant> employeeArrayList) {
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
                ViewHolder holder = null;

                if (single == null) {


                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    single = inflater.inflate(R.layout.single_row, parent, false);
                    holder = new ViewHolder(single);
                    single.setTag(holder);

                } else {
                    holder = (ViewHolder) single.getTag();


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
                        final ArrayList<Myimportant> results = new ArrayList<Myimportant>();
                        if (orig == null)
                            orig = employeeArrayList;
                        if (constraint != null) {
                            if (orig != null && orig.size() > 0) {
                                for (final Myimportant g : orig) {
                                    if (g.getName()
                                            .contains(constraint.toString()))
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
                        employeeArrayList = (ArrayList<Myimportant>) results.values;
                        notifyDataSetChanged();
                    }
                };
            }
            public void notifyDataSetChanged() {
                super.notifyDataSetChanged();
            }
        }


        class ViewHolder {
            TextView title;
            TextView desc;

            ViewHolder(View v) {
                title = (TextView) v.findViewById(R.id.textView);
                desc = (TextView) v.findViewById(R.id.textView2);
            }


        }


