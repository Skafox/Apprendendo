package com.fatecerss.tcc.apprendendo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fatecerss.tcc.apprendendo.R;
import com.fatecerss.tcc.apprendendo.model.Interest;
import com.fatecerss.tcc.apprendendo.model.Negotiation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import static com.fatecerss.tcc.apprendendo.R.id.textViewTitle;

/**
 * Created by Sandro on 13/06/2018.
 */

public class NegotiationArrayAdapter extends ArrayAdapter<Negotiation> {

    private ArrayList<Negotiation> neg;
    private Context context;
    private TextView textViewName;
    private TextView textViewAdTitle;
    private TextView textViewDate;
    private TextView textViewOwnerName;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    public NegotiationArrayAdapter(Context c, ArrayList<Negotiation> objects){
        super(c, 0, objects);
        this.neg = objects;
        this.context = c;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String uId = null;
        
        Negotiation ne = getItem(position);

        View view = null;

        if (neg != null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.fragment_negotiation_list_item, parent, false);

            textViewName = (TextView) view.findViewById(R.id.textViewName);
            textViewAdTitle = (TextView) view.findViewById(R.id.textViewAdTitle);
            textViewDate = (TextView) view.findViewById(R.id.textViewDate);
            textViewOwnerName = (TextView) view.findViewById(R.id.textViewOwnerName);

            Negotiation negotiation = neg.get(position);
            String adTitleText = textViewAdTitle.getText().toString()+" "+negotiation.getAdTitle();
            String dateText = textViewDate.getText().toString().trim()+" "+negotiation.getLastDate();
            String userText = textViewName.getText().toString().trim()+" "+negotiation.getInterestedName();
            String ownerText = textViewOwnerName.getText().toString().trim()+" "+negotiation.getAdOwnerName();

            firebaseAuth = FirebaseAuth.getInstance();
            firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                uId = firebaseUser.getUid();
            }
            
            if (negotiation.getAdOwnerId().equals(uId)){
                textViewName.setText(negotiation.getInterestedName());
            }
            else if(negotiation.getInterestedId().equals(uId)){
                textViewName.setText(negotiation.getAdOwnerName());
            }

            textViewOwnerName.setText(ownerText);
            textViewAdTitle.setText(adTitleText);
            textViewDate.setText(negotiation.getLastDate());



        }


        return view;

    }
}