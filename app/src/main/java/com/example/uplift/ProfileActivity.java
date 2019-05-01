package com.example.uplift;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    TextView tvName, tvEmail, tvNumber, tvType, tvKyc;
    ImageView editName, editEmail, editNumber;
    EditText details;
    Button changeDetails, doKyc, proceed;

    RadioGroup selectDocument;
    RadioButton type;
    EditText documentName;

    static String kyc;

    DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    String currentUserId;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        currentUserId = firebaseAuth.getCurrentUser().getUid();

        tvName = findViewById(R.id.tv_name);
        tvEmail = findViewById(R.id.tv_email);
        tvNumber = findViewById(R.id.tv_number);
        tvType = findViewById(R.id.tv_type);
        tvKyc = findViewById(R.id.tv_kyc);
        editName = findViewById(R.id.iv_edit_name);
        editEmail = findViewById(R.id.iv_edit_email);
        editNumber = findViewById(R.id.iv_edit_number);

        doKyc = findViewById(R.id.btn_do_kyc);

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


        doKyc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog materialDialog = new MaterialDialog.Builder(ProfileActivity.this)
                        .customView(R.layout.dialog_do_kyc, false)
                        .title("KYC Form")
                        .show();

                selectDocument = (RadioGroup) materialDialog.findViewById(R.id.rg_ducument_type);
                documentName = (EditText) materialDialog.findViewById(R.id.et_document);
                proceed = (Button) materialDialog.findViewById(R.id.btn_proceed);

                int id = selectDocument.getCheckedRadioButtonId();
                type = (RadioButton) materialDialog.findViewById(id);

                proceed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String docType = type.getText().toString();
                        String documentNo = documentName.getText().toString();

                        Log.d("urmi", "no: "+documentNo);

                        final RequestQueue queue = Volley.newRequestQueue(ProfileActivity.this);
                        final String url = "http://ec2-13-233-49-83.ap-south-1.compute.amazonaws.com:2309/appsearch";

                        JSONObject req = new JSONObject();
                        try {
                            req.put("docno", documentNo);
                            req.put("email","uk80718@gmail.com");
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url,
                                req,
                                new Response.Listener<JSONObject>(){
                                    @Override
                                    public void onResponse(JSONObject response) {
//                                        result.setText(response.toString());
                                        JSONObject b = response;
                                        try {
                                            String n = b.get("number").toString();
                                            Log.d("urmi", "n: "+n);
                                            if(Integer.parseInt(n) > 0) {
                                                Toast.makeText(ProfileActivity.this, "KYC already done", Toast.LENGTH_SHORT).show();
                                                kyc = "Yes";

                                                mDatabase.child("users").child(currentUserId).child("kyc").setValue(kyc);
                                            }
//                                            textView.setText(n);
                                        }
                                        catch (JSONException e){
                                            e.printStackTrace();
                                            String n = "-1";
                                        }

                                    }
                                }
                                , new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
//                                result.setText("That didn't work!");
                                Toast.makeText(ProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                        queue.add(jsonRequest);
                        // Code here executes on main thread after user presses button
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mDatabase.child("users").child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                String email = dataSnapshot.child("email").getValue(String.class);
                String number = dataSnapshot.child("number").getValue(String.class);
                String type = dataSnapshot.child("type").getValue(String.class);
                String kyc = dataSnapshot.child("kyc").getValue(String.class);

                tvName.setText(name);
                tvEmail.setText(email);
                tvNumber.setText(number);
                tvType.setText(type);
                tvKyc.setText(kyc);

                if(kyc.equals("Yes")) {
                    doKyc.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}



