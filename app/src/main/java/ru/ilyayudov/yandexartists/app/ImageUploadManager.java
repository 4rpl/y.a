package ru.ilyayudov.yandexartists.app;

import android.graphics.drawable.Drawable;
import android.util.LruCache;

import java.io.*;
import java.net.URL;

public class ImageUploadManager {
    static LruCache<String, Drawable> cache = new LruCache<String, Drawable>(2 * 1024 * 1024); // 2 МБ

    public static Drawable GetImage(String path) throws IOException {
        Drawable image = cache.get(path);
        if (image != null) {
            return image;
        }
        image = Drawable.createFromStream((InputStream) new URL(path).getContent(), "image");
        cache.put(path, image);
        return image;
    }
}
