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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";
    private FirebaseAuth mAuth;

    //    private TextView backTV;
    private EditText nameET, emailET, passwordET, confirmPasswordET;
    private Button signupButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

//        backTV = (TextView) findViewById(R.id.back_sign_up);
        nameET = (EditText) findViewById(R.id.sign_up_name);
        emailET = (EditText) findViewById(R.id.sign_up_email);
        passwordET = (EditText) findViewById(R.id.sign_up_password);
        confirmPasswordET = (EditText) findViewById(R.id.sign_up_confirm_password);
        signupButton = (Button) findViewById(R.id.sign_up_btn);

        mAuth = FirebaseAuth.getInstance();

        signupButton.setOnClickListener(view -> signup());

    }


    private void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signupButton.setEnabled(false);
        showProgressDialog(SignupActivity.this, "Creating Account...");

        String name = nameET.getText().toString();
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

        createAccount(name, email, password);

    }

    private void onSignupSuccess() {
        signupButton.setEnabled(true);
        hideProgressDialog();
        Intent i = new Intent(SignupActivity.this, MainActivity.class);
        SignupActivity.this.startActivity(i);
        finish();
    }

    private void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Sign up failed", Toast.LENGTH_LONG).show();
        hideProgressDialog();
        signupButton.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String name = nameET.getText().toString();
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        String confirmPassword = confirmPasswordET.getText().toString();

        if (name.isEmpty() || name.length() < 3) {  // check name validation
            nameET.setError("at least 3 characters");
            valid = false;
        } else {
            nameET.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) { // check email validation
            emailET.setError("enter a valid email address");
            valid = false;
        } else {
            emailET.setError(null);
        }

        if (password.isEmpty() || password.length() < 8 || password.length() > 15) { // check password validation
            passwordET.setError("between 8 and 15 characters");
            valid = false;
        } else {
            passwordET.setError(null);
        }

        if (!confirmPassword.equals(password)) { // check confirmPassword validation
            confirmPasswordET.setError("password not matches");
            valid = false;
        } else {
            confirmPasswordET.setError(null);
        }

        return valid;
    }

    private void createAccount(final String name, final String email, final String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");

                            saveProfileData(name, email);
                            onSignupSuccess();

                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            onSignupFailed();
                        }
                    }
                });

    }

    private void saveProfileData(String name, String email) {
        FirebaseUser user = SignupActivity.this.mAuth.getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("Email", email);
        userInfo.put("username", name);

        reference.child("users").child(user.getUid()).setValue(userInfo);

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