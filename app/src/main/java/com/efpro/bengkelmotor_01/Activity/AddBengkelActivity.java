package com.efpro.bengkelmotor_01.Activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.efpro.bengkelmotor_01.Bengkel;
import com.efpro.bengkelmotor_01.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddBengkelActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AddBengkelActivity";
    int PLACE_PICKER_REQUEST = 1;
    Button btnSetLokasi, btnAddBengkel;
    EditText edtNamaBengkel, edtAlamat, edtTelepon;
    EditText edtStartH1, edtStartH2, edtStartH3, edtStartH4, edtStartH5, edtStartH6, edtStartH7;
    EditText edtEndH1, edtEndH2, edtEndH3, edtEndH4, edtEndH5, edtEndH6, edtEndH7;
    CheckBox cbH1, cbH2, cbH3, cbH4, cbH5, cbH6, cbH7;
    TextView tSenin, tSelasa, tRabu, tKamis, tJumat, tSabtu, tMinggu;
    TextView txtLat, txtLng;
    String nama, alamat, telepon;
    double latitude, longitude;
    HashMap<String, String> jambuka = new HashMap<>();
    boolean flagSetTime = false;

    private DatabaseReference mBengkelRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bengkel);

        txtLat          = (TextView) findViewById(R.id.txtLat);
        txtLng          = (TextView) findViewById(R.id.txtLng);
        tSenin          = (TextView) findViewById(R.id.tSenin);
        tSelasa         = (TextView) findViewById(R.id.tSelasa);
        tRabu           = (TextView) findViewById(R.id.tRabu);
        tKamis          = (TextView) findViewById(R.id.tKamis);
        tJumat          = (TextView) findViewById(R.id.tJumat);
        tSabtu          = (TextView) findViewById(R.id.tSabtu);
        tMinggu         = (TextView) findViewById(R.id.tMinggu);

        cbH1            = (CheckBox) findViewById(R.id.cbH1);
        cbH2            = (CheckBox) findViewById(R.id.cbH2);
        cbH3            = (CheckBox) findViewById(R.id.cbH3);
        cbH4            = (CheckBox) findViewById(R.id.cbH4);
        cbH5            = (CheckBox) findViewById(R.id.cbH5);
        cbH6            = (CheckBox) findViewById(R.id.cbH6);
        cbH7            = (CheckBox) findViewById(R.id.cbH7);

        edtNamaBengkel  = (EditText) findViewById(R.id.edtNamaBengkel);
        edtAlamat       = (EditText) findViewById(R.id.edtAlamat);
        edtTelepon      = (EditText) findViewById(R.id.edtTelepon);
        edtStartH1      = (EditText) findViewById(R.id.edtStartH1);
        edtStartH2      = (EditText) findViewById(R.id.edtStartH2);
        edtStartH3      = (EditText) findViewById(R.id.edtStartH3);
        edtStartH4      = (EditText) findViewById(R.id.edtStartH4);
        edtStartH5      = (EditText) findViewById(R.id.edtStartH5);
        edtStartH6      = (EditText) findViewById(R.id.edtStartH6);
        edtStartH7      = (EditText) findViewById(R.id.edtStartH7);
        edtEndH1        = (EditText) findViewById(R.id.edtEndH1);
        edtEndH2        = (EditText) findViewById(R.id.edtEndH2);
        edtEndH3        = (EditText) findViewById(R.id.edtEndH3);
        edtEndH4        = (EditText) findViewById(R.id.edtEndH4);
        edtEndH5        = (EditText) findViewById(R.id.edtEndH5);
        edtEndH6        = (EditText) findViewById(R.id.edtEndH6);
        edtEndH7        = (EditText) findViewById(R.id.edtEndH7);

        edtStartH1.setOnClickListener(this);
        edtStartH2.setOnClickListener(this);
        edtStartH3.setOnClickListener(this);
        edtStartH4.setOnClickListener(this);
        edtStartH5.setOnClickListener(this);
        edtStartH6.setOnClickListener(this);
        edtStartH7.setOnClickListener(this);
        edtEndH1.setOnClickListener(this);
        edtEndH2.setOnClickListener(this);
        edtEndH3.setOnClickListener(this);
        edtEndH4.setOnClickListener(this);
        edtEndH5.setOnClickListener(this);
        edtEndH6.setOnClickListener(this);
        edtEndH7.setOnClickListener(this);

        btnSetLokasi    = (Button) findViewById(R.id.btnSetLokasi);
        btnAddBengkel   = (Button) findViewById(R.id.btnAddBengkel);
        btnSetLokasi.setOnClickListener(this);
        btnAddBengkel.setOnClickListener(this);

        mBengkelRef = FirebaseDatabase.getInstance().getReference("ListBengkel");



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSetLokasi:
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    Intent intent = builder.build(this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            break;
            case R.id.btnAddBengkel:
                nama         = String.valueOf(edtNamaBengkel.getText());
                alamat       = String.valueOf(edtAlamat.getText());
                telepon      = String.valueOf(edtTelepon.getText());
                //Toast.makeText(this, "Show" + latitude + " - " + longitude , Toast.LENGTH_SHORT).show();
                jambuka.put("Senin",    edtStartH1.getText().toString() + " - " + edtEndH1.getText().toString());
                jambuka.put("Selasa",   edtStartH2.getText().toString() + " - " + edtEndH2.getText().toString());
                jambuka.put("Rabu",     edtStartH3.getText().toString() + " - " + edtEndH3.getText().toString());
                jambuka.put("Kamis",    edtStartH4.getText().toString() + " - " + edtEndH4.getText().toString());
                jambuka.put("Jumat",    edtStartH5.getText().toString() + " - " + edtEndH5.getText().toString());
                jambuka.put("Sabtu",    edtStartH6.getText().toString() + " - " + edtEndH6.getText().toString());
                jambuka.put("Minggu",   edtStartH7.getText().toString() + " - " + edtEndH7.getText().toString());

                for (Map.Entry<String, String> entry : jambuka.entrySet()) {
                    String hari = entry.getKey();
                    String jam = entry.getValue();
                    if (jam.equals(" - ")) {
                        jambuka.put(hari, "Tutup");
                    }
                }
                Log.e(TAG, String.valueOf(jambuka));

                addBengkel(nama, alamat, telepon, latitude, longitude, jambuka);
            break;
            case R.id.edtStartH1:
                flagSetTime = true;
                setTime(edtStartH1);
            break;
            case R.id.edtStartH2:
                flagSetTime = true;
                setTime(edtStartH2);
            break;
            case R.id.edtStartH3:
                flagSetTime = true;
                setTime(edtStartH3);
            break;
            case R.id.edtStartH4:
                flagSetTime = true;
                setTime(edtStartH4);
            break;
            case R.id.edtStartH5:
                flagSetTime = true;
                setTime(edtStartH5);
            break;
            case R.id.edtStartH6:
                flagSetTime = true;
                setTime(edtStartH6);
            break;
            case R.id.edtStartH7:
                flagSetTime = true;
                setTime(edtStartH7);
            break;

            case R.id.edtEndH1:
                flagSetTime = false;
                setTime(edtEndH1);
            break;
            case R.id.edtEndH2:
                flagSetTime = false;
                setTime(edtEndH2);
            break;
            case R.id.edtEndH3:
                flagSetTime = false;
                setTime(edtEndH3);
            break;
            case R.id.edtEndH4:
                flagSetTime = false;
                setTime(edtEndH4);
            break;
            case R.id.edtEndH5:
                flagSetTime = false;
                setTime(edtEndH5);
            break;
            case R.id.edtEndH6:
                flagSetTime = false;
                setTime(edtEndH6);
            break;
            case R.id.edtEndH7:
                flagSetTime = false;
                setTime(edtEndH7);
            break;
        }
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.cbH1:
                if (checked){
                    edtStartH1.setEnabled(true);
                    edtEndH1.setEnabled(true);
                } else{
                    edtStartH1.setEnabled(false);
                    edtStartH1.setText("");
                    edtEndH1.setEnabled(false);
                    edtEndH1.setText("");
                }
            break;
            case R.id.cbH2:
                if (checked){
                    edtStartH2.setEnabled(true);
                    edtEndH2.setEnabled(true);
                } else{
                    edtStartH2.setEnabled(false);
                    edtStartH2.setText("");
                    edtEndH2.setEnabled(false);
                    edtEndH2.setText("");
                }
                break;
            case R.id.cbH3:
                if (checked){
                    edtStartH3.setEnabled(true);
                    edtEndH3.setEnabled(true);
                } else{
                    edtStartH3.setEnabled(false);
                    edtStartH3.setText("");
                    edtEndH3.setEnabled(false);
                    edtEndH3.setText("");

                }
                break;
            case R.id.cbH4:
                if (checked){
                    edtStartH4.setEnabled(true);
                    edtEndH4.setEnabled(true);
                } else{
                    edtStartH4.setEnabled(false);
                    edtStartH4.setText("");
                    edtEndH4.setEnabled(false);
                    edtEndH4.setText("");
                }
                break;
            case R.id.cbH5:
                if (checked){
                    edtStartH5.setEnabled(true);
                    edtEndH5.setEnabled(true);
                } else{
                    edtStartH5.setEnabled(false);
                    edtStartH5.setText("");
                    edtEndH5.setEnabled(false);
                    edtEndH5.setText("");
                }
                break;
            case R.id.cbH6:
                if (checked){
                    edtStartH6.setEnabled(true);
                    edtEndH6.setEnabled(true);
                } else{
                    edtStartH6.setEnabled(false);
                    edtStartH6.setText("");
                    edtEndH6.setEnabled(false);
                    edtEndH6.setText("");
                }
                break;
            case R.id.cbH7:
                if (checked){
                    edtStartH7.setEnabled(true);
                    edtEndH7.setEnabled(true);
                } else{
                    edtStartH7.setEnabled(false);
                    edtStartH7.setText("");
                    edtEndH7.setEnabled(false);
                    edtEndH7.setText("");
                }
                break;
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                LatLng latLng = place.getLatLng();
                latitude    = latLng.latitude;
                longitude   = latLng.longitude;
                //Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void addBengkel(String nama, String alamat, String telepon,
                           double latitude, double longitude, HashMap<String, String> jambuka){
        String key = mBengkelRef.push().getKey();
        Bengkel bengkel = new Bengkel(nama,alamat,telepon,latitude,longitude,jambuka);
        mBengkelRef.child(key).setValue(bengkel);
    }

    public void setTime (final EditText edtText){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AddBengkelActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String sHrs, sMins;
                if(selectedMinute<10){
                    sMins = "0"+selectedMinute;
                }else{
                    sMins = String.valueOf(selectedMinute);
                }
                if(selectedHour<10){
                    sHrs = "0"+selectedHour;
                }else{
                    sHrs = String.valueOf(selectedHour);
                }
                edtText.setText( sHrs + ":" + sMins);
            }
        }, hour, minute, true);
        if (flagSetTime){
            mTimePicker.setTitle("Jam Buka");
        } else{
            mTimePicker.setTitle("Jam Tutup");
        }
        mTimePicker.show();
    }

}
