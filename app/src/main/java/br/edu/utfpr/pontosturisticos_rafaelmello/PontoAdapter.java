package br.edu.utfpr.pontosturisticos_rafaelmello;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class PontoAdapter extends BaseAdapter {

    private List<PontoTuristico> pontos;
    private Activity activity;

    public PontoAdapter(Activity activity, List<PontoTuristico> pontos) {
        this.activity = activity;
        this.pontos = pontos;
    }

    @Override
    public int getCount() {
        return pontos.size();
    }

    @Override
    public Object getItem(int i) {
        return pontos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return pontos.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = activity .getLayoutInflater().inflate(R.layout.item,viewGroup,false);
        TextView titulo = v.findViewById(R.id.textView4);
        TextView latitude = v.findViewById(R.id.textView5);
        TextView longitude = v.findViewById(R.id.textView6);

        PontoTuristico p = pontos.get(i);
        titulo.setText(p.getTitulo());
        latitude.setText(p.getLatitude());
        longitude.setText(p.getLongitude());

        return v;
    }
}
