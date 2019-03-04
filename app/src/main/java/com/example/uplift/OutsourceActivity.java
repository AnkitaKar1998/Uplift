package com.example.uplift;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class OutsourceActivity extends AppCompatActivity {

    TextInputLayout projectname,budget,milestonetext1,milestonevalue1,milestonetext2,milestonevalue2,milestonetext3,milestonevalue3,projectdescription;
    String val;
    Button submit;
    TextInputEditText textInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outsource);

        projectname = findViewById(R.id.ProjectName);
        budget = findViewById(R.id.Budget);
        milestonetext1 = findViewById(R.id.Milestone1);
        milestonetext2 = findViewById(R.id.Milestone2);
        milestonetext3 = findViewById(R.id.Milestone3);
        milestonevalue1 = findViewById(R.id.Milestone11);
        milestonevalue2 = findViewById(R.id.Milestone21);
        milestonevalue3 = findViewById(R.id.Milestone31);
        submit = findViewById(R.id.submit);
        projectdescription = findViewById(R.id.ProjectDesc);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( TextUtils.isEmpty(projectname.getEditText().getText())){
                    projectname.getEditText().setError( "Project Name is required!" );
                }
                if( TextUtils.isEmpty(projectdescription.getEditText().getText())){
                    projectdescription.getEditText().setError( "Project Name is required!" );
                }
                if(TextUtils.isEmpty(budget.getEditText().getText())){
                    budget.getEditText().setError( "Budget is required!" );
                }
                if(TextUtils.isEmpty(milestonetext1.getEditText().getText())){
                    milestonetext1.getEditText().setError( "Milestone is required!" );
                }
                if(TextUtils.isEmpty(milestonetext2.getEditText().getText())){
                    milestonetext2.getEditText().setError( "Milestone is required!" );
                }
                if(TextUtils.isEmpty(milestonetext3.getEditText().getText())){
                    milestonetext3.getEditText().setError( "Milestone is required!" );
                }
                else {
                    Toast.makeText(OutsourceActivity.this,"Sucessful Submission",Toast.LENGTH_LONG);
                }
            }
        });

    }
}

