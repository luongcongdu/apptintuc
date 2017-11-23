package com.blogspot.luongcongdu.appdoctintuc.V;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blogspot.luongcongdu.appdoctintuc.R;

public class InfoActivity extends AppCompatActivity {
    TextView txtEmailContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        txtEmailContact = (TextView) findViewById(R.id.txt_email_contact);
        txtEmailContact.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
