package ru.ilyayudov.yandexartists.app;

import android.content.Intent;
import android.gesture.Gesture;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
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
        pb = (ProgressBar) findViewById(R.id.small_cover_pb);
        link = (TextView) findViewById(R.id.link);
        //endregion

        final Artist artist = (Artist) getIntent().getSerializableExtra("artist");

        name.setText(artist.name);
        if (artist.link != null) {
            link.setVisibility(View.VISIBLE);
            link.setText(artist.link);
        }
        if (artist.genres.length > 0) {
            genres.setVisibility(View.VISIBLE);
            genres.setText(TextUtils.join(", ", artist.genres));
        }
        albums.setText(String.valueOf(artist.albums));
        tracks.setText(String.valueOf(artist.tracks));
        if (artist.description != null) {
            description.setVisibility(View.VISIBLE);
            description.setText(artist.name + " - " + artist.description);
        }
        if (artist.coverSmall != null) {
            cover.setVisibility(View.VISIBLE);
            new CoverUploader().execute(artist.coverSmall);
            cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ArtistDetailsActivity.this, CoverObserverActivity.class);
                    intent.putExtra("uri", artist.coverBig);
                    intent.putExtra("name", artist.name);
                    startActivity(intent);
                }
            });
        }
    }

    class CoverUploader extends AsyncTask<String, Void, Drawable> {
        @Override
        protected Drawable doInBackground(String... params) {
            Drawable coverSmall;
            try {
                coverSmall = ImageUploadManager.GetImage(params[0]);
            } catch (IOException e) {
                return null;
            }
            return coverSmall;
        }

        @Override
        protected void onPostExecute(Drawable result) {
            //pb.setVisibility(View.GONE);
            if (result != null) {
                cover.setImageDrawable(result);
            } else {
                info.setVisibility(View.VISIBLE);
            }
        }
    }
}
