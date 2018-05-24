package com.fatecerss.tcc.apprendendo.view;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
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
import com.fatecerss.tcc.apprendendo.model.Advertisement;
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
 * A simple {@link Fragment} subclass.
 */
public class AdListFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<Advertisement> ads;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private Query adsReference = databaseReference.child("advertisements");
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String uId;
    private ValueEventListener valueEventListenerAds;
    private FloatingActionButton fab_add_Ad;

    public AdListFragment() {
        // Required empty public constructor
    }

    //OTIMIZANDO OS RECURSOS DO ANDROID COM O CICLO DE VIDA DOS FRAGMENTS
    @Override
    public void onStart() {
        super.onStart();
        adsReference.addValueEventListener(valueEventListenerAds);
    }

    @Override
    public void onStop() {
        super.onStop();
        adsReference.removeEventListener(valueEventListenerAds);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ads = new ArrayList<Advertisement>();

        View view = inflater.inflate(R.layout.fragment_ad_list, container, false);

        fab_add_Ad = (FloatingActionButton) view.findViewById(R.id.fab_add_Ad);
        listView = (ListView) view.findViewById(R.id.listAdvertise);
        adapter = new AdArrayAdapter(getActivity(), ads);
        listView.setAdapter(adapter);

        //Recuperar anúncios do Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            uId = firebaseUser.getUid();
        }
        adsReference = databaseReference.child("advertisements").orderByChild("uId").equalTo(uId);
        valueEventListenerAds = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Limpa a lista
                ads.clear();
                //Popula a lista recuperada do firebase
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Advertisement advertisement = data.getValue( Advertisement.class );
                    ads.add(advertisement);
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

                Advertisement advertisement = ads.get(position);
                String adId = advertisement.getAdId();

                Bundle args = new Bundle();
                args.putString("adId",adId);
                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = new AdEditFragment();
                fragment.setArguments(args);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack("AdListFragment");
                fragmentTransaction.replace(R.id.layoutContentHome, fragment);
                fragmentTransaction.commit();
            }
        });

        fab_add_Ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = new AdCreateFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack("AdListFragment");
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
