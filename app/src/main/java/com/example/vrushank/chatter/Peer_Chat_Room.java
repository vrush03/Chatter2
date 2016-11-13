package com.example.vrushank.chatter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Peer_Chat_Room extends AppCompatActivity {

    Button add_user;
    EditText room_name;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> list_of_rooms = new ArrayList<>();
    String name, user_name, chatRoom;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peer__chat__room);

        listView = (ListView) findViewById(R.id.listView2);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list_of_rooms);
        listView.setAdapter(arrayAdapter);
        user_name = getIntent().getExtras().get("user_name").toString();
        room_name = (EditText) findViewById(R.id.room_name_edittext_2);
        //request_user_name();
        add_user = (Button) findViewById(R.id.btn_add_room_2);
        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = room_name.getText().toString();
                Map<String, Object> map = new HashMap<String, Object>();
                if (name.compareToIgnoreCase(user_name) > 0)
                    chatRoom = name + user_name;
                else
                    chatRoom = user_name + name;

                map.put(chatRoom, "");
                root.updateChildren(map);
            }
        });

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator i = dataSnapshot.getChildren().iterator();
                Set<String> set = new HashSet<String>();
                while (i.hasNext()) {
                    set.add(((DataSnapshot) i.next()).getKey());

                }
                list_of_rooms.clear();
                list_of_rooms.addAll(set);

                for (int j = 0; j < list_of_rooms.size(); j++) {
                    if (list_of_rooms.get(j).contains(user_name))
                        list_of_rooms.set(j, list_of_rooms.get(j).replace(user_name, ""));
                    else
                        list_of_rooms.remove(j);
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Chat_Room_2.class);
                intent.putExtra("room_name", ((TextView) view).getText().toString());
                intent.putExtra("user_name", user_name);
                startActivity(intent);
            }
        });

    }

    private void request_user_name() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter username of The Friend You want to chat:");
        final EditText input_field = new EditText(this);
        builder.setView(input_field);
        builder.setPositiveButton("Welcome!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                name = input_field.getText().toString();
                Map<String, Object> map = new HashMap<String, Object>();
                if (name.compareToIgnoreCase(user_name) > 0)
                    chatRoom = name + user_name;
                else
                    chatRoom = user_name + name;

                map.put(chatRoom, "");
                root.updateChildren(map);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });

        builder.show();
    }
}
