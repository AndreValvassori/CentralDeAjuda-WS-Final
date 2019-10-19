package Faczz.Drevelopment.centraldeajuda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Faczz.Drevelopment.centraldeajuda.Model.Aviso;

public class AvisoListAdapter extends ArrayAdapter<Aviso> {
    private Context mContext;
    int mResource;

    public AvisoListAdapter(@NonNull Context context, int resource, @NonNull List<Aviso> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        String nome = getItem(position).getNome();
        String descricao = getItem(position).getDetalhes();
        Date horario = getItem(position).getHorario();
        Double longitude = getItem(position).getLongitude();
        Double latitude = getItem(position).getLatitude();

        Aviso aviso = new Aviso();
        aviso.setNome(nome);
        aviso.setDetalhes(descricao);
        aviso.setHorario(horario);
        aviso.setLatitude(latitude);
        aviso.setLongitude(longitude);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent,false);

        TextView tvNome = (TextView) convertView.findViewById(R.id.listavisos_nome);
        TextView tvDetalhes = (TextView) convertView.findViewById(R.id.listavisos_descricao);
        TextView tvHorario = (TextView) convertView.findViewById(R.id.listavisos_horario);
        TextView tvLatitude = (TextView) convertView.findViewById(R.id.listavisos_latitude);
        TextView tvLongitude = (TextView) convertView.findViewById(R.id.listavisos_longitude);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        tvNome.setText("Nome: "+aviso.getNome());
        tvDetalhes.setText("Descrição:: "+aviso.getDetalhes());
        tvHorario.setText("Horário: "+ simpleDateFormat.format(aviso.getHorario()));
        tvLatitude.setText("Lati: "+aviso.getLatitude());
        tvLongitude.setText("Long: "+aviso.getLongitude());

        return convertView;
    }
}
