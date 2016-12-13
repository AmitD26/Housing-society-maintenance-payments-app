package com.example.amit.dhareshwar_maintenance;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.concurrent.ExecutionException;


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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
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
                try {
                    if (holder instanceof TotalCollectedMoney) {
                        JSONObject collected_money = new GetTotalCollectedMoneyFromServer(getContext()).execute().get();
                        Integer cash = Integer.parseInt(collected_money.getString("cash"));
                        Integer cheque = Integer.parseInt(collected_money.getString("cheque"));
                        Integer total = cash + cheque;

                        //Locale settings not taken into account
                        ((TotalCollectedMoney) holder).cash.setText(String.format(getString(R.string.collected_money),cash.toString()));
                        ((TotalCollectedMoney) holder).cheque.setText(String.format(getString(R.string.collected_money),cheque.toString()));
                        ((TotalCollectedMoney) holder).total.setText(String.format(getString(R.string.collected_money),total.toString()));
                        ((TotalCollectedMoney) holder).reset.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                TextView dialogTitle = new TextView(getContext());
                                dialogTitle.setText(R.string.are_you_sure_reset_collected_money);
                                dialogTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                                dialogTitle.setTypeface(Typeface.DEFAULT_BOLD);
                                int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                                dialogTitle.setPadding(padding, padding, padding, padding);
                                alertDialogBuilder.setCustomTitle(dialogTitle);
//                                alertDialogBuilder.setView(inflater.inflate(R.layout.dialog_reset_collected_money, null, false));
                                alertDialogBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        AlertDialog.Builder passwordForResetCollectedMoney = new AlertDialog.Builder(getContext());
                                        passwordForResetCollectedMoney.setTitle(R.string.enter_password_to_proceed);
                                        passwordForResetCollectedMoney.setCancelable(false);
                                        final View passwordSecurityCheckDialogView = inflater.inflate(R.layout.password_security_check, null);
                                        passwordForResetCollectedMoney.setView(passwordSecurityCheckDialogView);
                                        passwordForResetCollectedMoney.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                String username = getContext().getSharedPreferences(MainActivity.LOGIN_INFO_SHARED_PREFS, Context.MODE_PRIVATE).getString("username", null);
                                                EditText password_editText = (EditText) passwordSecurityCheckDialogView.findViewById(R.id.enter_password_for_confirmation);
                                                String password = password_editText.getText().toString();
                                                try {
                                                    JSONObject serverResponse = new JSONObject(new LoginToServer(getContext()).execute(username, password).get());
                                                    Log.e("u p r",username + " " + password + " " + serverResponse.toString());
                                                    if (serverResponse.getBoolean("success")) {
                                                        Toast.makeText(getContext(), "Success.", Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (JSONException | InterruptedException | ExecutionException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                        passwordForResetCollectedMoney.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        });
                                        passwordForResetCollectedMoney.show();
                                    }
                                });
                                alertDialogBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                alertDialogBuilder.show();
                            }
                        });
                    }
                    else if (holder instanceof FlatNumbers) {
                        final JSONArray residents_info = new GetAllResidentsInfoFromServer(getContext()).execute().get();
                        final JSONArray residents_dues_info = new GetAllResidentsDuesInfoFromServer(getContext()).execute().get();
                        RecyclerView flatNumbersRecyclerView = ((FlatNumbers) holder).flatNumbersRecyclerView;
                        flatNumbersRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
                        flatNumbersRecyclerView.setAdapter(new RecyclerView.Adapter() {
                            @Override
                            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                                return new FlatNumberCard(LayoutInflater.from(parent.getContext()).inflate(R.layout.flat_number_card, parent, false));
                            }

                            @Override
                            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                                FlatNumberCard flatNumberCardViewHolder = (FlatNumberCard) holder;
                                try {
                                    Integer dues = Integer.parseInt(residents_dues_info.getJSONObject(position).getString("dues"));
                                    flatNumberCardViewHolder.flatNumber.setText(residents_info.getJSONObject(position).getString("flat_no"));
                                    flatNumberCardViewHolder.status.setText(residents_info.getJSONObject(position).getString("status").substring(0,6));
                                    if (dues > 500) {
//                                        ((FlatNumberCard) holder).flatNumberCard.setCardBackgroundColor(ContextCompat.getColor(getContext(),R.color.light_red));
                                        ((FlatNumberCard) holder).dues.setText(String.format(getString(R.string.dues),dues.toString()));
                                        ((FlatNumberCard) holder).dues.setTextColor(Color.RED);
                                    }
                                    else if (dues == 0){
//                                        ((FlatNumberCard) holder).flatNumberCard.setCardBackgroundColor(ContextCompat.getColor(getContext(),R.color.light_green));
                                        ((FlatNumberCard) holder).dues.setText(String.format(getString(R.string.dues),dues.toString()));
                                        ((FlatNumberCard) holder).dues.setTextColor(Color.GREEN);
                                    }
                                    else {
                                        ((FlatNumberCard) holder).dues.setText(String.format(getString(R.string.dues),dues.toString()));
                                        ((FlatNumberCard) holder).dues.setTextColor(Color.RED);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public int getItemCount() {
                                return 24;
                            }

                            class FlatNumberCard extends RecyclerView.ViewHolder {
                                CardView flatNumberCard;
                                TextView flatNumber, status, dues;
                                public FlatNumberCard(View itemView) {
                                    super(itemView);
                                    flatNumber = (TextView) itemView.findViewById(R.id.flat_number);
                                    status = (TextView) itemView.findViewById(R.id.status);
                                    flatNumberCard = (CardView) itemView.findViewById(R.id.flat_number_card);
                                    dues = (TextView) itemView.findViewById(R.id.dues_flat_number_card);

                                }
                            }
                        });
                    }
                } catch (InterruptedException | ExecutionException | JSONException e) {
                    e.printStackTrace();
                }
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
                TextView cash, cheque, total, reset;
                public TotalCollectedMoney(View itemView) {
                    super(itemView);
                    cash = (TextView) itemView.findViewById(R.id.total_money_collected_in_cash);
                    cheque = (TextView) itemView.findViewById(R.id.total_money_collected_in_cheque);
                    total = (TextView) itemView.findViewById(R.id.total_money);
                    reset = (TextView) itemView.findViewById(R.id.reset_collected_money);
                }
            }

//            class FlatNumber extends RecyclerView.ViewHolder {
//
//                public FlatNumber(View itemView) {
//                    super(itemView);
//
//                }
//            }

            class FlatNumbers extends RecyclerView.ViewHolder {
                RecyclerView flatNumbersRecyclerView;
//                TextView flat_number, status;
                public FlatNumbers(View itemView) {
                    super(itemView);
//                    flat_number = (TextView) itemView.findViewById(R.id.flat_number);
//                    status = (TextView) itemView.findViewById(R.id.status);
                    flatNumbersRecyclerView = (RecyclerView) itemView.findViewById(R.id.at_a_glance_inner_recycler_view);
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
