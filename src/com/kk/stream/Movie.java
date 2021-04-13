package com.kk.stream;

public class Movie implements Comparable<Movie> {
	String name;
	int releaseDate;
	Language language;
	
	public Movie(String name, int releaseDate, Language language) {
		this.name = name;
		this.releaseDate = releaseDate;
		this.language = language;
	}
	
	public String getName() {
		return name;
	}
	public int getReleaseDate() {
		return releaseDate;
	}
	public Language getLanguage() {
		return language;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		if (language != other.language)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Movie [name=" + name + ", releaseDate=" + releaseDate + ", language=" + language + "]";
	}

	@Override
	public int compareTo(Movie o) {
		return Integer.valueOf(releaseDate).compareTo(o.getReleaseDate());
	}
	
}
