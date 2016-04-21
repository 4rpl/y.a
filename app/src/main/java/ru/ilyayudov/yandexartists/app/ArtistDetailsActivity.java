package ru.ilyayudov.yandexartists.app;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

public class ArtistDetailsActivity extends AppCompatActivity {

    private ImageView cover;
    private TextView name;
    private TextView genres;
    private TextView albums;
    private TextView tracks;
    private TextView description;
    private TextView info;
    private TextView link;
    private ProgressBar pb;

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
        pb = (ProgressBar) findViewById(R.id.details_pb);
        link = (TextView) findViewById(R.id.link);
        info = (TextView) findViewById(R.id.details_info);
        //endregion

        final Artist artist = (Artist) getIntent().getSerializableExtra("artist");

        //region Вывод данных об исполнителе на страницу

        name.setText(artist.getName());
        if (artist.getLink() != null) {
            link.setVisibility(View.VISIBLE);
            link.setText(artist.getLink());
        }
        if (artist.getGenres().length > 0) {
            genres.setVisibility(View.VISIBLE);

            String[] sGenres = new String[artist.getGenres().length];
            for (int i = 0; i < sGenres.length; i++) {
                sGenres[i] = artist.getGenres()[i].toString(this);
            }

            genres.setText(TextUtils.join(", ", sGenres));
        }
        albums.setText(String.valueOf(artist.getAlbums()));
        tracks.setText(String.valueOf(artist.getTracks()));
        if (artist.getDescription() != null) {
            description.setVisibility(View.VISIBLE);
            description.setText(artist.getName() + " - " + artist.getDescription());
        }

        //endregion

        // Загрузка изображения
        if (artist.getCoverSmall() != null) {
            cover.setVisibility(View.VISIBLE);
            new CoverUploader().execute(artist.getCoverSmall());
            cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ArtistDetailsActivity.this, CoverObserverActivity.class);
                    intent.putExtra("uri_small", artist.getCoverSmall());
                    intent.putExtra("uri_big", artist.getCoverBig());
                    intent.putExtra("name", artist.getName());
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
            pb.setVisibility(View.GONE);
            if (result != null) {
                cover.setImageDrawable(result);
            } else {
                info.setVisibility(View.VISIBLE);
            }
        }
    }
}
