
public class Ingredient{
	
	private Dados dados;
	private String ingred;
	private String steps;
	
	
	public Ingredient(Dados dados, String ingred, String steps) {
		this.dados = dados;
		this.ingred = ingred;
		this.steps = steps;
	}

	public String getImage() {
		return dados.imageURL;
	}
	
	public String getIngred() {
		return ingred;
	}
	public String getSteps() {
		return steps;
	}

	@Override
	public String toString(){
		return "Autor: " + dados.getAuthor() + "\n" + "Title: " + dados.getTitle() + "\n" + "ID: " + dados.getID() + "\n" + "Rating " + dados.getRating() + "\n" +
				"Ingredients " + this.ingred + "\n" + "Steps: " + this.steps;
	}

}
