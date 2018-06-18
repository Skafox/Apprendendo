package com.fatecerss.tcc.apprendendo.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fatecerss.tcc.apprendendo.R;
import com.fatecerss.tcc.apprendendo.model.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.resource;

/**
 * Created by Sandro on 17/06/2018.
 */

public class MessageAdapter extends ArrayAdapter<Message> {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private Context context;
    private ArrayList<Message> messages;

    public MessageAdapter(@NonNull Context c, @NonNull ArrayList<Message> objects) {
        super(c, 0, objects);
        this.context = c;
        this.messages = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = null;

        if (messages != null){

            LayoutInflater inflater  = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            Message message = messages.get(position);
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseUser = firebaseAuth.getCurrentUser();
            String uId = null;
            if (firebaseUser != null) {
                uId = firebaseUser.getUid();
            }

            if (uId.equals(message.getUserId())){
                view = inflater.inflate(R.layout.message_item_right, parent, false);
            }
            else{
                view = inflater.inflate(R.layout.message_item_left, parent, false);
            }


            TextView messageText = (TextView) view.findViewById(R.id.textViewMessage);
            messageText.setText(message.getMessage());


        }


        return view;
    }

}
