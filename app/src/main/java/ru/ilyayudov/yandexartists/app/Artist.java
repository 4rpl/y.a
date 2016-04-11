package ru.ilyayudov.yandexartists.app;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.net.CacheRequest;

public class Artist implements Serializable, Comparable<Artist> {
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

    public enum Genre {
        UNKNOWN_GENRE(""),
        POP("pop"),
        DANCE("dance"),
        ELECTRONICS("electronics"),
        RNB("rnb"),
        RAP("rap"),
        RUSRAP("rusrap"),
        SOUL("soul"),
        ALTERNATIVE("alternative"),
        COUNTRY("country"),
        URBAN("urban"),
        RUSROCK("rusrock"),
        LOCAL_INDIE("local-indie"),
        CLASSICAL("classical"),
        FOLK("folk"),
        ROCK("rock"),
        INDIE("indie"),
        RELAX("relax"),
        JAZZ("jazz"),
        BLUES("blues"),
        SOUNDTRACK("soundtrack"),
        LATINFOLK("latinfolk"),
        METAL("metal"),
        ESTRADA("estrada"),
        BARD("bard"),
        DNB("dnb"),
        UKRROCK("ukrrock"),
        PUNK("punk"),
        NEWWAVE("newwave"),
        AFRICAN("african"),
        HOUSE("house"),
        LOUNGE("lounge"),
        TRANCE("trance"),
        RUSFOLK("rusfolk"),
        DUBSTEP("dubstep"),
        DISCO("disco"),
        PROG("prog"),
        VIDEOGAME("videogame"),
        REGGAE("reggae"),
        INDUSTRIAL("industrial"),
        CONJAZZ("conjazz");

        String value;

        Genre(String g) {
            value = g;
        }

        public static Genre getGenre(String sGenre) {
            for (Genre g : Genre.values()) {
                if (g.getValue().equals(sGenre)) {
                    return g;
                }
            }
            return UNKNOWN_GENRE;
        }

        private String getValue() {
            return value;
        }

        //public String toString() {
        //    int id = 0;
        //    switch (this) {
        //        case UnknownGenre:
        //            id = R.string.unknown_genre;
        //            break;
        //        case Pop:
        //            id = R.string.pop;
        //            break;
        //        case Indy:
        //            id = R.string.indy;
        //            break;
        //        case Rock:
        //            id = R.string.rock;
        //            break;
        //        case Metal:
        //            id = R.string.metal;
        //            break;
        //        case Alternative:
        //            id = R.string.alternative;
        //            break;
        //        case Electronics:
        //            id = R.string.electronics;
        //            break;
        //        case Dance:
        //            id = R.string.dance;
        //            break;
        //        case Rap:
        //            id = R.string.rap;
        //            break;
        //        case RnB:
        //            id = R.string.rnb;
        //            break;
        //        case Jazz:
        //            id = R.string.jazz;
        //            break;
        //        case Blues:
        //            id = R.string.blues;
        //            break;
        //        case Reggae:
        //            id = R.string.reggae;
        //            break;
        //        case Punk:
        //            id = R.string.punk;
        //            break;
        //    }
        //    return getApplicationContext().getResources().getString(id);
        //}
    }

    @Override
    public int compareTo(@NonNull Artist another) {
        return name.compareTo(another.name);
    }

    @Override
    public String toString() {
        return name;
    }
}