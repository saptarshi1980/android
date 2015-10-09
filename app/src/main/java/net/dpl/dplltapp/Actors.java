package net.dpl.dplltapp;

public class Actors {

	private String id;
	private String description;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private String title;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String url;


	public Actors() {

	}

	@Override
	public String toString() {
		return 	"ID='" + id + '\'' +
				", Description='" + description + '\'' +
				", Title='" + title + '\'' +
				", URL='" + url + '\'' ;
	}
}
