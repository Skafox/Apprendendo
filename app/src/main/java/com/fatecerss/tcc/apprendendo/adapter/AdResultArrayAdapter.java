package com.fatecerss.tcc.apprendendo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fatecerss.tcc.apprendendo.R;
import com.fatecerss.tcc.apprendendo.model.Advertisement;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Sandro on 20/05/2018.
 */

public class AdResultArrayAdapter extends ArrayAdapter<Advertisement> {

private ArrayList<Advertisement> ads;
private Context context;

        TextView textViewTitle;
        TextView textViewSpecialty;
        TextView textViewDate;
        TextView textViewType;


public AdResultArrayAdapter(Context c,ArrayList<Advertisement> objects){
        super(c,0,objects);
        this.ads=objects;
        this.context=c;
        }

@Override
public View getView(int position, View convertView, ViewGroup parent){

        Advertisement ad=getItem(position);

        View view=null;

        if(ads!=null){

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.fragment_ad_search_item,parent,false);

            textViewTitle=(TextView)view.findViewById(R.id.textViewTitle);
            textViewSpecialty=(TextView)view.findViewById(R.id.textViewSpecialty);
            textViewDate=(TextView)view.findViewById(R.id.textViewDate);
            textViewType=(TextView)view.findViewById(R.id.textViewType);

            Advertisement advertisement=ads.get(position);
            String type = null;
            if (advertisement.getType().equals("t")){
                type = textViewType.getText().toString().trim()+" Teacher";
            }
            else{
                type = textViewType.getText().toString().trim()+" Student";
            }
            String specialtyText=textViewSpecialty.getText().toString()+" "+advertisement.getSpecialty();
            String dateText=textViewDate.getText().toString().trim()+" "+advertisement.getRegistrationDate();

            textViewTitle.setText(advertisement.getTitle());
            textViewSpecialty.setText(specialtyText);
            textViewDate.setText(dateText);
            textViewType.setText(type);

            }

        return view;

        }
}