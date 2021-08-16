package br.edu.utfpr.pontosturisticos_rafaelmello.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Arrays;
import java.util.List;

import br.edu.utfpr.pontosturisticos_rafaelmello.PontoAdapter;
import br.edu.utfpr.pontosturisticos_rafaelmello.PontoTuristico;
import br.edu.utfpr.pontosturisticos_rafaelmello.PontoTuristicoDAO;
import br.edu.utfpr.pontosturisticos_rafaelmello.R;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null)
            mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        if (!sp.getString("map", "1").equals("false")) {
            googleMap.setMapType(Integer.parseInt(sp.getString("map", "1"))); //config do mapa
        }
        googleMap.setMinZoomPreference(Integer.parseInt(sp.getString("zoom", "1")));

        PontoTuristicoDAO dao = new PontoTuristicoDAO(this);
        List<PontoTuristico> list = dao.ObterTodos();

        PontoAdapter adaptador = new PontoAdapter(this, list);
        for (int i = 0; i < list.size(); i++) {
            PontoTuristico p = (PontoTuristico) adaptador.getItem(i);
            System.out.println(p.getTitulo() + " lat " + p.getLatitude() + " long " + p.getLongitude());
            LatLng ponto = new LatLng(Integer.parseInt(p.getLatitude()), Integer.parseInt(p.getLongitude()));

            Bitmap foto = null;
            if(p.getFoto() != null) {
                foto = BitmapFactory.decodeByteArray(p.getFoto(), 0, p.getFoto().length);
            }

            if(foto != null) {
                googleMap.addMarker(new MarkerOptions().position(ponto).title(p.getTitulo()).icon(BitmapDescriptorFactory.fromBitmap(foto)));
            } else {
                googleMap.addMarker(new MarkerOptions().position(ponto).title(p.getTitulo()));
            }
        }
    }
}
