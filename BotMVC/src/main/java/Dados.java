
public class Dados {
	
	protected String author;
	protected String title;
	protected double rating;
	protected String id;
	protected String imageURL;
	
	public Dados(String author, String title, double rating, String id, String imageURL) {
		this.author = author;
		this.title = title;
		this.rating = rating;
		this.id = id;
		this.imageURL = imageURL;
	}
	
	public String getAuthor() {
		return author;
	}
	public String getTitle() {
		return title;
	}
	public double getRating() {
		return rating;
	}
	public String getID() {
		return id;
	}
	public String getImage() {
		return imageURL;
	}

}
