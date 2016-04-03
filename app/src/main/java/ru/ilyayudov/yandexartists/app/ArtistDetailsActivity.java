package ru.ilyayudov.yandexartists.app;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawableWrapper;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ArtistDetailsActivity extends AppCompatActivity {

    ImageView cover;
    TextView name;
    TextView genres;
    TextView albums;
    TextView tracks;
    TextView description;
    TextView info;
    TextView link;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_details);

        //region Получение элементов UI
        cover = (ImageView) findViewById(R.id.cover);
        name = (TextView) findViewById(R.id.name);
        genres = (TextView) findViewById(R.id.genres);
        albums = (TextView) findViewById(R.id.album_counter);
        tracks = (TextView) findViewById(R.id.track_counter);
        description = (TextView) findViewById(R.id.description);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        //endregion

        Artist artist = (Artist) getIntent().getSerializableExtra("artist");

        name.setText(artist.name);
        albums.setText(String.valueOf(artist.albums));
        tracks.setText(String.valueOf(artist.tracks));
        description.setText(artist.description);

        if (artist.coverSmall != null) {
            new CoverUploader().execute(artist.coverSmall);
        } else {
            cover.setVisibility(View.GONE);
        }
    }

    class CoverUploader extends AsyncTask<String, Void, Drawable> {
        @Override
        protected Drawable doInBackground(String... params) {
            Drawable coverSmall;
            try {
                InputStream stream = (InputStream) new URL(params[0]).getContent();
                coverSmall = Drawable.createFromStream(stream, "small cover");
            } catch (IOException e) {
                return null;
            }
            return coverSmall;
        }

        @Override
        protected void onPostExecute(Drawable result) {
            super.onPostExecute(result);
            pb.setVisibility(View.INVISIBLE);
            if (result != null) {
                cover.setImageDrawable(result);
            } else {
                info.setVisibility(View.VISIBLE);
            }
        }
    }
}
