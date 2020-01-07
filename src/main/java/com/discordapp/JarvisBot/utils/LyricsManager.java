package com.discordapp.JarvisBot.utils;

import org.jmusixmatch.MusixMatch;
import org.jmusixmatch.MusixMatchException;
import org.jmusixmatch.entity.lyrics.Lyrics;
import org.jmusixmatch.entity.track.Track;
import org.jmusixmatch.entity.track.TrackData;

public class LyricsManager {
	public static LyricsManager instance;
	MusixMatch musixMatch;
	public LyricsManager() {
		musixMatch = new MusixMatch(DataFields.lyricsAPIKey);
	}

	public String getLyrics(String query, String artistName) {
		try {
			Track track = musixMatch.getMatchingTrack(query, artistName);
			TrackData data = track.getTrack();
			int id = data.getTrackId();

			Lyrics lyrics = musixMatch.getLyrics(id);

			return lyrics.getLyricsBody();
		} catch (MusixMatchException ex) {
			return "No lyrics found";
		}
	}
	public static LyricsManager getInstance() {
		if(instance == null) {
			instance = new LyricsManager();
		}
		return instance;
	}
}
