package com.fatecerss.tcc.apprendendo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fatecerss.tcc.apprendendo.R;
import com.fatecerss.tcc.apprendendo.model.Advertisement;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.resource;

/**
 * Created by Sandro on 08/05/2018.
 */

public class AdArrayAdapter extends ArrayAdapter<Advertisement> {

    private ArrayList<Advertisement> ads;
    private Context context;
    TextView textViewTitle;
    TextView textViewSpecialty;
    TextView textViewDate;


    public AdArrayAdapter(Context c, ArrayList<Advertisement> objects){
        super(c, 0, objects);
        this.ads = objects;
        this.context = c;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Advertisement ad = getItem(position);

        View view = null;

        if (ads != null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.fragment_ad_list_item, parent, false);

            textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
            textViewSpecialty = (TextView) view.findViewById(R.id.textViewSpecialty);
            textViewDate = (TextView) view.findViewById(R.id.textViewDate);

            Advertisement advertisement = ads.get(position);
            String specialtyText = textViewSpecialty.getText().toString()+" "+advertisement.getSpecialty();
            String dateText = textViewDate.getText().toString().trim()+" "+advertisement.getRegistrationDate();

            textViewTitle.setText(advertisement.getTitle());
            textViewSpecialty.setText(specialtyText);
            textViewDate.setText(dateText);


        }


        return view;
        /*if (convertView == null){

            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.fragment_ad_list_item, parent, false);

            viewHolder.textViewTitle = (TextView) convertView.findViewById(R.id.textViewTitle);
            viewHolder.textViewSpecialty = (TextView) convertView.findViewById(R.id.textViewSpecialty);
            viewHolder.textViewDate = (TextView) convertView.findViewById(R.id.textViewDate);

        }

        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }


        return convertView;*/

    }
}
