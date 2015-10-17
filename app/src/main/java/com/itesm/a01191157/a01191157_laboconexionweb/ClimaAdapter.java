package com.itesm.a01191157.a01191157_laboconexionweb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by alfredo_altamirano on 10/16/15.
 */
public class ClimaAdapter extends ArrayAdapter<Clima> {

    private Context context;
    private int resource;
    private List<Clima> climas;

    public ClimaAdapter(Context context, int resource, List<Clima> climas) {
        super(context, resource, climas);

        this.context = context;
        this.resource = resource;
        this.climas = climas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.renglon, parent, false);

        Clima clima = climas.get(position);

        ((TextView)row.findViewById(R.id.tempActual)).setText(clima.getTempActual() + "");
        ((TextView)row.findViewById(R.id.tempMaxima)).setText(clima.getTempMaxima() + "");
        ((TextView)row.findViewById(R.id.tempMinima)).setText(clima.getTempMinima() + "");

        return row;
    }

}
