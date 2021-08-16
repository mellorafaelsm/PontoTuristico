package br.edu.utfpr.pontosturisticos_rafaelmello.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import br.edu.utfpr.pontosturisticos_rafaelmello.PontoTuristico;
import br.edu.utfpr.pontosturisticos_rafaelmello.PontoTuristicoDAO;
import br.edu.utfpr.pontosturisticos_rafaelmello.R;

public class MainActivity extends AppCompatActivity {

    private ImageView ivFoto;
    private EditText titulo;
    private EditText latitude;
    private EditText longitude;
    private PontoTuristicoDAO dao;
    private PontoTuristico ponto;

    Bitmap foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titulo = findViewById(R.id.editTitulo);
        latitude = findViewById(R.id.editLatitude);
        longitude = findViewById(R.id.editLongitude);
        ivFoto = findViewById(R.id.ivFoto);

        dao = new PontoTuristicoDAO(this);

        Intent it = getIntent();
        if (it.hasExtra("pontoturistico")) {
            ponto = (PontoTuristico) it.getSerializableExtra("pontoturistico");
            if (ponto != null) {
                titulo.setText(ponto.getTitulo());
                latitude.setText(ponto.getLatitude());
                longitude.setText(ponto.getLongitude());
            }
        }
    }

    private void adicionaValoresPonto() {
        ponto.setTitulo(titulo.getText().toString());
        ponto.setLatitude(latitude.getText().toString());
        ponto.setLongitude(longitude.getText().toString());
        if(foto != null) {
            ponto.setFoto(getBitmapAsByteArray(foto));
        }
    }

    public void salvar(View view) {
        if (ponto == null) {
            ponto = new PontoTuristico();
            adicionaValoresPonto();
            long id = dao.inserir(ponto);
            Toast.makeText(this, "Ponto turístico inserido com id: " + id, Toast.LENGTH_SHORT).show();
        } else {
            adicionaValoresPonto();
            dao.atualizar(ponto);
            Toast.makeText(this, "O ponto foi atualizado", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    public void tirarFoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            assert data != null;
            foto = (Bitmap) data.getExtras().get("data");
            ivFoto.setImageBitmap(foto);
        }
    }
}
