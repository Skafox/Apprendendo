package com.fatecerss.tcc.apprendendo.view;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

import static com.fatecerss.tcc.apprendendo.R.id.fab_add_Ad;

/**
 * Created by Sandro on 20/05/2018.
 */

public class AdSearchFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<Advertisement> ads;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference adsReference = databaseReference.child("advertisements");
    private ValueEventListener valueEventListenerAds = null;
    private FloatingActionButton fab_search_Ad;
    private String searchStatement = null;

    public AdSearchFragment() {
        // Required empty public constructor
    }

    //OTIMIZANDO OS RECURSOS DO ANDROID COM O CICLO DE VIDA DOS FRAGMENTS
    @Override
    public void onStart() {
        super.onStart();
        if (valueEventListenerAds != null){
            adsReference.addValueEventListener(valueEventListenerAds);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (valueEventListenerAds != null){
            adsReference.removeEventListener(valueEventListenerAds);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ads = new ArrayList<Advertisement>();

        View view = inflater.inflate(R.layout.fragment_ad_search, container, false);

        fab_search_Ad = (FloatingActionButton) view.findViewById(R.id.fab_search_Ad);
        listView = (ListView) view.findViewById(R.id.listSearch);
        adapter = new AdArrayAdapter(getActivity(), ads);
        listView.setAdapter(adapter);

        //Recuperar anúncios do Firebase

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Advertisement advertisement = ads.get(position);
                String adId = advertisement.getAdId();

                Bundle args = new Bundle();
                args.putString("adId",adId);
                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = new AdResultFragment();
                fragment.setArguments(args);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack("AdSearchFragment");
                fragmentTransaction.replace(R.id.layoutContentHome, fragment);
                fragmentTransaction.commit();

            }
        });

        fab_search_Ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getString(R.string.searchStatement));

                final EditText input = new EditText(getActivity());

                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        searchStatement = input.getText().toString();
                        if (searchStatement != null){
                            //Buscar Anúncio
                            adsReference = databaseReference.child("advertisements");
                            //filtrar na aplicação
                            valueEventListenerAds = new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //Limpa a lista
                                    ads.clear();
                                    //Popula a lista recuperada do firebase
                                    for (DataSnapshot data: dataSnapshot.getChildren()){
                                        Advertisement advertisement = data.getValue( Advertisement.class );
                                        if (advertisement.getSpecialty().contains(searchStatement) || advertisement.getTitle().contains(searchStatement) || advertisement.getDescription().contains(searchStatement)){
                                            ads.add(advertisement);
                                        }
                                        if (advertisement.isMorning() && searchStatement.contains("morning") || advertisement.isMorning() && searchStatement.contains("MORNING") ||
                                                advertisement.isMorning() && searchStatement.contains("Morning") ||
                                                advertisement.isMorning() && searchStatement.contains("MANHÃ") || advertisement.isMorning() && searchStatement.contains("manhã") ||
                                                advertisement.isMorning() && searchStatement.contains("Manhã") ||
                                                advertisement.isMorning() && searchStatement.contains("morgen") || advertisement.isMorning() && searchStatement.contains("MORGEN") ||
                                                advertisement.isMorning() && searchStatement.contains("Morgen")){
                                            ads.add(advertisement);
                                        }
                                        if (advertisement.isAfternoon() && searchStatement.contains("afternoon") || advertisement.isAfternoon() && searchStatement.contains("AFTERNOON") ||
                                                advertisement.isAfternoon() && searchStatement.contains("Afternoon") ||
                                                advertisement.isAfternoon() && searchStatement.contains("TARDE") || advertisement.isAfternoon() && searchStatement.contains("tarde") ||
                                                advertisement.isAfternoon() && searchStatement.contains("Tarde") ||
                                                advertisement.isAfternoon() && searchStatement.contains("nachmittag") || advertisement.isAfternoon() && searchStatement.contains("NACHMITTAG") ||
                                                advertisement.isAfternoon() && searchStatement.contains("Nachmittag")){
                                            ads.add(advertisement);
                                        }
                                        if (advertisement.isNight() && searchStatement.contains("night") || advertisement.isNight() && searchStatement.contains("NIGHT") ||
                                                advertisement.isNight() && searchStatement.contains("Night") ||
                                                advertisement.isNight() && searchStatement.contains("NOITE") || advertisement.isNight() && searchStatement.contains("noite") ||
                                                advertisement.isNight() && searchStatement.contains("Noite") ||
                                                advertisement.isNight() && searchStatement.contains("abend") || advertisement.isNight() && searchStatement.contains("ABEND") ||
                                                advertisement.isNight() && searchStatement.contains("Abend")){
                                            ads.add(advertisement);
                                        }
                                        if (advertisement.isSunday() && searchStatement.contains("night") || advertisement.isSunday() && searchStatement.contains("NIGHT") ||
                                                advertisement.isSunday() && searchStatement.contains("Night") ||
                                                advertisement.isSunday() && searchStatement.contains("NOITE") || advertisement.isSunday() && searchStatement.contains("noite") ||
                                                advertisement.isSunday() && searchStatement.contains("Noite") ||
                                                advertisement.isSunday() && searchStatement.contains("abend") || advertisement.isSunday() && searchStatement.contains("ABEND") ||
                                                advertisement.isSunday() && searchStatement.contains("Abend")){
                                            ads.add(advertisement);
                                        }
                                        if (advertisement.isMonday() && searchStatement.contains("night") || advertisement.isMonday() && searchStatement.contains("NIGHT") ||
                                                advertisement.isMonday() && searchStatement.contains("Night") ||
                                                advertisement.isMonday() && searchStatement.contains("NOITE") || advertisement.isMonday() && searchStatement.contains("noite") ||
                                                advertisement.isMonday() && searchStatement.contains("Noite") ||
                                                advertisement.isMonday() && searchStatement.contains("abend") || advertisement.isMonday() && searchStatement.contains("ABEND") ||
                                                advertisement.isMonday() && searchStatement.contains("Abend")){
                                            ads.add(advertisement);
                                        }
                                        if (advertisement.isTuesday() && searchStatement.contains("night") || advertisement.isTuesday() && searchStatement.contains("NIGHT") ||
                                                advertisement.isTuesday() && searchStatement.contains("Night") ||
                                                advertisement.isTuesday() && searchStatement.contains("NOITE") || advertisement.isTuesday() && searchStatement.contains("noite") ||
                                                advertisement.isTuesday() && searchStatement.contains("Noite") ||
                                                advertisement.isTuesday() && searchStatement.contains("abend") || advertisement.isTuesday() && searchStatement.contains("ABEND") ||
                                                advertisement.isTuesday() && searchStatement.contains("Abend")){
                                            ads.add(advertisement);
                                        }
                                        if (advertisement.isWednesday() && searchStatement.contains("night") || advertisement.isWednesday() && searchStatement.contains("NIGHT") ||
                                                advertisement.isWednesday() && searchStatement.contains("Night") ||
                                                advertisement.isWednesday() && searchStatement.contains("NOITE") || advertisement.isWednesday() && searchStatement.contains("noite") ||
                                                advertisement.isWednesday() && searchStatement.contains("Noite") ||
                                                advertisement.isWednesday() && searchStatement.contains("abend") || advertisement.isWednesday() && searchStatement.contains("ABEND") ||
                                                advertisement.isWednesday() && searchStatement.contains("Abend")){
                                            ads.add(advertisement);
                                        }
                                        if (advertisement.isThursday() && searchStatement.contains("night") || advertisement.isThursday() && searchStatement.contains("NIGHT") ||
                                                advertisement.isThursday() && searchStatement.contains("Night") ||
                                                advertisement.isThursday() && searchStatement.contains("NOITE") || advertisement.isThursday() && searchStatement.contains("noite") ||
                                                advertisement.isThursday() && searchStatement.contains("Noite") ||
                                                advertisement.isThursday() && searchStatement.contains("abend") || advertisement.isThursday() && searchStatement.contains("ABEND") ||
                                                advertisement.isThursday() && searchStatement.contains("Abend")){
                                            ads.add(advertisement);
                                        }
                                        if (advertisement.isFriday() && searchStatement.contains("night") || advertisement.isFriday() && searchStatement.contains("NIGHT") ||
                                                advertisement.isFriday() && searchStatement.contains("Night") ||
                                                advertisement.isFriday() && searchStatement.contains("NOITE") || advertisement.isFriday() && searchStatement.contains("noite") ||
                                                advertisement.isFriday() && searchStatement.contains("Noite") ||
                                                advertisement.isFriday() && searchStatement.contains("abend") || advertisement.isFriday() && searchStatement.contains("ABEND") ||
                                                advertisement.isFriday() && searchStatement.contains("Abend")){
                                            ads.add(advertisement);
                                        }
                                        if (advertisement.isSaturday() && searchStatement.contains("night") || advertisement.isSaturday() && searchStatement.contains("NIGHT") ||
                                                advertisement.isSaturday() && searchStatement.contains("Night") ||
                                                advertisement.isSaturday() && searchStatement.contains("NOITE") || advertisement.isSaturday() && searchStatement.contains("noite") ||
                                                advertisement.isSaturday() && searchStatement.contains("Noite") ||
                                                advertisement.isSaturday() && searchStatement.contains("abend") || advertisement.isSaturday() && searchStatement.contains("ABEND") ||
                                                advertisement.isSaturday() && searchStatement.contains("Abend")){
                                            ads.add(advertisement);
                                        }

                                    }
                                    //notifica o adapter que a lista foi mudada
                                    adapter.notifyDataSetChanged();
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            };
                            adsReference.addValueEventListener(valueEventListenerAds);
                            final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        }
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
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

    }

}