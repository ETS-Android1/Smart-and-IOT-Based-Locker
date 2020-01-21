package com.elsawy.ahmed.fingerprintiot.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.elsawy.ahmed.fingerprintiot.R;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailET;
    private Button sendEmailBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailET = (EditText) findViewById(R.id.forgot_password_email_et);
        sendEmailBtn = (Button) findViewById(R.id.send_email_button);

        sendEmailBtn.setOnClickListener(view -> {
            sendEmailBtn.setEnabled(false);
            sendEmail();
        });

    }

    private void sendEmail() {
        String emailAddress = emailET.getText().toString();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        onSendComplete();
                    }
                });
    }

    private void onSendComplete() {
        Toast toast = Toast.makeText(ForgotPasswordActivity.this, "the email is sent", Toast.LENGTH_LONG);
        toast.getView().setBackgroundColor(getResources().getColor(R.color.colorAccent));
        toast.show();

        finish();
    }

}
