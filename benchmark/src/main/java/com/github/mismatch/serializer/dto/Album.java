package com.github.mismatch.serializer.dto;

import java.util.List;

public class Album {
	private final String title;

	private final List<String> artists;

	private final int releaseYear;

	private final List<String> songsTitles;

	public Album(final String newTitle, final List<String> newArtists,
			final int newYear, final List<String> newSongsTitles) {
		title = newTitle;
		artists = newArtists;
		releaseYear = newYear;
		songsTitles = newSongsTitles;
	}

	public String getTitle() {
		return title;
	}

	public List<String> getArtists() {
		return artists;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public List<String> getSongsTitles() {
		return songsTitles;
	}

	@Override
	public String toString() {
		return "'" + title + "' (" + releaseYear + ") by " + artists + " features songs " + songsTitles;
	}
}
