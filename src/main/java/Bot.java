import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.ArrayList;


public class Bot extends TelegramLongPollingBot {
    private final String BOT_NAME = "massageAstral_bot";
    private final String BOT_TOKEN = "6851895410:AAFAzQhS4D364xwHYs5D_IV44OM4FKHTDjg";
    Booking booking;
    private ReplyKeyboardMarkup replyKeyboardMarkup;

    Bot() {
        booking = new Booking();
        initKeyboard();
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {

            Message message = update.getMessage();
            String chatID = message.getChatId().toString();
            String responce = parceMessage(message.getText());

            System.out.println(message.getText());
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatID);
            sendMessage.setText(responce);
            sendMessage.setReplyMarkup(replyKeyboardMarkup);

            //отправка в чат
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String parceMessage(String textMessage) {
            booking.setBook(textMessage);

            if (textMessage.equals("/start") || textMessage.equals("Перезапуск")) {
                booking.getFree();
                initKeyboard();
                return "Бот запущен\nДля того, что бы забронировать время - нажми кнопку соответствующую вренмени и напиши свое ФИО.";
            }

            if (textMessage.contains(":"))
                return "Введите ФИО с пробелами";

            if (textMessage.equals("q1w2e3r4"))
                booking.checkAdmin();


            if (textMessage.equals("Проверить свободные места")) {
                booking.getFree();
                initMenu();
                return "Свободное время";
            }
            if(textMessage.contains(" ")) {
                initKeyboard();
                return booking.getBook();
            } else
                return "";
    }

    private void initKeyboard() {
        replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true); //подгоняем размер
        replyKeyboardMarkup.setOneTimeKeyboard(true); //скрываем после использования

        //создаем список с рядами кнопок
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();

        //Создаем один ряд кнопок и добавляем его в список
        KeyboardRow keyboardRow1 = new KeyboardRow();

        keyboardRows.add(keyboardRow1);
        keyboardRow1.add(new KeyboardButton("Перезапуск"));
        keyboardRow1.add(new KeyboardButton("Проверить свободные места"));

        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }

    private void initMenu() {

        replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true); //подгоняем размер
        replyKeyboardMarkup.setOneTimeKeyboard(true); //скрываем после использования

        //создаем список с рядами кнопок
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();

        String[] buttonArr = booking.getFree();
        //Добавляем кнопки с текстом
        keyboardRows.add(keyboardRow);
        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);

        if (!buttonArr[0].equals("")) {
            if (buttonArr.length <= 4)
                for (int i = 0; i < buttonArr.length; i++)
                    keyboardRow.add(new KeyboardButton(buttonArr[i]));
            else {
                for (int i = 0; i < 4; i++)
                    keyboardRow.add(new KeyboardButton(buttonArr[i]));

                for (int j = 4; j < buttonArr.length; j++)
                    keyboardRow1.add(new KeyboardButton(buttonArr[j]));
            }
        }

        keyboardRow2.add(new KeyboardButton("Перезапуск"));
        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }
}
