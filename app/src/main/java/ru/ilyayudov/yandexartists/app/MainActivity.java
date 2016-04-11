package ru.ilyayudov.yandexartists.app;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.LruCache;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    TextView message;
    ListView artists;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region Получение элементов UI

        message = (TextView) findViewById(R.id.info);
        artists = (ListView) findViewById(R.id.artists);
        progressBar = (ProgressBar) findViewById(R.id.small_cover_pb);

        //endregion

        // Начинаем загрузку данных в фоновом режиме
        new ArtistsDownloadTask().execute("http://download.cdn.yandex.net/mobilization-2016/artists.json");
    }

    // Получение данных

    class ArtistsDownloadTask extends AsyncTask<String, Void, ArrayList<Artist>> {

        @Override
        protected ArrayList<Artist> doInBackground(String[] params) {
            InputStream stream;
            ArrayList<Artist> artists;
            try {
                stream = GetInputStream(params[0]);
                artists = InputStreamToArtistList(stream);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
            return artists;
        }

        @Override
        protected void onPostExecute(ArrayList<Artist> result) {
            progressBar.setVisibility(View.GONE);
            if (result != null) {
                message.setVisibility(View.GONE);
                artists.setVisibility(View.VISIBLE);
                Collections.sort(result);
                artists.setAdapter(
                        new ArtistArrayAdapter(
                                MainActivity.this,R.layout.abc_list_menu_item_layout,
                                result
                        )
                );
                artists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(MainActivity.this, ArtistDetailsActivity.class);
                        Artist a = (Artist) parent.getItemAtPosition(position);
                        intent.putExtra("artist", a);
                        startActivity(intent);
                    }
                });
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
        private ArrayList<Artist> InputStreamToArtistList(InputStream stream) throws IOException, InterruptedException {
            ArrayList<Artist> artists = new ArrayList<Artist>();
            JsonReader jr = new JsonReader(new InputStreamReader(stream, "UTF-8"));

            // Разбираем массив
            jr.beginArray();

            while (jr.peek() != JsonToken.END_ARRAY) {
                int id = 0, tracks = 0, albums = 0;
                String name = null, link = null, description = null, coverSmall = null, coverBig = null;
                ArrayList<Artist.Genre> genres = new ArrayList<Artist.Genre>();

                // Разбираем очередной объект
                jr.beginObject();
                while (jr.peek() != JsonToken.END_OBJECT) {
                    //region Разбираем очередное поле
                    String fieldName = jr.nextName();
                    if (fieldName.equals("id")) {
                        id = jr.nextInt();
                    } else if (fieldName.equals("tracks")) {
                        tracks = jr.nextInt();
                    } else if (fieldName.equals("albums")) {
                        albums = jr.nextInt();
                    } else if (fieldName.equals("name")) {
                        name = jr.nextString();
                    } else if (fieldName.equals("link")) {
                        link = jr.nextString();
                    } else if (fieldName.equals("description")) {
                        description = jr.nextString();
                    } else if (fieldName.equals("cover")) {
                        jr.beginObject();
                        for (int i = 0; i < 2; i++) {
                            String coverName = jr.nextName();
                            if (coverName.equals("big")) {
                                coverBig = jr.nextString();
                            } else if (coverName.equals("small")) {
                                coverSmall = jr.nextString();
                            }
                        }
                        jr.endObject();
                    } else if (fieldName.equals("genres")) {
                        jr.beginArray();
                        while (jr.peek() != JsonToken.END_ARRAY) {
                            genres.add(Artist.Genre.getGenre(jr.nextString()));
                        }
                        jr.endArray();
                    }
                    //endregion
                }
                jr.endObject();

                // Формируем очередной объект из полученных данных
                artists.add(new Artist(id, tracks, albums, name, link, description, coverSmall, coverBig,
                        genres.toArray(new Artist.Genre[genres.size()])));
            }
            jr.endArray();

            jr.close();

            return artists;
        }
    }
}
