package com.t9l.millionkitchen;

import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.t9l.millionkitchen.tools.Methods;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by praneet on 24-02-2015.
 */
public class AddressFragment extends Fragment implements View.OnClickListener {
    private SupportMapFragment fragment;
    private GoogleMap map;
    Address address;
    Marker marker;
    EditText cityET, areaET, houseNoET, floorET, landmarkET;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Methods.setMenuItems(menu, false, false, false);
        return;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        fragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, fragment).commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, container, false);
        view.findViewById(R.id.backBtn).setOnClickListener(this);
        view.findViewById(R.id.nextBtn).setOnClickListener(this);
        cityET = (EditText) view.findViewById(R.id.cityET);
        areaET = (EditText) view.findViewById(R.id.areaET);
        houseNoET = (EditText) view.findViewById(R.id.houseNoET);
        floorET = (EditText) view.findViewById(R.id.floorET);
        landmarkET = (EditText) view.findViewById(R.id.landmarkET);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().hide();
        if (isPlayServicesAvailable()) {
            if (map == null) {
                map = fragment.getMap();
                map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                    @Override
                    public void onMapClick(LatLng point) {
                        new AsyncTask<LatLng, Void, List<Address>>() {

                            private ProgressDialog dialog;

                            protected void onPreExecute() {
                                dialog = new ProgressDialog(getActivity());
                                dialog.setCancelable(false);
                                dialog.setMessage("Locating...");
                                dialog.show();
                            }

                            ;

                            @Override
                            protected java.util.List<Address> doInBackground(
                                    LatLng... params) {
                                List<Address> addresses = null;
                                Geocoder geo = new Geocoder(getActivity(), Locale
                                        .getDefault());

                                try {
                                    addresses = geo.getFromLocation(
                                            params[0].latitude,
                                            params[0].longitude, 1);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return addresses;
                            }

                            @Override
                            protected void onPostExecute(
                                    java.util.List<Address> data) {
                                super.onPostExecute(data);
                                dialog.dismiss();
                                if (data != null && data.size() > 0) {
                                    address = data.get(0);
                                    setMarker(data.get(0));
                                } else
                                    Methods.openUtilityDialog(getActivity(),
                                            "Unable to get address. Please try some different location.");
                            }
                        }.execute(point);

                    }
                });
            }
        } else
            Toast.makeText(getActivity(), "Play Services are not available on your device.", Toast.LENGTH_SHORT).show();

    }

    public void setMarker(Address address) {
        LatLng latLng = new LatLng(address.getLatitude(),
                address.getLongitude());
        if (marker != null)
            marker.remove();
        marker = map.addMarker(new MarkerOptions().position(latLng).title(address.getAddressLine(0)).icon(BitmapDescriptorFactory
                .fromResource(R.drawable.ic_launcher)));
//        marker.showInfoWindow();
//        map.setOnMarkerDragListener(this);
        CameraUpdate center = CameraUpdateFactory.newLatLng(latLng);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

        map.moveCamera(center);
        // map.animateCamera(zoom);
        setFieldData();
    }

    private void setFieldData() {
        cityET.setText(address.getLocality());
        areaET.setText(address.getSubAdminArea());
    }

    protected boolean isPlayServicesAvailable() {
        int status = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getActivity());

        if (status == ConnectionResult.SUCCESS) {
            return (true);
        } else if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
            // deal with error
        } else {
            // maps is not available
        }

        return (false);
    }


    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backBtn:
                getFragmentManager()
                        .popBackStack();
                break;
            case R.id.nextBtn:
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new PaymentInfoFragment(),
                                "PaymentInfo").addToBackStack("PaymentInfo")
                        .commit();
                break;
        }
    }
}
