package Faczz.Drevelopment.centraldeajuda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import Faczz.Drevelopment.centraldeajuda.Model.Aviso;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class MeusAvisosActivity extends AppCompatActivity {


    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    private FusedLocationProviderClient fusedLocationClient;

    EditText nomeAviso, descricaoaviso, horarioAviso;
    Button btnEnviarAviso;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_avisos);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (firebaseDatabase == null)
            inicializarFirebase();

        nomeAviso = (EditText) findViewById(R.id.edtTituloAviso);
        descricaoaviso = (EditText) findViewById(R.id.edtDescricaoAviso);
        horarioAviso = (EditText) findViewById(R.id.edtHorarioAviso);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        horarioAviso.setText( simpleDateFormat.format(now) );

        btnEnviarAviso = (Button) findViewById(R.id.btnEnviarAviso);

        btnEnviarAviso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Aviso novoAviso;
                novoAviso = new Aviso();
                novoAviso.setUid(UUID.randomUUID().toString());
                novoAviso.setNome(nomeAviso.getText().toString().trim());
                novoAviso.setDetalhes(descricaoaviso.getText().toString().trim());

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Calendar calendar = Calendar.getInstance();
                Date now = calendar.getTime();
                novoAviso.setHorario( now );

                /*MyLocation.LocationResult locationResult = new MyLocation.LocationResult(){
                    @Override
                    public void gotLocation(Location location){
                        novoAviso.setLatitude( location.getLatitude());
                        novoAviso.setLongitude( location.getLongitude());
                    }
                };
                MyLocation myLocation = new MyLocation();
                myLocation.getLocation(MeusAvisosActivity.this, locationResult);
*/

                fetchgpsandaddaviso(novoAviso);
            }
        });

    }


    private void fetchgpsandaddaviso(final Aviso aviso) {

// Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(MeusAvisosActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MeusAvisosActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                new AlertDialog.Builder(this)
                        .setTitle("Permissão para localização necessária!")
                       .setMessage("Você precisa permitir o acesso a localização para pode enviar avisos!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MeusAvisosActivity.this,
                                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
                            }
                        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();


            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(MeusAvisosActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                aviso.setLongitude(location.getLongitude());
                                aviso.setLatitude(location.getLatitude());
                                InsertAviso(aviso);
                                finish();
                            }
                        }
                    });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                btnEnviarAviso.callOnClick();
            }
            else
            {

            }
        }
    }

    public void inicializarFirebase(){
        //Database
        //FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(false);
        databaseReference = firebaseDatabase.getReference();


    }
    boolean InsertAviso(Aviso aviso){
        if (databaseReference == null)
            inicializarFirebase();

        databaseReference.child("Avisos").child(aviso.getUid()).setValue(aviso);

        Toast.makeText(this, "Aviso enviado com sucesso!", Toast.LENGTH_SHORT).show();
        return true;
    }
}
