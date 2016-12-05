package com.example.amit.dhareshwar_maintenance;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "stored_login_information";

    EditText username, password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
   }

    public void loginButtonClicked(View v) {
        final String usernameString = this.username.getText().toString();
        final String passwordString = this.password.getText().toString();
        try {
            String response = new LoginToServer(this).execute(usernameString,passwordString).get();
            JSONObject serverResponse = new JSONObject(response);
            if (serverResponse.getBoolean("success")) {
                SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                sharedPreferencesEditor.putString("username", usernameString);
                sharedPreferencesEditor.putString("password", passwordString);
                sharedPreferencesEditor.apply();


                final AlertDialog.Builder newPasswordDialogBuilder = new AlertDialog.Builder(this);
                newPasswordDialogBuilder.setTitle(R.string.password_change_dialog);
                final View alertDialogView = getLayoutInflater().inflate(R.layout.password_change, null);
                newPasswordDialogBuilder.setView(alertDialogView);
                newPasswordDialogBuilder.setCancelable(false);
                newPasswordDialogBuilder.setPositiveButton(R.string.confirm, null);
                final AlertDialog newPasswordDialog = newPasswordDialogBuilder.create();
                newPasswordDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(final DialogInterface dialogInterface) {
                        Button confirmButton = newPasswordDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        confirmButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                EditText newPassword = (EditText) alertDialogView.findViewById(R.id.newPassword);
                                EditText confirmNewPassword = (EditText) alertDialogView.findViewById(R.id.confirmNewPassword);
                                TextView passwordInstructions = (TextView) alertDialogView.findViewById(R.id.passwordInstructions);
                                String newPasswordString = newPassword.getText().toString();
                                String confirmNewPasswordString = confirmNewPassword.getText().toString();

                                if (newPasswordString.equals(confirmNewPasswordString)) {
                                    if (newPasswordString.length() < 8) {
                                        passwordInstructions.setText(R.string.password_length_inadequate);
                                        passwordInstructions.setVisibility(View.VISIBLE);
                                    } else {
                                        try {
                                            JSONObject serverResponse = new ChangePassword().execute(usernameString, newPasswordString, getResources().getString(R.string.password_change_PHP)).get();
                                            if (serverResponse.getBoolean("success")) {
                                                dialogInterface.dismiss();
                                                Intent intent = new Intent(MainActivity.this,UserProfileActivity.class);
                                                startActivity(intent);
                                                Toast.makeText(MainActivity.this, "Password changed successfully", Toast.LENGTH_LONG).show();
                                            }
                                        } catch (InterruptedException | ExecutionException | JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } else {
                                    passwordInstructions.setText(R.string.passwords_did_not_match);
                                    passwordInstructions.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
                });

                newPasswordDialog.show();
            }
            else {
                AlertDialog.Builder enterCorrectLoginInfo = new AlertDialog.Builder(this);
                enterCorrectLoginInfo.setTitle(R.string.enter_correct_login_info);
                enterCorrectLoginInfo.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                enterCorrectLoginInfo.show();
            }

        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
    }
}

//        newPasswordDialogBuilder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                EditText newPassword = (EditText) alertDialogView.findViewById(R.id.newPassword);
//                EditText confirmNewPassword = (EditText) alertDialogView.findViewById(R.id.confirmNewPassword);
//                TextView passwordsDoNotMatch = (TextView) alertDialogView.findViewById(R.id.passwordsDoNotMatch);
//
//                String newPasswordString = newPassword.getText().toString();
//                String confirmNewPasswordString = confirmNewPassword.getText().toString();
//                if(newPasswordString.equals(confirmNewPasswordString)) {
////                    JSONObject serverResponse = new ChangePassword().execute(username,password).get();
//                }
//                else {
////                    passwordsDoNotMatch.setVisibility(TextView.VISIBLE);
////                    final AlertDialog.Builder passwdInstructionsDialog = new AlertDialog.Builder(MainActivity.this);
////                    passwdInstructionsDialog.setTitle(R.string.passwords_did_not_match);
////                    passwdInstructionsDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
////                        @Override
////                        public void onClick(DialogInterface dialogInterface, int i) {
//////                            dialogInterface.dismiss();
////
////                            newPasswordDialogBuilder.show();
////
////                        }
////                    });
////                    passwdInstructionsDialog.show();
//                }
//            }
//        });

