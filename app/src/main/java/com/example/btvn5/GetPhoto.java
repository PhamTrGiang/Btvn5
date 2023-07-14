package com.example.btvn5;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetPhoto extends AsyncTask<Void,Void,Void> {

    private String TAG = MainActivity.class.getSimpleName();

    public static String urlString = "https://jsonplaceholder.typicode.com/photos/";

    ArrayList<Photos> list;

    private ProgressDialog pDialog;
    ListView listView;
    Context context;
    PhotosAdapter adapter;

    public GetPhoto(ListView listView, Context context) {
        this.listView = listView;
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    protected Void doInBackground(Void... voids) {
        HttpHandler handler = new HttpHandler();
        // making request to url and getting response
        String jsonStr = handler.makeServiceCall(urlString);
        Log.e(TAG, "Response from url: " + jsonStr);
        if (jsonStr != null) {
            try {
                JSONObject jsonObject = new JSONObject("{\"photos\":"+jsonStr+"}");
                // Getting JSON Array node
                JSONArray photos = jsonObject.getJSONArray("photos");
                // looping through all Contacts
                for (int i = 0; i < photos.length(); i++) {
                    JSONObject c = photos.getJSONObject(i);
                    String albumId = c.getString("albumId");
                    String id = c.getString("id");
                    String title = c.getString("title");
                    String url = c.getString("url");
                    String thumbnailUrl = c.getString("thumbnailUrl");

                    Photos photo = new Photos();
                    photo.setAlbumId(albumId);
                    photo.setId(id);
                    photo.setTitle(title);
                    photo.setUrl(url);
                    photo.setThumbnailUrl(thumbnailUrl);
                    list.add(photo);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
        adapter = new PhotosAdapter(context, list);
        listView.setAdapter(adapter);

    }
}
