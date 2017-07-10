package com.amigos.android.medios;

/**
 * Created by Jishnu Dey on 10-07-2017.
 */

/**
 * {@link Song} represents a song that the user wants to play.
 * It contains the name and the artist of that song.
 */
public class Song {

    // Name of the song
    private String mSongName;

    // Name of the artist of the song
    private String mArtistName;

    /**
     * Create a new Word object.
     *
     * @param songName   is the name of the song
     * @param artistName is the name of the artist of the song
     */
    public Song(String songName, String artistName) {
        mSongName = songName;
        mArtistName = artistName;
    }

    /**
     * Get the name of the song
     */
    public String getSongName() {
        return mSongName;
    }

    /**
     * Get the name of the artist of the song
     */
    public String getArtistName() {
        return mArtistName;
    }
}
