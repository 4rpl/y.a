package ru.ilyayudov.yandexartists.app;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import uk.co.senab.photoview.PhotoView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class CoverObserverActivity extends Activity {

    PhotoView coverContainer;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_observer);
        setTitle(getIntent().getStringExtra("name"));

        //region Получение элементов UI

        coverContainer = (PhotoView) findViewById(R.id.observer);
        pb = (ProgressBar) findViewById(R.id.big_cover_pb);

        //endregion

        new CoverUploader().execute(getIntent().getStringExtra("uri"));
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
                coverContainer.setImageDrawable(result);
            }
        }
    }
}
