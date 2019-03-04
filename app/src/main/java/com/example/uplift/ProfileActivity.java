package com.example.uplift;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;

public class ProfileActivity extends AppCompatActivity {

    ImageView editName, editEmail, editNumber;
    EditText details;
    Button changeDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editName = findViewById(R.id.iv_edit_name);
        editEmail = findViewById(R.id.iv_edit_email);
        editNumber = findViewById(R.id.iv_edit_number);

        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MaterialDialog materialDialog = new MaterialDialog.Builder(ProfileActivity.this)
                        .customView(R.layout.dialog_change_details, false)
                        .title("Change Name")
                        .show();
                details = (EditText) materialDialog.findViewById(R.id.et_details);
                changeDetails = (Button) materialDialog.findViewById(R.id.btn_change_details);

                changeDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newName = details.getText().toString();
                        // send this sting to database
                    }
                });
            }
        });

        editEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MaterialDialog materialDialog = new MaterialDialog.Builder(ProfileActivity.this)
                        .customView(R.layout.dialog_change_details, false)
                        .title("Change Email")
                        .show();
                details = (EditText) materialDialog.findViewById(R.id.et_details);
                changeDetails = (Button) materialDialog.findViewById(R.id.btn_change_details);

                changeDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newName = details.getText().toString();
                        // send this sting to database
                    }
                });
            }
        });

        editNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MaterialDialog materialDialog = new MaterialDialog.Builder(ProfileActivity.this)
                        .customView(R.layout.dialog_change_details, false)
                        .title("Change Number")
                        .show();
                details = (EditText) materialDialog.findViewById(R.id.et_details);
                changeDetails = (Button) materialDialog.findViewById(R.id.btn_change_details);

                changeDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newName = details.getText().toString();
                        // send this sting to database
                    }
                });
            }
        });
    }
}
