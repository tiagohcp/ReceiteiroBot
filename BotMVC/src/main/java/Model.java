import java.util.LinkedList;
import java.util.List;

import com.pengrad.telegrambot.model.Update;


public class Model implements Subject{
	
	private List<Observer> observers = new LinkedList<Observer>();
		
	private static Model uniqueInstance;
	
	Connection connection;
	
	private Model(){}
	
	public static Model getInstance(){
		if(uniqueInstance == null){
			uniqueInstance = new Model();
		}
		return uniqueInstance;
	}
	
	public void registerObserver(Observer observer){
		observers.add(observer);
	}
	
	public void notifyObservers(long chatId, String answerData){
		for(Observer observer:observers){
			observer.update(chatId, answerData);
		}
	}
	
	public void notifyObserversImage(long chatId, String imageData){
		for(Observer observer:observers){
			observer.updateImage(chatId, imageData);
		}
	}
	
	public void notifyObserversInline(Update update){
		for(Observer observer:observers){
			observer.inline(update);
		}
	}
	
	
	public void setConnetion(Connection connection){ //Strategy Pattern
		this.connection = connection;
	}
	
	public int searchRecipe(Update update){
		
		setConnetion(new Connection());

		String text  = update.message().text().replaceAll(" ", "+");
		List<Recipe> finalResult = this.connection.getDataRecipe(text);
		
		int cont = 0;
		
		if(finalResult.size() != 0){
			
			for(Recipe result:finalResult){				
				if(cont<10){
					this.notifyObservers(update.message().chat().id(), result.toString());
					this.notifyObserversImage(update.message().chat().id(), result.getImage());
				}
				else{
					break;
				}
				cont++;
			}
			
			this.notifyObserversInline(update);
			

			
		} else {
			this.notifyObservers(update.message().chat().id(), "Recipes not found for this ingredient");
		}
		
		return cont;		
	}
	
public int searchRecipePrev(Update update, int pos, String text){
		
		setConnetion(new Connection());

		List<Recipe> finalResult = this.connection.getDataRecipe(text);

		if(finalResult.size() != 0){
			int cont = 0;
			for(Recipe result:finalResult){
				if((cont>=pos-20) && (cont<pos-10)){
					this.notifyObservers(update.callbackQuery().message().chat().id(), result.toString());
					this.notifyObserversImage(update.callbackQuery().message().chat().id(), result.getImage());
				}
				else if(cont == pos-10){
					break;
				}
				cont++;
			}
						
			pos = cont;
			
		} else {
			this.notifyObservers(update.message().chat().id(), "Recipes not found for this ingredient");
		}
		
		return pos;		
	}
	
public int searchRecipeNext(Update update, int pos, String text){
	
	setConnetion(new Connection());

	List<Recipe> finalResult = this.connection.getDataRecipe(text);

	if(finalResult.size() != 0){
		int cont = 0;
		for(Recipe result:finalResult){	
			if((cont>=pos) && (cont<pos+10)){
				this.notifyObservers(update.callbackQuery().message().chat().id(), result.toString());
				this.notifyObserversImage(update.callbackQuery().message().chat().id(), result.getImage());
			}
			else if(cont == pos+10){
				break;
			}
			cont++;
		}
		
		//this.notifyObserversInline(update);
		
		pos = cont;
		
	} else {
		this.notifyObservers(update.message().chat().id(), "Recipes not found for this ingredient");
	}
	
	return pos;		
}
	
	public void searchIngredient(Update update){
		
		setConnetion(new Connection());
		String text  = update.message().text();
		List<Ingredient> finalResult = this.connection.getDataIngredient(text);
		if(finalResult.size() != 0){
			for(Ingredient result:finalResult){
				this.notifyObservers(update.message().chat().id(), result.toString());
				this.notifyObserversImage(update.message().chat().id(), result.getImage());
			}

		} else {
			this.notifyObservers(update.message().chat().id(), "Recipe not found");
		}
		
	}


}
