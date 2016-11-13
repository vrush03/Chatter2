package com.example.vrushank.chatter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity implements View.OnClickListener {

    String name;
    TextView textViewemail, textViewuser;
    Button logout, enterChat, butPeer;
    EditText ed1;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        request_user_name();
        textViewemail = (TextView) findViewById(R.id.textViewUserEmail);
        textViewemail.setText("Welcome " + user.getEmail());
        textViewuser = (TextView) findViewById(R.id.textViewUserName);
        textViewuser.setText(name);
        logout = (Button) findViewById(R.id.buttonLogout);
        enterChat = (Button) findViewById(R.id.buttonEnter);

        butPeer = (Button) findViewById(R.id.buttonPeer);
        butPeer.setOnClickListener(this);
        logout.setOnClickListener(this);
        enterChat.setOnClickListener(this);

    }

    private void request_user_name() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter username:");
        final EditText input_field = new EditText(this);
        builder.setView(input_field);
        builder.setPositiveButton("Welcome!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                name = input_field.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                request_user_name();
            }
        });

        builder.show();
    }

    @Override
    public void onClick(View v) {
        if (v == logout) {
            firebaseAuth.signOut();
            finish();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        if (v == enterChat) {
            finish();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("user_name", name);
            startActivity(intent);
        }

        if (v == butPeer) {
            finish();
            Intent intent = new Intent(this, Peer_Chat_Room.class);
            intent.putExtra("user_name", name);
            startActivity(intent);

        }
    }

    public void callme(View v) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        ed1 = (EditText) findViewById(R.id.editText);
        intent.setData(Uri.parse("tel:" + ed1.getText().toString()));

        try {
            startActivity(intent);
            Log.d("sdf", "working");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
