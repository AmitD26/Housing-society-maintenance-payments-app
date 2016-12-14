package com.example.amit.dhareshwar_maintenance;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserPayments.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserPayments#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserPayments extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static String SINGLE_USER = "0";
    public static String ALL_USERS = "1";
    public static String TO_BE_CONFIRMED = "2";
    public static String RECEIPTS_TO_BE_SENT = "3";
    public static String REQUESTS_FOR_RECEIPTS = "4";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public UserPayments() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserPayments.
     */
    // TODO: Rename and change types and number of parameters
    public static UserPayments newInstance(String param1, String param2, String argument) {
        UserPayments fragment = new UserPayments();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, argument);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View rootView = inflater.inflate(R.layout.fragment_user_payments, container, false);
        RecyclerView paymentsRecyclerView = (RecyclerView) rootView.findViewById(R.id.payments_recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        paymentsRecyclerView.setLayoutManager(llm);
        try {
            JSONArray paymentRecords = null;
            if (mParam1.equals(SINGLE_USER)) {
                paymentRecords = new GetPaymentRecordsFromServer(getContext()).execute(getContext().getSharedPreferences(MainActivity.LOGIN_INFO_SHARED_PREFS, Context.MODE_PRIVATE).getString("username", null)).get();
            }
            else if (mParam1.equals(ALL_USERS)) {
                paymentRecords = new GetPaymentRecordsFromServer(getContext()).execute("all").get();
            }
            else if (mParam1.equals(TO_BE_CONFIRMED)) {
                paymentRecords = new GetPaymentRecordsFromServer(getContext()).execute("to_be_confirmed").get();
            }
            else if (mParam1.equals(RECEIPTS_TO_BE_SENT)) {
                paymentRecords = new GetPaymentRecordsFromServer(getContext()).execute("receipts_to_be_sent").get();
            }
            else if (mParam1.equals(REQUESTS_FOR_RECEIPTS)) {
                paymentRecords = new GetPaymentRecordsFromServer(getContext()).execute("requests_for_receipts").get();
            }
            PaymentsRecyclerViewAdapter paymentsRecyclerViewAdapter = new PaymentsRecyclerViewAdapter(paymentRecords, getContext(), mParam1);
            paymentsRecyclerView.setAdapter(paymentsRecyclerViewAdapter);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
//        paymentsRecyclerView.addItemDecoration(new DividerDecorationRecyclerView(paymentsRecyclerView.getContext(), R.drawable.payment_divider));

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
