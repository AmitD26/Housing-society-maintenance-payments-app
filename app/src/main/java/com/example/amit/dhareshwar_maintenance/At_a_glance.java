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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link At_a_glance.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link At_a_glance#newInstance} factory method to
 * create an instance of this fragment.
 */
public class At_a_glance extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public At_a_glance() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment At_a_glance.
     */
    // TODO: Rename and change types and number of parameters
    public static At_a_glance newInstance(String param1, String param2) {
        At_a_glance fragment = new At_a_glance();
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
        View rootView = inflater.inflate(R.layout.fragment_at_a_glance, container, false);
        RecyclerView outerRecyclerView = (RecyclerView) rootView.findViewById(R.id.at_a_glance_outer_recycler_view);
        outerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        outerRecyclerView.setAdapter(new RecyclerView.Adapter() {
            public static final int TOTAL_COLLECTED_MONEY = 1;
            public static final int FLAT_NUMBERS = 2;

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                if (viewType == TOTAL_COLLECTED_MONEY) {
                    return new TotalCollectedMoney(LayoutInflater.from(parent.getContext()).inflate(R.layout.at_a_glance_total_money, parent, false));
                }
                else if (viewType == FLAT_NUMBERS) {
                    return new FlatNumbers(LayoutInflater.from(parent.getContext()).inflate(R.layout.at_a_glance_flat_numbers, parent, false));
                }
                return null;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 2;
            }

            @Override
            public int getItemViewType(int position) {
                if (position == 0) {
                    return TOTAL_COLLECTED_MONEY;
                }
                else if (position == 1) {
                    return FLAT_NUMBERS;
                }

                return super.getItemViewType(position);
            }

            class TotalCollectedMoney extends RecyclerView.ViewHolder {
                public TotalCollectedMoney(View itemView) {
                    super(itemView);
                }
            }

            class FlatNumbers extends RecyclerView.ViewHolder {
                public FlatNumbers(View itemView) {
                    super(itemView);
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
