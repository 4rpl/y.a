package ru.ilyayudov.yandexartists.app;

import android.graphics.drawable.Drawable;
import android.util.LruCache;

import java.io.*;
import java.net.URL;

// Класс, через который осуществляется кэширование изображений
// Кэширование работает только в рамках одной итерации ЖЦ
public class ImageUploadManager {
    private static LruCache<String, Drawable> cache = new LruCache<String, Drawable>(2 * 1024 * 1024); // 2 МБ

    // Ищет изображение в кэше, если не находит - загружает
    public static Drawable GetImage(String path) throws IOException {
        Drawable image = cache.get(path);
        if (image != null) {
            return image;
        }
        image = Drawable.createFromStream((InputStream) new URL(path).getContent(), "image");
        cache.put(path, image);
        return image;
    }

    // Ищет изображение строго в кэше
    public static Drawable GetCachedImage(String path) {
        return cache.get(path);
    }
}
