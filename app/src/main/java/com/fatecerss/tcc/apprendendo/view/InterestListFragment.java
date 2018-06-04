package com.fatecerss.tcc.apprendendo.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
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
import com.fatecerss.tcc.apprendendo.adapter.InterestArrayAdapter;
import com.fatecerss.tcc.apprendendo.model.Advertisement;
import com.fatecerss.tcc.apprendendo.model.Interest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Sandro on 31/05/2018.
 */

public class InterestListFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<Interest> ints;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private Query intsReference = databaseReference.child("interests");
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String uId;
    private ValueEventListener valueEventListenerAds;

    public InterestListFragment() {
        // Required empty public constructor
    }

    //OTIMIZANDO OS RECURSOS DO ANDROID COM O CICLO DE VIDA DOS FRAGMENTS
    @Override
    public void onStart() {
        super.onStart();
        intsReference.addValueEventListener(valueEventListenerAds);
    }

    @Override
    public void onStop() {
        super.onStop();
        intsReference.removeEventListener(valueEventListenerAds);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ints = new ArrayList<Interest>();

        View view = inflater.inflate(R.layout.fragment_interest_list, container, false);

        listView = (ListView) view.findViewById(R.id.listInterest);
        adapter = new InterestArrayAdapter(getActivity(), ints);
        listView.setAdapter(adapter);

        //Recuperar anúncios do Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            uId = firebaseUser.getUid();
        }
        intsReference = databaseReference.child("interests");
        valueEventListenerAds = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Limpa a lista
                ints.clear();
                //Popula a lista recuperada do firebase
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Interest interest = data.getValue( Interest.class );
                    if(interest.getOwnerId().equalsIgnoreCase(uId)){
                        ints.add(interest);
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

                Interest interest = ints.get(position);
                String interestId = interest.getInterestId();

                Bundle args = new Bundle();
                args.putString("interestId",interestId);
                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = new InterestDetailsFragment();
                fragment.setArguments(args);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack("InterestListFragment");
                fragmentTransaction.replace(R.id.layoutContentHome, fragment);
                fragmentTransaction.commit();
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
