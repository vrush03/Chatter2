package com.example.vrushank.chatter;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.R.attr.name;

public class Chat_room extends AppCompatActivity {

    Button btn_send_msg;
    EditText input_msg;
    TextView chat_conv;
    String user, room, temp;
    private DatabaseReference root;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_room);
        btn_send_msg = (Button) findViewById(R.id.btn_send);
        input_msg = (EditText) findViewById(R.id.msg_input);
        chat_conv = (TextView) findViewById(R.id.textView);
        user = getIntent().getExtras().get("user_name").toString();
        room = getIntent().getExtras().get("room_name").toString();
        setTitle(" Room - " + room);
        root = FirebaseDatabase.getInstance().getReference().child(room);

        btn_send_msg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<String, Object>();
                temp = root.push().getKey();
                root.updateChildren(map);

                DatabaseReference message_root = root.child(temp);
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("name", user);
                map2.put("msg", input_msg.getText().toString());

                message_root.updateChildren(map2);
                input_msg.setText("");
            }
        });
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                append_chat(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                append_chat(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void append_chat(DataSnapshot datasnapshot) {
        String userchat, usermsg;
        Iterator i = datasnapshot.getChildren().iterator();
        while (i.hasNext()) {
            usermsg = (String) ((DataSnapshot) i.next()).getValue();
            userchat = (String) ((DataSnapshot) i.next()).getValue();
            chat_conv.append(userchat + " : " + usermsg + " \n");
        }

    }
    public void getData() {
        SharedPreferences preferences = this.getSharedPreferences("Username",this.MODE_PRIVATE);
        user = preferences.getString("User_name","");
    }


}
