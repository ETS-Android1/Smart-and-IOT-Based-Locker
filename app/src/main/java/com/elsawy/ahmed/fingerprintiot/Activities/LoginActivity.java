package com.elsawy.ahmed.fingerprintiot.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.elsawy.ahmed.fingerprintiot.MainActivity;
import com.elsawy.ahmed.fingerprintiot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;


    private EditText emailET, passwordET;
    private Button loginButton;
    private TextView forgotPasswordTV, registerTV;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailET = (EditText) findViewById(R.id.login_email);
        passwordET = (EditText) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.login_button);
        forgotPasswordTV = (TextView) findViewById(R.id.forgot_password_tv);
        registerTV = (TextView) findViewById(R.id.login_register_tv);

        this.mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(view -> login());
        registerTV.setOnClickListener(view -> openSignupActivity());
        forgotPasswordTV.setOnClickListener(view -> openForgotPasswordActivity());


    }

    private void openForgotPasswordActivity() {
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private void openSignupActivity() {
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
    }

    private void login() {
        Log.d(TAG, "login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);
        showProgressDialog(LoginActivity.this, "login...");

        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

        this.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new loginTask());

    }

    private void onLoginSuccess() {
        loginButton.setEnabled(true);
        hideProgressDialog();
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        LoginActivity.this.startActivity(i);
        finish();
    }

    private void onLoginFailed() {
        Toast.makeText(getBaseContext(), "login failed", Toast.LENGTH_LONG).show();
        hideProgressDialog();
        loginButton.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) { // check email validation
            emailET.setError("enter a valid email address");
            valid = false;
        } else {
            emailET.setError(null);
        }

        if (password.isEmpty() || password.length() < 8 || password.length() > 15) { // check password validation
            passwordET.setError("wrong password");
            valid = false;
        } else {
            passwordET.setError(null);
        }

        return valid;
    }

    private class loginTask implements OnCompleteListener<AuthResult> {

        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {

                Log.d(TAG, "signInWithEmail:success");

                onLoginSuccess();

            } else {
                Log.w(TAG, "signInWithEmail:failure", task.getException());

                onLoginFailed();
            }
        }
    }

    public void showProgressDialog(Context context, String message) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = new ProgressDialog(context,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

}