package com.example.ppaproject;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsAPI {
    public static void getData(Context context, String url, final VolleyResponseListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listener.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.getMessage());
            }
        });

        queue.add(stringRequest);
    }

    public interface VolleyResponseListener {
        void onResponse(String response);

        void onError(String message);
    }

    public static void showNearbyPlaces(String response, GoogleMap mMap) {
        try {
            mMap.clear(); // Clear the map before adding new markers

            JSONObject jsonObject = new JSONObject(response);
            JSONArray results = jsonObject.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject place = results.getJSONObject(i);
                JSONObject location = place.getJSONObject("geometry").getJSONObject("location");
                String name = place.getString("name");
                double lat = location.getDouble("lat");
                double lng = location.getDouble("lng");

                LatLng latLng = new LatLng(lat, lng);
                mMap.addMarker(new MarkerOptions().position(latLng).title(name));
            }
        } catch (JSONException e) {
            Log.e("MapsAPI", "Error parsing JSON", e);
        }
    }

    public static void currentLocation(GoogleMap mMap, double lat, double lng, String title) {
        mMap.clear(); // Clear the map before adding the marker

        LatLng latLng = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(latLng).title(title));
    }
}
