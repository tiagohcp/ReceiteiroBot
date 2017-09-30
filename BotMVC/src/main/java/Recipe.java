
public class Recipe {
	
	private Dados dados;

	
	public Recipe(Dados dados) {
		this.dados = dados;
	}
	
	public String getImage() {
		return dados.imageURL;
	}
	
	public String toString(){
		return "Author: " + dados.getAuthor() + "\n" + "Title: " + dados.getTitle() + "\n" + "ID: " + dados.getID() + "\n" +
					"Rating:  " + dados.getRating();
	}

}
