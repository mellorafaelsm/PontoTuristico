package br.edu.utfpr.pontosturisticos_rafaelmello;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private PontoTuristicoDAO dao;
    private List<PontoTuristico> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        dao = new PontoTuristicoDAO(this);
        list = dao.ObterTodos();

        PontoAdapter adaptador = new PontoAdapter(this,list);
        for (int i = 0; i < list.size(); i++) {
            PontoTuristico p = new PontoTuristico();
            p = (PontoTuristico) adaptador.getItem(i);
            System.out.println(p.getTitulo() + " lat " + p.getLatitude() + " long " + p.getLongitude());

            LatLng ponto = new LatLng(Integer.parseInt(p.getLatitude()), Integer.parseInt(p.getLongitude()));
            mMap.addMarker(new MarkerOptions().position(ponto).title(p.getTitulo()));
        }

//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Teste em sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
