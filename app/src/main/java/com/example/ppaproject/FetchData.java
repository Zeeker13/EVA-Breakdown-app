package com.example.ppaproject;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class FetchData extends AsyncTask<Object, String, String> {

    String googleNearByPlaceData;
    GoogleMap googleMap;
    String url;

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject placeObject = jsonArray.getJSONObject(i);
                JSONObject locationObject = placeObject.getJSONObject("geometry").getJSONObject("location");

                double lat = locationObject.getDouble("lat");
                double lng = locationObject.getDouble("lng");
                String name = placeObject.getString("name");

                LatLng latLng = new LatLng(lat, lng);
                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(name);
                googleMap.addMarker(markerOptions);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(Object... objects) {
        try {
            googleMap = (GoogleMap) objects[0];
            url = (String) objects[1];
            DownloadUrl downLoadUrl = new DownloadUrl();
            googleNearByPlaceData = downLoadUrl.retireveUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googleNearByPlaceData;
    }
}
