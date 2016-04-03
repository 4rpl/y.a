package ru.ilyayudov.yandexartists.app;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class ArtistAdapter extends ArrayAdapter<Artist> {

    public ArtistAdapter(Context context, int resource, List<Artist> objects) {
        super(context, resource, objects);
    }
}
