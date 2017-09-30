import com.pengrad.telegrambot.model.Update;

public interface Observer {

	public void update(long chatId, String answerData);
	public void updateImage(long chatId, String answerData);
	public void inline(Update update);
	
}
