package br.edu.utfpr.pontosturisticos_rafaelmello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText titulo;
    private EditText latitude;
    private EditText longitude;
    private PontoTuristicoDAO dao;
    private PontoTuristico ponto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titulo = findViewById(R.id.editTitulo);
        latitude = findViewById(R.id.editLatitude);
        longitude = findViewById(R.id.editLongitude);

        dao = new PontoTuristicoDAO(this);

        Intent it = getIntent();
        if (it.hasExtra("pontoturistico")){
            ponto = (PontoTuristico) it.getSerializableExtra("pontoturistico");
            titulo.setText(ponto.getTitulo().toString());
            latitude.setText(ponto.getLatitude().toString());
            longitude.setText(ponto.getLongitude().toString());
        }
    }

    public void salvar(View view){

        if (ponto == null){
            ponto = new PontoTuristico();
            ponto.setTitulo(titulo.getText().toString());
            ponto.setLatitude(latitude.getText().toString());
            ponto.setLongitude(longitude.getText().toString());
            long id = dao.inserir(ponto);
            Toast.makeText(this, "Ponto turistico inserido com o id: " + id,Toast.LENGTH_SHORT).show();
        } else {
            ponto.setTitulo(titulo.getText().toString());
            ponto.setLatitude(latitude.getText().toString());
            ponto.setLongitude(longitude.getText().toString());
            dao.atualizar(ponto);
            Toast.makeText(this, "O ponto foi atualizado", Toast.LENGTH_SHORT);
        }

    }


}
