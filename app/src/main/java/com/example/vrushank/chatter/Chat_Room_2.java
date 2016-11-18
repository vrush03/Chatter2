package com.example.vrushank.chatter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Chat_Room_2 extends AppCompatActivity {

    Button btn_send_msg;
    EditText input_msg;
    TextView chat_conv;
    String user, room, temp, chatRoom;
    private MessageAdapter adapter;
    private RecyclerView recyclerView;
    private static ArrayList<Message> list = new ArrayList<>();

    private DatabaseReference root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat__room_2);

        recyclerView = (RecyclerView) findViewById(R.id.messageScrollView);
        LinearLayoutManager manager= new LinearLayoutManager(this);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);
        adapter = new MessageAdapter(this, list);
        recyclerView.setAdapter(adapter);
        btn_send_msg = (Button) findViewById(R.id.btn_send2);
        input_msg = (EditText) findViewById(R.id.msg_input2);
        //chat_conv = (TextView) findViewById(R.id.textView3);
        getData();
        user = getIntent().getExtras().get("user_name").toString();
        room = getIntent().getExtras().get("room_name").toString();
        chatRoom = "";
        if (room.compareToIgnoreCase(user) < 0)
            chatRoom = room + user;
        else
            chatRoom = user + room;

        setTitle(room);
        root = FirebaseDatabase.getInstance().getReference().child(chatRoom);
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
            boolean self;
            if(userchat.equals(user))
                self=true;
            else
                self=false;
            Message m = new Message(usermsg,userchat,self);
            list.add(m);

            //chat_conv.append(userchat + " : " + usermsg + " \n");
        }
        adapter = new MessageAdapter(Chat_Room_2.this, list);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Chat_Room_2.this, Peer_Chat_Room.class);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        list.clear();
        startActivity(startMain);

    }
    public void getData() {
        SharedPreferences preferences = this.getSharedPreferences("Username",this.MODE_PRIVATE);
        user = preferences.getString("User_name","");
    }
}

