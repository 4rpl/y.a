package ru.ilyayudov.yandexartists.app;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ArtistArrayAdapter extends ArrayAdapter<Artist> {
    Context context;

    public ArtistArrayAdapter(Context context, int resource, List<Artist> objects) {
        super(context, resource, objects);
        this.context = getContext();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Artist artist = getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.artist_list_tile, null);
        ((TextView) convertView.findViewById(R.id.primaryText)).setText(artist.name);
        ((TextView) convertView.findViewById(R.id.secondaryText)).setText(TextUtils.join(", ", artist.genres));

        return convertView;
    }
}
