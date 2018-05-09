package com.fatecerss.tcc.apprendendo.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

import static com.fatecerss.tcc.apprendendo.R.id.bt_ad;
import static com.fatecerss.tcc.apprendendo.R.id.tf_bio;
import static com.fatecerss.tcc.apprendendo.R.id.tf_email;
import static com.fatecerss.tcc.apprendendo.R.id.tf_name;
import static com.fatecerss.tcc.apprendendo.R.id.tf_password;
import static com.fatecerss.tcc.apprendendo.R.id.tf_phone;
import static com.fatecerss.tcc.apprendendo.R.id.tf_username;

/**
 * Created by Sandro on 08/05/2018.
 */

public class AdEditFragment extends Fragment implements View.OnClickListener{

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
    private Button bt_updateAd;
    private Button bt_deleteAd;
    private Spinner spinnerSpecialty;
    private String specialty;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String uId;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private String adId;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private Query adsReference = databaseReference.child("advertisements");
    private Advertisement advertisement;
    private ProgressDialog progressDialog;
    private AlertDialog.Builder warning;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_ad, container, false);

        //INSTANCIA AS VARIAVEIS

        adId = getArguments().getString("adId");

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
        bt_updateAd = (Button) view.findViewById(R.id.bt_updateAd);
        bt_deleteAd = (Button) view.findViewById(R.id.bt_deleteAd);
        spinnerSpecialty = (Spinner) view.findViewById(R.id.spinnerSpecialty);

        adsReference = databaseReference.child("advertisements");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            uId = firebaseUser.getUid();
        }

        readFromDatabase(adId);



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
        bt_updateAd.setOnClickListener(this);
        bt_deleteAd.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        if(v == bt_updateAd){
            updateAd();
        }
        if(v == bt_deleteAd){
            deleteAd();
        }
    }


    //Método 2
    public void updateAd() {

        //CRIA UMA CAIXA DE DIALOGO COM BOTAO DE SIM E DE CANCELAR
        warning = new AlertDialog.Builder(getActivity());
        final String updatedSuccess = this.getString(R.string.updateSuccess);

        warning.setTitle(R.string.updateWarning);
        warning.setMessage(getString(R.string.updateMessage));
        warning.setIcon(android.R.drawable.ic_dialog_alert);
        warning.setCancelable(true);
        warning.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                //CASO SEJA APERTADO A OPÇÃO SIM, CRIA UM USUARIO NOVO PARA ATUALIZAR, COM OS CAMPOS DA TELA
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
                else{
                    progressDialog = ProgressDialog.show(getActivity(), "", getActivity().getString(R.string.pg_create_ad));
                    //CHAMA O FIREBASE DATABASE PARA CADASTRAR ANUNCIO
                    //ATUALIZA NO BANCO
                    advertisement.setAdId(adId);
                    databaseReference.child("advertisements").child(adId).setValue(advertisement);
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), getActivity().getString(R.string.update_ad_success), Toast.LENGTH_LONG).show();
                }
            }});
        warning.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }});
        warning.show();

    }

    public void deleteAd(){

        //CRIA UMA CAIXA DE DIALOGO COM BOTAO DE SIM E DE CANCELAR
        warning = new AlertDialog.Builder(getActivity());
        final String updatedSuccess = this.getString(R.string.updateSuccess);

        warning.setTitle(R.string.updateWarning);
        warning.setMessage(getString(R.string.deleteMessage));
        warning.setIcon(android.R.drawable.ic_dialog_alert);
        warning.setCancelable(true);
        warning.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                    databaseReference.child("advertisements").child(adId).removeValue();

                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = new AdListFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.layoutContentHome, fragment);
                fragmentTransaction.commitAllowingStateLoss();
                //Volta para a lista
            }});
        warning.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }});
        warning.show();

    }

    public void readFromDatabase(String adId){

        //RECEBE O ID DO ANUNCIO QUE FOI CLICADO COMO PARÂMETRO
        adsReference = databaseReference.child("advertisements").child(adId);
        adsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot != null) {

                    Advertisement advertisement = dataSnapshot.getValue(Advertisement.class);
                    editTextTitle.setText(advertisement.getTitle());
                    editTextDescription.setText(advertisement.getDescription());
                    chk_sunday.setChecked(advertisement.isSunday());
                    chk_monday.setChecked(advertisement.isMonday());
                    chk_tuesday.setChecked(advertisement.isTuesday());
                    chk_wednesday.setChecked(advertisement.isWednesday());
                    chk_thursday.setChecked(advertisement.isThursday());
                    chk_friday.setChecked(advertisement.isFriday());
                    chk_saturday.setChecked(advertisement.isSaturday());
                    chk_morning.setChecked(advertisement.isMorning());
                    chk_afternoon.setChecked(advertisement.isAfternoon());
                    chk_night.setChecked(advertisement.isNight());

                    String specialtyResult = advertisement.getSpecialty();
                    String specialtyExpected1 = getActivity().getString(R.string.dropDownItem1);
                    String specialtyExpected2 = getActivity().getString(R.string.dropDownItem2);
                    String specialtyExpected3 = getActivity().getString(R.string.dropDownItem3);
                    String specialtyExpected4 = getActivity().getString(R.string.dropDownItem4);
                    String specialtyExpected5 = getActivity().getString(R.string.dropDownItem5);
                    String specialtyExpected6 = getActivity().getString(R.string.dropDownItem6);
                    if (specialtyResult.equals(specialtyExpected1)) {
                        spinnerSpecialty.setSelection(0);
                    } else if (specialtyResult.equals(specialtyExpected2)) {
                        spinnerSpecialty.setSelection(1);
                    } else if (specialtyResult.equals(specialtyExpected3)) {
                        spinnerSpecialty.setSelection(2);
                    } else if (specialtyResult.equals(specialtyExpected4)) {
                        spinnerSpecialty.setSelection(3);
                    } else if (specialtyResult.equals(specialtyExpected5)) {
                        spinnerSpecialty.setSelection(4);
                    } else if (specialtyResult.equals(specialtyExpected6)) {
                        spinnerSpecialty.setSelection(5);
                    }
                } else {
                    FragmentManager fragmentManager = getFragmentManager();
                    Fragment fragment = new AdListFragment();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.layoutContentHome, fragment);
                    fragmentTransaction.commitAllowingStateLoss();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
