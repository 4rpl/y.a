package ru.ilyayudov.yandexartists.app;

import java.io.Serializable;

public class Artist implements Serializable {
    int id, tracks, albums;
    String name, link, description, coverSmall, coverBig;
    Genre[] genres;

    public Artist(
            int id,
            int tracks,
            int albums,
            String name,
            String link,
            String description,
            String coverSmall,
            String coverBig,
            Genre[] genres
            ) {
        this.id = id;
        this.tracks = tracks;
        this.albums = albums;
        this.name = name;
        this.link = link;
        this.description = description;
        this.coverSmall = coverSmall;
        this.coverBig = coverBig;
        this.genres = genres;
    }

    public static Genre getGenre(String s) {
        if (s.equals("pop"))            return Genre.Pop;
        if (s.equals("Dance"))          return Genre.Dance;
        if (s.equals("Electronics"))    return Genre.Electronics;
        return Genre.Pop;
    }

    public enum Genre {
        Pop,
        Dance,
        Electronics
    }

    @Override
    public String toString() {
        return name;
    }
}

// "id":1080505,
// "name":"Tove Lo",
// "genres":["pop","dance","electronics"],
// "tracks":81,
// "albums":22,
// "link":"http://www.tove-lo.com/",
// "description":"<...>",
// "cover":{
//      "small":"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/300x300",
//      "big":"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000"
// }