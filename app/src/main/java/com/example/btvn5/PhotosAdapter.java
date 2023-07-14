package com.example.btvn5;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class PhotosAdapter extends BaseAdapter {
    Context context;
    ArrayList<Photos> list;

    public PhotosAdapter(Context context, ArrayList<Photos> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View classView;
        if (view == null){
            classView =View.inflate(viewGroup.getContext(), R.layout.item,null);
        }else{
            classView = view;
        }

        final Photos objPhoto = list.get(i);

        TextView id = classView.findViewById(R.id.tvId);
        TextView albumId = classView.findViewById(R.id.tvAlbumId);
        TextView title = classView.findViewById(R.id.tvTiltle);
        ImageView img = classView.findViewById(R.id.img);

        id.setText("ID : "+objPhoto.getId());
        albumId.setText("AlbumId : " +objPhoto.getAlbumId());
        title.setText("Title : "+objPhoto.getTitle());

        Picasso.get().load(objPhoto.getThumbnailUrl()).into(img);

        return classView;
    }

}
