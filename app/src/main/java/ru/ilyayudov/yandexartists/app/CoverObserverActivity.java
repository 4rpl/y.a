package ru.ilyayudov.yandexartists.app;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import uk.co.senab.photoview.PhotoView;

import java.io.IOException;

public class CoverObserverActivity extends Activity {

    private PhotoView coverContainer;
    private ProgressBar pb;
    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_observer);
        setTitle(getIntent().getStringExtra("name"));

        //region Получение элементов UI

        coverContainer = (PhotoView) findViewById(R.id.observer);
        pb = (ProgressBar) findViewById(R.id.observer_pb);
        info = (TextView) findViewById(R.id.observer_info);

        //endregion

        // Если в кэше есть маленькая обложка, то ставим его для плавности
        coverContainer.setImageDrawable(ImageUploadManager.GetCachedImage(getIntent().getStringExtra("uri_small")));
        new CoverUploader().execute(getIntent().getStringExtra("uri_big"));
    }

    class CoverUploader extends AsyncTask<String, Void, Drawable> {
        @Override
        protected Drawable doInBackground(String... params) {
            Drawable coverBig;
            try {
                coverBig = ImageUploadManager.GetImage(params[0]);
            } catch (IOException e) {
                return null;
            }
            return coverBig;
        }

        @Override
        protected void onPostExecute(Drawable result) {
            pb.setVisibility(View.GONE);
            if (result != null) {
                coverContainer.setImageDrawable(result);
            } else {
                info.setVisibility(View.VISIBLE);
            }
        }
    }
}
