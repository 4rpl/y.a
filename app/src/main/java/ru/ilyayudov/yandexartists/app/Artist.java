package ru.ilyayudov.yandexartists.app;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.Serializable;

public class Artist implements Serializable, Comparable<Artist> {
    private int id, tracks, albums;
    private String name, link, description, coverSmall, coverBig;
    private Genre[] genres;

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

    //region Геттеры

    public int getAlbums() {
        return albums;
    }

    public int getId() {
        return id;
    }

    public int getTracks() {
        return tracks;
    }

    public Genre[] getGenres() {
        return genres;
    }

    public String getCoverBig() {
        return coverBig;
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

    public String getCoverSmall() {
        return coverSmall;
    }

    public String getDescription() {
        return description;
    }

    //endregion

    public enum Genre {
        //region Набор жанров
        //Токен         Строка в JSON   ID в ресурсах
        UNKNOWN_GENRE(  "",             R.string.other),
        POP(            "pop",          R.string.pop),
        DANCE(          "dance",        R.string.dance),
        ELECTRONICS(    "electronics",  R.string.electronics),
        RNB(            "rnb",          R.string.rnb),
        RAP(            "rap",          R.string.rap),
        RUSRAP(         "rusrap",       R.string.rusrap),
        SOUL(           "soul",         R.string.soul),
        ALTERNATIVE(    "alternative",  R.string.alternative),
        COUNTRY(        "country",      R.string.country),
        URBAN(          "urban",        R.string.urban),
        RUSROCK(        "rusrock",      R.string.rusrock),
        LOCAL_INDIE(    "local-indie",  R.string.local_indie),
        CLASSICAL(      "classical",    R.string.classical),
        FOLK(           "folk",         R.string.folk),
        ROCK(           "rock",         R.string.rock),
        INDIE(          "indie",        R.string.indie),
        RELAX(          "relax",        R.string.relax),
        JAZZ(           "jazz",         R.string.jazz),
        BLUES(          "blues",        R.string.blues),
        SOUNDTRACK(     "soundtrack",   R.string.soundtrack),
        LATINFOLK(      "latinfolk",    R.string.latinfolk),
        METAL(          "metal",        R.string.metal),
        ESTRADA(        "estrada",      R.string.estrada),
        BARD(           "bard",         R.string.bard),
        DNB(            "dnb",          R.string.dnb),
        UKRROCK(        "ukrrock",      R.string.ukrrock),
        PUNK(           "punk",         R.string.punk),
        NEWWAVE(        "newwave",      R.string.newwave),
        AFRICAN(        "african",      R.string.african),
        HOUSE(          "house",        R.string.house),
        LOUNGE(         "lounge",       R.string.lounge),
        TRANCE(         "trance",       R.string.trance),
        RUSFOLK(        "rusfolk",      R.string.rusfolk),
        DUBSTEP(        "dubstep",      R.string.dubstep),
        DISCO(          "disco",        R.string.disco),
        PROG(           "prog",         R.string.prog),
        VIDEOGAME(      "videogame",    R.string.videogame),
        REGGAE(         "reggae",       R.string.reggae),
        INDUSTRIAL(     "industrial",   R.string.industrial),
        CONJAZZ(        "conjazz",      R.string.conjazz);

        //endregion

        // jString = наименование жанра в JSON
        // strRes = идентификатор строки жанра в ресурсах
        private String jString;
        private int strRes;

        Genre(String s, int i) {
            jString = s;
            strRes = i;
        }

        public static Genre getGenre(String sGenre) {
            for (Genre g : Genre.values()) {
                if (g.jString.equals(sGenre)) {
                    return g;
                }
            }
            return UNKNOWN_GENRE;
        }

        // toString для получения названия жанра из ресурсов
        public String toString(Context context) {
            return context.getString(strRes);
        }
    }

    // Сравнение для сортировки по имени
    @Override
    public int compareTo(@NonNull Artist another) {
        return name.compareTo(another.name);
    }

    @Override
    public String toString() {
        return name;
    }
}