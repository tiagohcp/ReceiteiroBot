import com.pengrad.telegrambot.model.Update;

public interface Subject {
	
	public void registerObserver(Observer observer);
	
	public void notifyObservers(long chatId, String answerData);
	
	public void notifyObserversImage(long chatId, String answerData);
	
	public void notifyObserversInline(Update update);

}
