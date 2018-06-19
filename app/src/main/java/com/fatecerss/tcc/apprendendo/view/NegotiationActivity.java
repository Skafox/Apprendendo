package com.fatecerss.tcc.apprendendo.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.fatecerss.tcc.apprendendo.R;
import com.fatecerss.tcc.apprendendo.adapter.MessageAdapter;
import com.fatecerss.tcc.apprendendo.model.Message;
import com.fatecerss.tcc.apprendendo.model.Negotiation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.R.id.message;
import static android.R.id.progress;

public class NegotiationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText tf_msg;
    private ImageButton bt_send;
    private ListView listView;
    private ArrayList<Message> messages;
    private ArrayAdapter<Message> adapter;
    private int SENDERINTERESTED = -1;
    private Negotiation negotiation = new Negotiation();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference msgReference = databaseReference.child("messages");
    private DatabaseReference database;
    private ValueEventListener valueEventListenerMessages;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negotiation);

        toolbar = (Toolbar) findViewById(R.id.tb_negotiation);
        bt_send = (ImageButton) findViewById(R.id.bt_send);
        tf_msg = (EditText) findViewById(R.id.tf_msg);
        listView = (ListView) findViewById(R.id.listViewNegotiation);

        //Recover Arguments
        Bundle args = getIntent().getExtras();

        if (args != null){
            negotiation.setNegotiationId(args.getString("negotiationId"));
            negotiation.setInterestedId(args.getString("interestedId"));
            negotiation.setLastDate(args.getString("lastDate"));
            negotiation.setAdOwnerId(args.getString("adOwnerId"));
            negotiation.setAdOwnerName(args.getString("adOwnerName"));
            negotiation.setInterestedName(args.getString("interestedName"));
            negotiation.setInterestId(args.getString("interestId"));
        }

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            uId = firebaseUser.getUid();
        }

        if (uId.equalsIgnoreCase(negotiation.getInterestedId())){
            toolbar.setTitle(negotiation.getAdOwnerName());
            SENDERINTERESTED = 1;
        }
        else if (uId.equalsIgnoreCase(negotiation.getAdOwnerId())){
            toolbar.setTitle(negotiation.getInterestedName());
            SENDERINTERESTED = 0;
        }

        //Set Toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        //Load Messages
        messages = new ArrayList<>();
        adapter = new MessageAdapter(NegotiationActivity.this, messages);
        listView.setAdapter(adapter);

        readMessagesInDatabase();

        //Send Button
        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = tf_msg.getText().toString();

                if(messageText.isEmpty()){
                }else{

                    Date date = Calendar.getInstance().getTime();
                    DateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
                    String today = formatter.format(date);
                    databaseReference.child("negotiations").child(negotiation.getNegotiationId()).child("lastDate").setValue(today);

                    Message message = new Message();
                    message.setUserId(uId);
                    message.setMessage(messageText);

                    sendMessage(negotiation.getInterestId(), negotiation.getInterestedId(), negotiation.getAdOwnerId(), message);

                    sendMessage(negotiation.getInterestId(), negotiation.getAdOwnerId(), negotiation.getInterestedId(), message);

                }

            }
        });

    }

    private void readMessagesInDatabase() {

        if (SENDERINTERESTED == 1){
        database = msgReference.child(negotiation.getInterestId())
                               .child(uId)
                               .child(negotiation.getAdOwnerId());
        }
        else if (SENDERINTERESTED == 0){
        database = msgReference.child(negotiation.getInterestId())
                               .child(uId)
                               .child(negotiation.getInterestedId());
        }

        valueEventListenerMessages = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messages.clear();
                for ( DataSnapshot data: dataSnapshot.getChildren() ){
                    Message message = data.getValue(Message.class);
                    messages.add(message);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        database.addValueEventListener(valueEventListenerMessages);

    }

    private boolean sendMessage(String interestId, String idSender, String idReceiver, Message message){
        try{
            msgReference.child(interestId)
                    .child(idSender)
                    .child(idReceiver)
                    .push()
                    .setValue(message);

            tf_msg.setText("");
            return true;
        }catch (Exception e ){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        database.removeEventListener(valueEventListenerMessages);
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

}
