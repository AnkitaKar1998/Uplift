package com.example.uplift;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    TextView login;
    TextInputLayout email, number, password,firstName, lastName, companyName, ngoName;
    Button register;
    RadioGroup type;
    String inputType, inputName, inputFirstName, inputLastName, inputNgoName, inputCompanyName, inputEmail, inputNumber, inputPassword, inputKyc;
    ProgressDialog progressDialog;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
    FirebaseAuth firebaseAuth;
    String currentUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();


        progressDialog = new ProgressDialog(this);
        login = findViewById(R.id.tv_login);
        firstName = findViewById(R.id.til_first_name);
        lastName = findViewById(R.id.til_last_name);
        ngoName = findViewById(R.id.til_ngo_name);
        companyName = findViewById(R.id.til_company_name);
        email = findViewById(R.id.til_email);
        number = findViewById(R.id.til_number);
        password = findViewById(R.id.til_password);
        type = findViewById(R.id.rg_type);
        register = findViewById(R.id.btn_register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_individual:
                        firstName.setVisibility(View.VISIBLE);
                        lastName.setVisibility(View.VISIBLE);
                        ngoName.setVisibility(View.GONE);
                        companyName.setVisibility(View.GONE);
                        inputType = "Individual";
                        break;

                    case R.id.rb_non_profits:
                        firstName.setVisibility(View.GONE);
                        lastName.setVisibility(View.GONE);
                        ngoName.setVisibility(View.VISIBLE);
                        companyName.setVisibility(View.GONE);
                        inputType = "Non Profits";
                        break;

                    case R.id.rb_private:
                        firstName.setVisibility(View.GONE);
                        lastName.setVisibility(View.GONE);
                        ngoName.setVisibility(View.GONE);
                        companyName.setVisibility(View.VISIBLE);
                        inputType = "Private";
                        break;
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
//                RegisterDataModel registerDataModel = new RegisterDataModel(inputName, inputType, inputNumber, inputEmail,inputPassword);
            }
        });
    }


    private void registerUser(){

        inputFirstName = firstName.getEditText().getText().toString();
        inputLastName = lastName.getEditText().getText().toString();
        inputNgoName = ngoName.getEditText().getText().toString();
        inputCompanyName = companyName.getEditText().getText().toString();
        inputNumber = number.getEditText().getText().toString();
        inputEmail = email.getEditText().getText().toString();
        inputPassword = password.getEditText().getText().toString();
        inputKyc = "No";

        Log.d("urmi", "type: "+inputType);
        if(inputType.equals("Individual") && !inputFirstName.equals("") && !inputLastName.equals("")) {
            inputName = inputFirstName+" "+inputLastName;
        } else if (inputType.equals("Non Profits") && !inputNgoName.equals("")) {
            inputName = inputNgoName;
        } else if (inputType.equals("Private") && !inputCompanyName.equals("")) {
            inputName = inputCompanyName;
        } else {
            Toast.makeText(RegisterActivity.this, "Fill appropriate details", Toast.LENGTH_SHORT).show();
        }
        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(inputEmail, inputPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            currentUserId = firebaseAuth.getCurrentUser().getUid();
                            RegisterDataModel registerDataModel = new RegisterDataModel(inputName, inputType, inputNumber, inputEmail,inputPassword, inputKyc);
                            databaseReference.child(currentUserId).setValue(registerDataModel);
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            //display some message here
//                            Toast.makeText(MainActivity.this,"Successfully registered",Toast.LENGTH_LONG).show();
                        }else{
                            //display some message here
//                            Toast.makeText(MainActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });


    }
}
