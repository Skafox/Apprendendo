package com.fatecerss.tcc.apprendendo.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fatecerss.tcc.apprendendo.R;
import com.fatecerss.tcc.apprendendo.adapter.AdArrayAdapter;
import com.fatecerss.tcc.apprendendo.adapter.NegotiationArrayAdapter;
import com.fatecerss.tcc.apprendendo.model.Advertisement;
import com.fatecerss.tcc.apprendendo.model.Negotiation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.fatecerss.tcc.apprendendo.R.id.fab_add_Ad;

/**
 * Created by Sandro on 13/06/2018.
 */

public class NegotiationListFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<Negotiation> neg;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private Query negReference = databaseReference.child("negotiations");
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String uId;
    private ValueEventListener valueEventListenerAds;

    public NegotiationListFragment() {
        // Required empty public constructor
    }

    //OTIMIZANDO OS RECURSOS DO ANDROID COM O CICLO DE VIDA DOS FRAGMENTS
    @Override
    public void onStart() {
        super.onStart();
        negReference.addValueEventListener(valueEventListenerAds);
    }

    @Override
    public void onStop() {
        super.onStop();
        negReference.removeEventListener(valueEventListenerAds);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        neg = new ArrayList<Negotiation>();

        View view = inflater.inflate(R.layout.fragment_negotiation_list, container, false);

        listView = (ListView) view.findViewById(R.id.listNegotiation);
        adapter = new NegotiationArrayAdapter(getActivity(), neg);
        listView.setAdapter(adapter);

        //Recuperar anúncios do Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            uId = firebaseUser.getUid();
        }
        //negReference = databaseReference.child("negotiations").orderByChild("uId").equalTo(uId);
        negReference = databaseReference.child("negotiations");
        valueEventListenerAds = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Limpa a lista
                neg.clear();
                //Popula a lista recuperada do firebase
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Negotiation negotiation = data.getValue( Negotiation.class );
                    if (negotiation.getAdOwnerId().equals(uId) || negotiation.getInterestedId().equals(uId)){
                        neg.add(negotiation);
                    }

                }
                //notifica o adapter que a lista foi mudada
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Negotiation negotiation = neg.get(position);

                Intent negotiationIntent = new Intent(getActivity(), NegotiationActivity.class);

                //passando argumentos do negotiation encontrado

                negotiationIntent.putExtra("interestId", negotiation.getInterestId());
                negotiationIntent.putExtra("negotiationId",negotiation.getNegotiationId());
                negotiationIntent.putExtra("adOwnerName",negotiation.getAdOwnerName());
                negotiationIntent.putExtra("interestedName",negotiation.getInterestedName());
                negotiationIntent.putExtra("adOwnerId",negotiation.getAdOwnerId());
                negotiationIntent.putExtra("interestedId",negotiation.getInterestedId());
                negotiationIntent.putExtra("lastDate",negotiation.getLastDate());

                startActivity(negotiationIntent);

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

}
