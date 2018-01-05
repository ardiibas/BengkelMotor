package com.efpro.bengkelmotor_01.Fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.efpro.bengkelmotor_01.Activity.DetailBengkelActivity;
import com.efpro.bengkelmotor_01.Activity.MainActivity;
import com.efpro.bengkelmotor_01.Bengkel;
import com.efpro.bengkelmotor_01.Foto;
import com.efpro.bengkelmotor_01.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment
        implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback, View.OnClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap       mGoogleMap;
    private MapView         mMapView;
    private View            mView;
    FloatingActionButton    fab_map, fab_myLocation;
    ArrayList<Bengkel>      tmpBengkel;
    ArrayList<Foto> mFotoBengkels;
    TextView                txtTNama, txtTAlamat, txtTJarak,
                            txtBNama, txtBAlamat, txtBJarak;
    CardView                cdvBengkelTop, cdvBengkelBottom;
    ImageView               imgBengkel;
    int setTag;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mView               = inflater.inflate(R.layout.fragment_map, container, false);
        fab_map             = (FloatingActionButton) mView.findViewById(R.id.fab_map);
        txtTNama            = (TextView) mView.findViewById(R.id.txtTNama);
        txtTAlamat          = (TextView) mView.findViewById(R.id.txtTAlamat);
        txtTJarak           = (TextView) mView.findViewById(R.id.txtTJarak);
        txtBNama            = (TextView) mView.findViewById(R.id.txtBNama);
        txtBAlamat          = (TextView) mView.findViewById(R.id.txtBAlamat);
        txtBJarak           = (TextView) mView.findViewById(R.id.txtBJarak);
        fab_myLocation      = (FloatingActionButton) mView.findViewById(R.id.fab_myLocation);
        cdvBengkelTop       = (CardView) mView.findViewById(R.id.cdvBengkelTop);
        cdvBengkelBottom    = (CardView) mView.findViewById(R.id.cdvBengkelBottom);
        imgBengkel          = (ImageView) mView.findViewById(R.id.imgBengkel);

        tmpBengkel          = ((MainActivity)getActivity()).getBengkelList();
        mFotoBengkels = ((MainActivity)getActivity()).getFotoBengkel();

        fab_map.setOnClickListener(this);
        fab_myLocation.setOnClickListener(this);
        cdvBengkelTop.setOnClickListener(this);
        cdvBengkelBottom.setOnClickListener(this);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = (MapView) mView.findViewById(R.id.mapLayout);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getActivity());
        mGoogleMap = googleMap;
        mGoogleMap.setOnMarkerClickListener(this);

        //Cardview menampilkan bengkel terdekat
        try{
            Bengkel bengkel = tmpBengkel.get(0);
            txtTNama.setText(bengkel.getbNama());
            txtTAlamat.setText(bengkel.getbAlamat());
            txtTJarak.setText(String.format("%.2f",bengkel.getbJarak()) + "Km");
        } catch (Exception e){
            e.printStackTrace();
        }

        //Add marker for each bengkel
        for (Bengkel bengkel:tmpBengkel) {
            LatLng bLocation = new LatLng(bengkel.getbLatitude(), bengkel.getbLongitude());
            mGoogleMap.addMarker(new MarkerOptions().position(bLocation).title(bengkel.getbNama()));
            Log.e("Nama", bengkel.getbNama());
        }

        //Add marker for your location and move camera
        LatLng myLocation = new LatLng(((MainActivity) getActivity()).getLatitude(), (((MainActivity) getActivity()).getLongitude()));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_map:
                BengkelFragment bengkelFragment = new BengkelFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.mainLayout, bengkelFragment).commit();
            break;
            case R.id.fab_myLocation:
                LatLng myLocation = new LatLng(((MainActivity) getActivity()).getLatitude(), (((MainActivity) getActivity()).getLongitude()));
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));
            break;
            case R.id.cdvBengkelTop:
                Bengkel topbengkel = tmpBengkel.get(0);
                Intent topbengkelintent = new Intent(getActivity(), DetailBengkelActivity.class);
                topbengkelintent.putExtra("BENGKEL",topbengkel);
                startActivity(topbengkelintent);
            break;
            case R.id.cdvBengkelBottom:
                Bengkel bengkel = tmpBengkel.get(setTag);
                Intent intent = new Intent(getActivity(), DetailBengkelActivity.class);
                intent.putExtra("BENGKEL",bengkel);
                startActivity(intent);
            break;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for(Bengkel bengkel: tmpBengkel){
            if(marker.getTitle().equals(bengkel.getbNama())){
                for(Foto fotoBengkel: mFotoBengkels){
                    if(bengkel.getbID().equals(fotoBengkel.getId())){
                        byte[] bytes = fotoBengkel.getFoto();
                        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        imgBengkel.setImageBitmap(bmp);
                    }
                }
                txtBNama.setText(bengkel.getbNama());
                txtBAlamat.setText(bengkel.getbAlamat());
                txtBJarak.setText(String.format("%.2f",bengkel.getbJarak()) + "Km");
                setTag = tmpBengkel.indexOf(bengkel);
                Log.d("cardview", "index = " + setTag);
                cdvBengkelBottom.setVisibility(View.VISIBLE);

            }
        }
        return false;
    }







}
