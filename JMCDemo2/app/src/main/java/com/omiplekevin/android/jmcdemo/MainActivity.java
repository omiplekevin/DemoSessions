package com.omiplekevin.android.jmcdemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.omiplekevin.android.jmcdemo.helpers.Constants;
import com.omiplekevin.android.jmcdemo.models.UserModel;
import com.omiplekevin.android.jmcdemo.utilities.Utilities;

import java.util.Arrays;
import java.util.TreeMap;

public class MainActivity extends Activity implements View.OnClickListener{

    private EditText nameField, passwordField;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameField = (EditText) findViewById(R.id.nameField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        submitBtn = (Button) findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitBtn:
                final String username = nameField.getText().toString().trim();
                final String password = passwordField.getText().toString().trim();

                new UserLogin().execute(username, password);

                break;
        }
    }

    private class UserLogin extends AsyncTask<String, Void, UserModel> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("Logging in");
            progressDialog.show();
        }

        @Override
        protected UserModel doInBackground(String... params) {
            Log.e("MainActivity", "sending request to " + Constants.API_URL);
            Log.e("MainActivity", "parameters: " + Arrays.deepToString(params));

            //simulation of network response delay
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String response = Utilities.sendHttpRequest(Constants.API_URL, "GET", null);

            /*TreeMap<String, String> postParams = new TreeMap<>();
            postParams.put("username", params[0]);
            postParams.put("password", params[1]);
            String response = Utilities.sendHttpRequest(Constants.API_URL, "POST", postParams);*/

            UserModel userModel = new Gson().fromJson(response, UserModel.class);
            Log.e("MainActivity", userModel.id + "\n"
                    + userModel.user_profile.first_name + "\n"
                    + userModel.user_profile.middle_initial + "\n"
                    + userModel.user_profile.last_name);

            return userModel;
        }

        @Override
        protected void onPostExecute(UserModel modelResponse) {
            super.onPostExecute(modelResponse);

            if (progressDialog != null) {
                progressDialog.dismiss();
            }

            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
