package com.fatecerss.tcc.apprendendo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fatecerss.tcc.apprendendo.R;
import com.fatecerss.tcc.apprendendo.model.Advertisement;
import com.fatecerss.tcc.apprendendo.model.Interest;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Sandro on 01/06/2018.
 */

public class InterestArrayAdapter extends ArrayAdapter<Interest> {

    private ArrayList<Interest> ints;
    private Context context;
    TextView textViewTitle;
    TextView textViewSpecialty;
    TextView textViewDate;
    TextView textViewName;
    TextView textViewType;


    public InterestArrayAdapter(Context c, ArrayList<Interest> objects){
        super(c, 0, objects);
        this.ints = objects;
        this.context = c;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Interest in = getItem(position);

        View view = null;

        if (ints != null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.fragment_interest_list_item, parent, false);

            textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
            textViewSpecialty = (TextView) view.findViewById(R.id.textViewSpecialty);
            textViewDate = (TextView) view.findViewById(R.id.textViewDate);
            textViewName = (TextView) view.findViewById(R.id.textViewName);
            //textViewType = (TextView) view.findViewById(R.id.textViewType);

            Interest interest = ints.get(position);
            String specialtyText = textViewSpecialty.getText().toString()+" "+interest.getAdSpecialty();
            String dateText = textViewDate.getText().toString().trim()+" "+interest.getInterestDate();
            String userText = textViewName.getText().toString().trim()+" "+interest.getInterestedName();

            textViewTitle.setText(interest.getAdTitle());
            textViewSpecialty.setText(specialtyText);
            textViewDate.setText(dateText);
            textViewName.setText(userText);


        }


        return view;

    }
}