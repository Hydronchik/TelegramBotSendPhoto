package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PictureBot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        try {

            PictureBot pictureBot = new PictureBot();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(pictureBot);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private String userFirstName;
    private String userName;
    private Long userChatId;
    private boolean isStartedP = false;
    private static final long GROUP_CHAT_ID = 1050135410L;

    @Override
    public String getBotUsername() {
        return "HydroPoligen Bot";
    }

    @Override
    public String getBotToken() {
        return "6802325880:AAFdAu0sQoaC2ZQJzAqJ_7_GFuPlR8_QkSU";
    }

    @Override
    public void onUpdateReceived(Update update) {
        getPicturee(update);
        if (update.hasMessage()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            Message message = update.getMessage();
            userFirstName = message.getFrom().getFirstName();
            userName = message.getFrom().getUserName();
            if (messageText.equals("/start")) {
                SendMessage startMessage = new SendMessage();

                startMessage.setChatId(chatId);
                startMessage.setText("Привіт! Я просто бот клану HydroPoligen в BrawlStars.Щоб попасти до нас в групу тобі потрібно натиснути кнопку ->(ПОЧАТИ)<- і надіслати скріншот свого головного екрана в BRAWL STARS.Якщо ви случайно відправили не той скріншот просто заново введіть команду /start");

                startMessage.setReplyMarkup(createStartButton());
                
                try {
                    execute(startMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else if (update.hasCallbackQuery()) {

            String callBackData = update.getCallbackQuery().getData();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            if (callBackData.equals("action") ){
                SendMessage actionMessage = new SendMessage();
                actionMessage.setChatId(chatId);
                actionMessage.setText("Відправте скріншот.");
                actionMessage.setReplyMarkup(createButton());
                isStartedP = true;
                try {

                    execute(actionMessage);
                }catch (TelegramApiException e){

                    e.printStackTrace();
                }

            }
        }
    }

    public InlineKeyboardMarkup createLinkButton() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(0,new InlineKeyboardButton());
        row.get(0).setCallbackData("1");
        row.get(0).setText("Вступити в Групу");
        row.get(0).setUrl("https://t.me/+zCHl_fQhnUU1NzUy");

        rows.add(row);
        markup.setKeyboard(rows);
        return markup;
    }

    public InlineKeyboardMarkup createAcceptButton() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(0,new InlineKeyboardButton());
        row.get(0).setCallbackData("accept");
        row.get(0).setText("Прийняти");


        rows.add(row);
        markup.setKeyboard(rows);
        return markup;
    }
    public InlineKeyboardMarkup createStartButton() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(0,new InlineKeyboardButton());
        row.get(0).setCallbackData("action");
        row.get(0).setText("ПОЧАТИ");
        rows.add(row);
        markup.setKeyboard(rows);
        return markup;
    }
    public InlineKeyboardMarkup createButton() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(0,new InlineKeyboardButton());
        row.get(0).setCallbackData("action1");
        row.get(0).setText("ПРИКЛАД");
        row.get(0).setUrl("https://catherineasquithgallery.com/uploads/posts/2021-02/1612290974_180-p-bravo-stars-fioletovii-fon-209.jpg");
        rows.add(row);
        markup.setKeyboard(rows);
        return markup;
    }
    public void getMessagee(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            long chatId = message.getChatId();
            String text = message.getText();

            // Forward the message to the group chat
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(GROUP_CHAT_ID);
            sendMessage.setText(text);

            try {
                execute(sendMessage);

                // Send a confirmation message to the user

                SendMessage confirmationMessage = new SendMessage();
                confirmationMessage.setChatId(chatId);
                confirmationMessage.setText(" Вітаю тепер це побачіть адмін.");
                execute(confirmationMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
    public void getPicturee(Update update) {
        if (isStartedP) {
            try {
                SendMessage sendMessage1 = new SendMessage();
                userChatId = update.getMessage().getChatId();
                SendPhoto sendPhoto = new SendPhoto();
                sendMessage1.setChatId(update.getMessage().getChatId());
                sendMessage1.setText("Ваш скріншот відправлено адміну, зачекайте поки він прийме заявку.");
                sendPhoto.setChatId(GROUP_CHAT_ID);
                sendPhoto.setPhoto(new InputFile(update.getMessage().getPhoto().get(0).getFileId()));
                sendPhoto.setCaption("Прийшой новий запит від користувача" + " firstName: "+userFirstName +" userName: "+userName+" прийняти його?");
                sendPhoto.setReplyMarkup(createAcceptButton());
                execute(sendMessage1);
                execute(sendPhoto);

                isStartedP = false;
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.hasCallbackQuery()) {

            String callBackData = update.getCallbackQuery().getData();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            if (callBackData.equals("accept") ){
                SendMessage actionMessage = new SendMessage();
                actionMessage.setChatId(userChatId);
                actionMessage.setText("Вітаю! вашу заявку ОДОБРИВ адмін, тепер ти можеш вступити в наш ЧАТ");
                actionMessage.setReplyMarkup(createLinkButton());
                try {

                    execute(actionMessage);
                }catch (TelegramApiException e){

                    e.printStackTrace();
                }

            }
        }

    }
}