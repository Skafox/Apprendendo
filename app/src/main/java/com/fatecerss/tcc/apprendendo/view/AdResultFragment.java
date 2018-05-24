package com.fatecerss.tcc.apprendendo.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fatecerss.tcc.apprendendo.R;
import com.fatecerss.tcc.apprendendo.model.Advertisement;
import com.fatecerss.tcc.apprendendo.model.Interest;
import com.fatecerss.tcc.apprendendo.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.R.attr.value;
import static com.fatecerss.tcc.apprendendo.R.id.bt_ad;
import static com.fatecerss.tcc.apprendendo.R.id.editTextTitle;
import static com.fatecerss.tcc.apprendendo.R.id.imageViewSendInterest;
import static com.fatecerss.tcc.apprendendo.R.id.spinnerSpecialty;

/**
 * Created by Sandro on 21/05/2018.
 */

public class AdResultFragment extends Fragment implements View.OnClickListener{

    private TextView textViewTitle;
    private EditText editTextDescription;
    private TextView textViewSpecialtySelected;
    private TextView textViewAdOwnerLink;
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
    private ImageButton imageViewSendInterest;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String uId;
    private String adId;
    private String adOwnerId;
    private String key;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private Query adsReference = databaseReference.child("advertisements");
    private Query usersReference = databaseReference.child("users");
    private Advertisement advertisement;
    private User adOwner;
    private User currentUser;
    private ProgressDialog progressDialog;
    private AlertDialog.Builder warning;
    private ValueEventListener adValueEventListener;
    private ValueEventListener ownerValueEventListener;
    private ValueEventListener userValueEventListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ad_search_result, container, false);

        adId = getArguments().getString("adId");

        //INSTANCIA AS VARIAVEIS
        textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
        editTextDescription = (EditText) view.findViewById(R.id.editTextDescription);
        textViewSpecialtySelected = (TextView) view.findViewById(R.id.textViewSpecialtySelected);
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
        imageViewSendInterest = (ImageButton) view.findViewById(R.id.imageViewSendInterest);
        textViewAdOwnerLink = (TextView) view.findViewById(R.id.textViewAdOwnerLink);
        imageViewSendInterest = (ImageButton) view.findViewById(R.id.imageViewSendInterest);

        adsReference = databaseReference.child("advertisements");
        usersReference = databaseReference.child("users");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            uId = firebaseUser.getUid();
        }

        readAdvertisementFromDatabase(adId);
        readCurrentUserFromDatabase(uId);

        return view;

    }


    @Override
    public void onClick(View v) {
        if(v == imageViewSendInterest){
            warning = new AlertDialog.Builder(getActivity());
            warning.setMessage(getString(R.string.sendInterestMessage));
            warning.setCancelable(true);
            warning.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    createInterest();
                    FragmentManager fragmentManager = getFragmentManager();
                    Fragment fragment = null;
                    fragmentManager.popBackStack();
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

    public void createInterest(){
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String today = formatter.format(date);
        Interest interest = new Interest(adOwnerId,uId,currentUser.getName(),adId,advertisement.getTitle(),advertisement.getSpecialty(),today);
        key = database.getReference("interests").push().getKey();
        interest.setInterestId(key);
        databaseReference.child("interests").child(key).setValue(interest);
    }

    public void readAdOwnerFromDatabase(String adOwnerId){
        usersReference = databaseReference.child("users").child(adOwnerId);
        ownerValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adOwner = dataSnapshot.getValue(User.class);
                textViewAdOwnerLink.setText(adOwner.getName());
                textViewAdOwnerLink.setPaintFlags(textViewAdOwnerLink.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        usersReference.addValueEventListener(ownerValueEventListener);
    }

    public void readAdvertisementFromDatabase(String adId){
        //RECEBE O ID DO ANUNCIO QUE FOI CLICADO COMO PARÂMETRO
        adsReference = databaseReference.child("advertisements").child(adId);
        adValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    Advertisement advertisement = dataSnapshot.getValue(Advertisement.class);
                    textViewTitle.setText(advertisement.getTitle());
                    editTextDescription.setText(advertisement.getDescription());
                    editTextDescription.setEnabled(false);
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
                    chk_sunday.setEnabled(false);
                    chk_monday.setEnabled(false);
                    chk_tuesday.setEnabled(false);
                    chk_wednesday.setEnabled(false);
                    chk_thursday.setEnabled(false);
                    chk_friday.setEnabled(false);
                    chk_saturday.setEnabled(false);
                    chk_morning.setEnabled(false);
                    chk_afternoon.setEnabled(false);
                    chk_night.setEnabled(false);

                    String specialtyResult = advertisement.getSpecialty();
                    String specialtyExpected1 = getActivity().getString(R.string.dropDownItem1);
                    String specialtyExpected2 = getActivity().getString(R.string.dropDownItem2);
                    String specialtyExpected3 = getActivity().getString(R.string.dropDownItem3);
                    String specialtyExpected4 = getActivity().getString(R.string.dropDownItem4);
                    String specialtyExpected5 = getActivity().getString(R.string.dropDownItem5);
                    String specialtyExpected6 = getActivity().getString(R.string.dropDownItem6);
                    if (specialtyResult.equals(specialtyExpected1)) {
                        textViewSpecialtySelected.setText(getActivity().getString(R.string.dropDownItem1));
                    } else if (specialtyResult.equals(specialtyExpected2)) {
                        textViewSpecialtySelected.setText(getActivity().getString(R.string.dropDownItem2));
                    } else if (specialtyResult.equals(specialtyExpected3)) {
                        textViewSpecialtySelected.setText(getActivity().getString(R.string.dropDownItem3));
                    } else if (specialtyResult.equals(specialtyExpected4)) {
                        textViewSpecialtySelected.setText(getActivity().getString(R.string.dropDownItem4));
                    } else if (specialtyResult.equals(specialtyExpected5)) {
                        textViewSpecialtySelected.setText(getActivity().getString(R.string.dropDownItem5));
                    } else if (specialtyResult.equals(specialtyExpected6)) {
                        textViewSpecialtySelected.setText(getActivity().getString(R.string.dropDownItem6));
                    } else if (specialtyResult.equals(specialtyExpected6)) {
                        textViewSpecialtySelected.setText(getActivity().getString(R.string.dropDownItem7));
                    }
                    adOwnerId = advertisement.getuId();
                    readAdOwnerFromDatabase(adOwnerId);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        adsReference.addValueEventListener(adValueEventListener);
    }

    public void readCurrentUserFromDatabase(String uId){
        usersReference = databaseReference.child("users").child(uId);
        userValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        usersReference.addValueEventListener(userValueEventListener);
    }

}
