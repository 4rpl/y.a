package ru.ilyayudov.yandexartists.app;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonToken;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView message;
    ListView artists;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region Инициализация элементов UI

        message = (TextView) findViewById(R.id.info);
        artists = (ListView) findViewById(R.id.artists);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //endregion

        // Начинаем загрузку данных в фоновом режиме
        new ArtistsDownloadTask().execute("http://download.cdn.yandex.net/mobilization-2016/artists.json");
    }

    //region Получение данных

    class ArtistsDownloadTask extends AsyncTask<String, Void, ArrayList<Artist>> {

        @Override
        protected void onPreExecute() {
            message.setText(getResources().getString(R.string.loading_on_progress));
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Artist> doInBackground(String[] params) {
            InputStream data;
            ArrayList<Artist> artists = null;
            try {
                //data = GetInputStream(params[0]);
                data = getResources().openRawResource(R.raw.data);
                artists = InputStreamToArtistList(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return artists;
        }

        @Override
        protected void onPostExecute(ArrayList<Artist> result) {
            progressBar.setVisibility(View.INVISIBLE);
            if (result != null) {
                message.setText("Count: " + result.size());
                artists.setAdapter(new ArrayAdapter<Artist>(MainActivity.this, 0, result));
            } else {
                String mes = getResources().getString(R.string.download_failed);
                message.setText(mes);
            }
        }

        // Path -> Stream
        private InputStream GetInputStream(String path) throws IOException {
            URL url = new URL(path);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setReadTimeout(6000);
            c.connect();
            return c.getInputStream();
        }

        // Stream -> ArrayList<Artist>
        private ArrayList<Artist> InputStreamToArtistList(InputStream stream) throws IOException {
            ArrayList<Artist> artists = new ArrayList<Artist>();

            JsonReader jr = new JsonReader(new InputStreamReader(stream));

            // Разбираем массив
            jr.beginArray();

            while (jr.peek() == JsonToken.BEGIN_OBJECT) {
                int id = 0, tracks = 0, albums = 0;
                String name = null, link = null, description = null, coverSmall = null, coverBig = null;
                Artist.Genre[] genres = null;

                // Разбираем очередной объект
                jr.beginObject();
                while (jr.peek() != JsonToken.END_ARRAY) {
                    //region Разбираем очередное поле
                    //String fieldName = jr.nextName();
                    //if (fieldName.equals("id")) {
                    //    id = jr.nextInt();
                    //} else if (fieldName.equals("tracks")) {
                    //    tracks = jr.nextInt();
                    //} else if (fieldName.equals("albums")) {
                    //    albums = jr.nextInt();
                    //} else if (fieldName.equals("name")) {
                    //    name = jr.nextString();
                    //} else if (fieldName.equals("link")) {
                    //    link = jr.nextString();
                    //} else if (fieldName.equals("description")) {
                    //    description = jr.nextString();
                    //} else if (fieldName.equals("cover")) {
                    //    jr.beginObject();
                    //    jr.endObject();
                    //} else if (fieldName.equals("genres")) {
                    //}

                    if (jr.peek() != JsonToken.NAME || !jr.nextName().equals("id")) continue;
                    id = jr.nextInt();
                    if (jr.peek() != JsonToken.NAME || !jr.nextName().equals("name")) continue;
                    name = jr.nextString();

                    if (jr.peek() != JsonToken.NAME || !jr.nextName().equals("genres")) continue;
                    jr.beginArray();
                    while (jr.peek() == JsonToken.STRING) {
                        //genres.add(Artist.getGenre(jr.nextString()));
                        jr.skipValue();
                    }
                    jr.endArray();

                    if (jr.peek() != JsonToken.NAME || !jr.nextName().equals("tracks")) continue;
                    tracks = jr.nextInt();
                    if (jr.peek() != JsonToken.NAME || !jr.nextName().equals("albums")) continue;
                    albums = jr.nextInt();
                    if (jr.peek() != JsonToken.NAME || !jr.nextName().equals("link")) continue;
                    link = jr.nextString();
                    if (jr.peek() != JsonToken.NAME || !jr.nextName().equals("description")) continue;
                    description = jr.nextString();

                    if (jr.peek() != JsonToken.NAME || !jr.nextName().equals("cover")) continue;
                    jr.beginObject();
                    if (jr.peek() != JsonToken.NAME || !jr.nextName().equals("small")) continue;
                    coverSmall = jr.nextString();
                    if (jr.peek() != JsonToken.NAME || !jr.nextName().equals("big")) continue;
                    coverBig = jr.nextString();
                    jr.endObject();
                    //endregion
                }
                jr.endObject();

                // Формируем очередной объект из полученных данных
                artists.add(new Artist(
                        id,
                        tracks,
                        albums,
                        name,
                        link,
                        description,
                        coverSmall,
                        coverBig,
                        genres));
            }
            jr.endArray();

            jr.close();

            return artists;
        }
    }

    //endregion
}
