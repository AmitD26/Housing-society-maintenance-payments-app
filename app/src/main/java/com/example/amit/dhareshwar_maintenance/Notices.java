package com.example.amit.dhareshwar_maintenance;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Notices.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Notices#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Notices extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Notices() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Notices.
     */
    // TODO: Rename and change types and number of parameters
    public static Notices newInstance(String param1, String param2) {
        Notices fragment = new Notices();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
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
        View rootView = inflater.inflate(R.layout.fragment_notices, container, false);

        RecyclerView noticeRecyclerView = (RecyclerView) rootView.findViewById(R.id.notice_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        noticeRecyclerView.setLayoutManager(linearLayoutManager);

        try {
            JSONObject receivedNotices = new GetNoticesFromServer(getActivity()).execute().get();
            NoticeRecyclerViewAdapter noticeRecyclerViewAdapter = new NoticeRecyclerViewAdapter(receivedNotices);
            noticeRecyclerView.setAdapter(noticeRecyclerViewAdapter);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

//        noticeRecyclerView.addItemDecoration(new DividerDecorationRecyclerView(getContext(), R.drawable.payment_divider));

//        LinearLayout notices_linear_layout = (LinearLayout) rootView.findViewById(R.id.notices_linear_layout);
//        TextView displayNoticeTextView;
//
//        try {
//            JSONObject receivedNotices = new GetNoticesFromServer(getActivity()).execute().get();
//            Iterator<?> keys = receivedNotices.keys();
//            int notice_fragment_container_linearLayout_id_incrementer = 1;
//            while (keys.hasNext()) {
//                String noticeID = (String)keys.next();
//                JSONObject notice = (JSONObject) receivedNotices.get(noticeID);
//                String[] notice_fields = getResources().getStringArray(R.array.notice_fields);
//
//                LinearLayout fragment_container = new LinearLayout(getContext());
//                int notice_fragment_container_linearLayout_id = notice_fragment_container_linearLayout_id_incrementer++;
//                fragment_container.setOrientation(LinearLayout.HORIZONTAL);
//                fragment_container.setId(notice_fragment_container_linearLayout_id);
//                getFragmentManager().beginTransaction().add(fragment_container.getId(),NoticeFragment.newInstance(noticeID,notice)).commit();
//                notices_linear_layout.addView(fragment_container);
////                displayNoticeTextView = new TextView(getContext());
////                displayNoticeTextView.setText(notice_fields[0] + noticeID + "\n" + notice.get("notice_date") + "\n" + notice.get("notice_subject") + "\n" + notice.get("main_body") + "\n" + notice_fields[1] + notice.get("sender_privilege_level") + "\n\n\n");
////                notices_linear_layout.addView(displayNoticeTextView, 0);
//            }
//        } catch (InterruptedException | ExecutionException | JSONException e) {
//            e.printStackTrace();
//        }
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
