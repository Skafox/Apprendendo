package com.fatecerss.tcc.apprendendo.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fatecerss.tcc.apprendendo.R;
import com.fatecerss.tcc.apprendendo.model.Advertisement;
import com.fatecerss.tcc.apprendendo.model.Interest;
import com.fatecerss.tcc.apprendendo.model.Negotiation;
import com.fatecerss.tcc.apprendendo.model.User;
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
import java.util.Calendar;
import java.util.Date;

import static android.R.attr.fragment;
import static com.fatecerss.tcc.apprendendo.R.id.imageViewSendInterest;
import static com.fatecerss.tcc.apprendendo.R.id.textViewAdOwnerLink;

/**
 * Created by Sandro on 02/06/2018.
 */

public class InterestDetailsFragment extends Fragment implements View.OnClickListener {

    private TextView textViewAdLink;
    private TextView textViewSpecialtySelected;
    private TextView textViewInterestedLink;
    private TextView textViewInterestDate;
    private Button btNegotiate;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private Query interestsReference = databaseReference.child("interests");
    private Interest interest;
    private String interestId;
    private ValueEventListener interestValueEventListener;
    private String key;
    private AlertDialog.Builder warning;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interest_details, container, false);

        interestId = getArguments().getString("interestId");

        //INSTANCIA AS VARIAVEIS
        textViewAdLink = (TextView) view.findViewById(R.id.textViewAdLink);
        textViewSpecialtySelected = (TextView) view.findViewById(R.id.textViewSpecialtySelected);
        textViewInterestedLink = (TextView) view.findViewById(R.id.textViewInterestedLink);
        textViewInterestDate = (TextView) view.findViewById(R.id.textViewInterestDate);
        btNegotiate = (Button) view.findViewById(R.id.btNegotiate);

        interestsReference = databaseReference.child("interests");

        readInterestFromDatabase(interestId);

        textViewAdLink.setOnClickListener(this);

        textViewInterestedLink.setOnClickListener(this);

        btNegotiate.setOnClickListener(this);

        return view;

    }


    @Override
    public void onClick(View v) {

        if (v == textViewAdLink) {
            String adId = interest.getAdId();

            Bundle args = new Bundle();
            args.putString("adId", adId);
            FragmentManager fragmentManager = getFragmentManager();
            Fragment fragment = new AdResultInterestFragment();
            fragment.setArguments(args);

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack("InterestFragment");
            fragmentTransaction.replace(R.id.layoutContentHome, fragment);
            fragmentTransaction.commit();

        }

        if (v == textViewInterestedLink){
            String uId = interest.getInterestedId();

            Bundle args = new Bundle();
            args.putString("uId", uId);
            FragmentManager fragmentManager = getFragmentManager();
            Fragment fragment = new ProfileDetailsFragment();
            fragment.setArguments(args);

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack("UserFragment");
            fragmentTransaction.replace(R.id.layoutContentHome, fragment);
            fragmentTransaction.commit();

        }

        if (v == btNegotiate){

            warning = new AlertDialog.Builder(getActivity());
            warning.setMessage(getString(R.string.sendNegotiation));
            warning.setCancelable(true);
            warning.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    Date date = Calendar.getInstance().getTime();
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String today = formatter.format(date);

                    key = database.getReference("negotiations").push().getKey();

                    Negotiation negotiation = new Negotiation(key, interest.getInterestId(), interest.getAdTitle(), interest.getOwnerId(),
                            interest.getOwnerName(), interest.getInterestedId(), interest.getInterestedName());

                    negotiation.setLastDate(today);

                    try{
                        databaseReference.child("negotiations").child(key).setValue(negotiation);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    Intent negotiationIntent = new Intent(getActivity(), NegotiationActivity.class);

                    //passando argumentos da negotiation encontrado

                    negotiationIntent.putExtra("interestId", negotiation.getInterestId());
                    negotiationIntent.putExtra("negotiationId",negotiation.getNegotiationId());
                    negotiationIntent.putExtra("adOwnerName",negotiation.getAdOwnerName());
                    negotiationIntent.putExtra("interestedName",negotiation.getInterestedName());
                    negotiationIntent.putExtra("adOwnerId",negotiation.getAdOwnerId());
                    negotiationIntent.putExtra("interestedId",negotiation.getInterestedId());
                    negotiationIntent.putExtra("lastDate",negotiation.getLastDate());

                    startActivity(negotiationIntent);

                }
                /*fragment = new AdListFragment();
                if (fragment != null) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.layoutContentHome, fragment);
                    fragmentTransaction.commit();
                }*/
            });
            warning.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                }});
            warning.show();

        }
    }

    public void readInterestFromDatabase(String interestId){
        interestsReference = databaseReference.child("interests").child(interestId);
        interestValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                interest = dataSnapshot.getValue(Interest.class);
                textViewAdLink.setText(interest.getAdTitle());
                textViewAdLink.setPaintFlags(textViewAdLink.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
                textViewInterestedLink.setText(interest.getInterestedName());
                textViewInterestedLink.setPaintFlags(textViewInterestedLink.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
                textViewInterestDate.setText(interest.getInterestDate());
                textViewSpecialtySelected.setText(interest.getAdSpecialty());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        interestsReference.addValueEventListener(interestValueEventListener);
    }

}
