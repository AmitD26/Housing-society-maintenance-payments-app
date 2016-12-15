package com.example.amit.dhareshwar_maintenance;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MakeNotice.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MakeNotice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MakeNotice extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MakeNotice() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MakeNotice.
     */
    // TODO: Rename and change types and number of parameters
    public static MakeNotice newInstance(String param1, String param2) {
        MakeNotice fragment = new MakeNotice();
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
        View rootView = inflater.inflate(R.layout.fragment_make_notice, container, false);
        final EditText notice_subject = (EditText) rootView.findViewById(R.id.notice_subject);
        final EditText notice_main_body = (EditText) rootView.findViewById(R.id.notice_main_body);
        Button notice_send_button = (Button) rootView.findViewById(R.id.notice_send_button);

        notice_send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notice_subject.getText() == null) {
                    Toast.makeText(getContext(), "Notice subject cannot be blank.", Toast.LENGTH_SHORT).show();
                    return;
                }
                String not_subject = notice_subject.getText().toString();
                String not_main_body = null;
                if (notice_main_body.getText() != null) {
                    not_main_body = notice_main_body.getText().toString();
                }
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String not_date = df.format(Calendar.getInstance().getTime());
                String username = getContext().getSharedPreferences(MainActivity.LOGIN_INFO_SHARED_PREFS, Context.MODE_PRIVATE).getString("username", null);

                try {
                    JSONObject server_response = new SendNoticeToServer(getContext()).execute(username, not_subject, not_main_body, not_date).get();
                    if (server_response.getBoolean("success")) {
                        new AlertDialog.Builder(getContext()).setTitle("Notice sent successfully.").setPositiveButton(R.string.ok, null).show();
                    } else {
                        Toast.makeText(getContext(), "Failure. Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException | ExecutionException | JSONException e) {
                    e.printStackTrace();
                }
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
