package com.fatecerss.tcc.apprendendo.view;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fatecerss.tcc.apprendendo.R;
import com.fatecerss.tcc.apprendendo.model.Advertisement;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sandro on 08/05/2018.
 */

public class AdCreateFragment extends Fragment implements View.OnClickListener {


    //Variáveis
    private EditText editTextTitle;
    private EditText editTextDescription;
    private CheckBox chk_sunday;
    private CheckBox chk_monday;
    private CheckBox chk_tuesday;
    private CheckBox chk_wednesday;
    private CheckBox chk_thursday;
    private CheckBox chk_friday;
    private CheckBox chk_saturday;
    private CheckBox chk_morning;
    private CheckBox chk_afternoon;
    private CheckBox chk_night;
    private Button bt_ad;
    private Spinner spinnerSpecialty;
    private String specialty;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String uId;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private String key;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference adsReference;
    private Advertisement advertisement;
    private ProgressDialog progressDialog;

    //Método 1
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ad_create, container, false);

        //INSTANCIA AS VARIAVEIS
        editTextTitle = (EditText) view.findViewById(R.id.editTextTitle);
        editTextDescription = (EditText) view.findViewById(R.id.editTextDescription);
        chk_sunday = (CheckBox) view.findViewById(R.id.chk_sunday);
        chk_monday = (CheckBox) view.findViewById(R.id.chk_monday);
        chk_tuesday = (CheckBox) view.findViewById(R.id.chk_tuesday);
        chk_wednesday = (CheckBox) view.findViewById(R.id.chk_wednesday);
        chk_thursday = (CheckBox) view.findViewById(R.id.chk_thursday);
        chk_friday = (CheckBox) view.findViewById(R.id.chk_friday);
        chk_saturday = (CheckBox) view.findViewById(R.id.chk_saturday);
        chk_morning = (CheckBox) view.findViewById(R.id.chk_morning);
        chk_afternoon = (CheckBox) view.findViewById(R.id.chk_afternoon);
        chk_night = (CheckBox) view.findViewById(R.id.chk_night);
        bt_ad = (Button) view.findViewById(R.id.bt_ad);
        spinnerSpecialty = (Spinner) view.findViewById(R.id.spinnerSpecialty);


        //SETA O COMBOBOX
        String[] items = new String[]{getActivity().getString(R.string.dropDownItem1),getActivity().getString(R.string.dropDownItem2),
                getActivity().getString(R.string.dropDownItem3),getActivity().getString(R.string.dropDownItem4),
                getActivity().getString(R.string.dropDownItem5),getActivity().getString(R.string.dropDownItem6)};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);

        spinnerSpecialty.setAdapter(adapter);

        spinnerSpecialty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                if (item != null) {
                    Toast.makeText(getActivity(), item.toString(),
                            Toast.LENGTH_SHORT).show();
                }
                specialty = item.toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                specialty = null;
            }
        });

        //CONTINUA COM O RESTO
        adsReference = databaseReference.child("advertisements");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            uId = firebaseUser.getUid();
        }

        bt_ad.setOnClickListener(this);

        return view;
    }


    //Método 2
    public void createAd() {



        //PEGA A INFORMAÇÃO DAS EDIT TEXT
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        boolean sunday = chk_sunday.isChecked();
        boolean monday = chk_monday.isChecked();
        boolean tuesday = chk_tuesday.isChecked();
        boolean wednesday = chk_wednesday.isChecked();
        boolean thursday = chk_thursday.isChecked();
        boolean friday = chk_friday.isChecked();
        boolean saturday = chk_saturday.isChecked();
        boolean morning = chk_morning.isChecked();
        boolean afternoon = chk_afternoon.isChecked();
        boolean night = chk_night.isChecked();
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String today = formatter.format(date);

        //CRIA UM OBJETO ANUNCIO PARA COLOCAR NO BANCO DE DADOS
        advertisement = new Advertisement(uId, title, description, specialty, sunday,
                monday, tuesday, wednesday, thursday, friday, saturday, morning, afternoon, night, today);

        //VALIDA OS CAMPOS
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description) || specialty == null) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.signupmissing), Toast.LENGTH_LONG).show();
        }else if(!morning && !afternoon && !night){
            Toast.makeText(getActivity(), getActivity().getString(R.string.signupmissing), Toast.LENGTH_LONG).show();
        }else if(!sunday && !monday && !tuesday && !wednesday && !thursday && !friday && !saturday){
            Toast.makeText(getActivity(), getActivity().getString(R.string.signupmissing), Toast.LENGTH_LONG).show();
        }

        //CHAMA O FIREBASE PARA CADASTRAR ANUNCIO
        else {

            progressDialog = ProgressDialog.show(getActivity(), "", getActivity().getString(R.string.pg_create_ad));
            //CHAMA O FIREBASE DATABASE PARA CADASTRAR ANUNCIO
            //CADASTRA NO BANCO
            key = database.getReference("advertisements").push().getKey();
            advertisement.setAdId(key);
            adsReference.child(key).setValue(advertisement);
            progressDialog.dismiss();
            Toast.makeText(getActivity(), getActivity().getString(R.string.create_ad_success), Toast.LENGTH_LONG).show();
        }
    }

    //Método 3
    @Override
    public void onClick(View v) {
            if(v == bt_ad){
                createAd();
            }
    }


}
