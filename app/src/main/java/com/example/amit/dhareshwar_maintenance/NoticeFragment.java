package com.example.amit.dhareshwar_maintenance;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NoticeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NoticeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoticeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String NOTICE_ID = "notice_id";
    private static final String NOTICE_DETAILS = "notice_details";

    // TODO: Rename and change types of parameters
    private String noticeID;
    private JSONObject noticeDetails;

    private OnFragmentInteractionListener mListener;

    public NoticeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoticeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoticeFragment newInstance(String noticeID, JSONObject noticeDetails) {
        NoticeFragment fragment = new NoticeFragment();
        Bundle args = new Bundle();
        args.putString(NOTICE_ID, noticeID);
        args.putString(NOTICE_DETAILS, noticeDetails.toString());

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            noticeID = getArguments().getString(NOTICE_ID);
            try {
                noticeDetails = new JSONObject(getArguments().getString(NOTICE_DETAILS));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_notice, container, false);

        TextView noticeID_textView = (TextView) rootView.findViewById(R.id.noticeID_textView);
        noticeID_textView.setText(noticeID);
        TextView noticeDate_textView = (TextView) rootView.findViewById(R.id.notice_date_textView);
        try {
            noticeDate_textView.setText(noticeDetails.getString("notice_date"));
            TextView noticeSubject_textView = (TextView) rootView.findViewById(R.id.notice_subject_textView);
            noticeSubject_textView.setText(noticeDetails.getString("notice_subject"));
            TextView noticeSender_textView = (TextView) rootView.findViewById(R.id.notice_sender_textView);
            noticeSender_textView.setText(noticeDetails.getString("sender_flat_no") + "  " + noticeDetails.getString("sender_privilege_level"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Button showNoticeDetails = (Button) rootView.findViewById(R.id.view_notice_details_button);
        showNoticeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ScrollingActivity.class));
            }
        });

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
