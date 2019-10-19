package Faczz.Drevelopment.centraldeajuda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Faczz.Drevelopment.centraldeajuda.Model.Aviso;

public class AvisosGeralActivity extends AppCompatActivity implements OnMapReadyCallback {
    private FirebaseAnalytics mFirebaseAnalytics;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ListView listAtivos;

    private MapView mapView;
    private GoogleMap gmap;
    private static final String MAP_VIEW_BUNDLE_KEY = "AIzaSyDxB4lxbtc6UPjX4yeSBjAfSvcDA_kNwPE";

    List<Aviso> listAvisos =  new ArrayList<Aviso>();
    List<Marker> markerlist;
    ArrayAdapter<Aviso> arrayAvisoAdapter;

    AvisoListAdapter avisoadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avisos_geral);

        listAtivos = (ListView) findViewById(R.id.list_Alertas);
        inicializarFirebase();

        fetchEvents();

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mapView = findViewById(R.id.mapAlertas);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

    }

    public void inicializarFirebase(){
        //Database
        if (firebaseDatabase == null)
        {
            FirebaseApp.initializeApp(AvisosGeralActivity.this);
        }
        firebaseDatabase = FirebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    private void fetchEvents() {
        databaseReference.child("Avisos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listAvisos.clear();
                try {
                    gmap.clear();
                }
                catch (Exception e){

                }

                for(DataSnapshot objsnap:dataSnapshot.getChildren()){
                    Aviso avs = objsnap.getValue(Aviso.class);
                    listAvisos.add(avs);




                }
                //arrayAvisoAdapter = new ArrayAdapter<Aviso>(AvisosGeralActivity.this, android.R.layout.simple_list_item_1, listAvisos);
                //listAtivos.setAdapter(arrayAvisoAdapter);
                for(Aviso avs :listAvisos){
                    try {
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(new LatLng(avs.getLatitude(),avs.getLongitude()));
                        gmap.addMarker(markerOptions);
                    }
                    catch (Exception e){

                    }
                }

                avisoadapter = new AvisoListAdapter(AvisosGeralActivity.this,R.layout.adapter_listavisos,listAvisos);
                listAtivos.setAdapter(avisoadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK)){
            DatabaseDM.logOut();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_geral,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_perfil){
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Click on Perfil");
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "text");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

            Intent i =  new Intent(getApplicationContext(),PerfilActivity.class);
            startActivity(i);
        } else
        if(id == R.id.menu_alertas){
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Click on Alerta");
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "text");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        } else
        if(id == R.id.menu_novoalerta){
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Click on Novo Alerta");
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "text");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
            Intent i =  new Intent(getApplicationContext(),MeusAvisosActivity.class);
            startActivity(i);
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(13);
        LatLng ny = new LatLng(-25.4954142,-49.2307036);
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));

        try {
            gmap.clear();
        }
        catch (Exception e){

        }
        for(Aviso avs :listAvisos){
            try {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(avs.getLatitude(),avs.getLongitude()));
                gmap.addMarker(markerOptions);
            }
            catch (Exception e){

            }
        }
    }
}
