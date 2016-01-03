package notifactionspecial.com.myapplication;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;




/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HospitalFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HospitalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HospitalFragment extends Fragment implements SearchView.OnQueryTextListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    SearchView searchView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final String ARG_SECTION_NUMBER = "section_number";
    ListView hospital;
    private OnFragmentInteractionListener mListener;
ArrayList<MyReuseClass> myReuseList;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment HospitalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HospitalFragment newInstance(int position) {
        HospitalFragment fragment=new HospitalFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    public HospitalFragment() {
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



        View list=inflater.inflate(R.layout.fragment_hospital, container, false);
    searchView   = (SearchView) list.findViewById(R.id.searchView3);
myReuseList=new ArrayList<MyReuseClass>();

        myReuseList.add(new MyReuseClass("Fire", "102"));
        myReuseList.add(new MyReuseClass("HowrahEnquiry", "102"));
        myReuseList.add(new MyReuseClass("SealdahEnquiry", "+913326603542"));
        myReuseList.add(new MyReuseClass("MetroRailways", "+913323503535"));
       myReuseList.add(new MyReuseClass("WBSEDCLEnquiry", "913322261054"));
       myReuseList.add(new MyReuseClass("CESCEnquiry", "1912"));

        setupSearchView();

        hospital= (ListView) list.findViewById(R.id.listView4);
hospital.setAdapter(new MyeuseCustomAdapter(getActivity(),myReuseList));
        hospital.setTextFilterEnabled(true);
        // Inflate the layout for this fragment
        return list;
    }

    private void setupSearchView()
    {
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener( this);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search Here");
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            hospital.clearTextFilter();
        } else {
            hospital.setFilterText(newText);
        }
        return true;
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
