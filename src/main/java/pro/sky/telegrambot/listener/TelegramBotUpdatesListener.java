package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Notification;
import pro.sky.telegrambot.service.NotificationService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private static final String START_CMD = "/start";
    private static final String WELCOME = "Привет, я робот";
    private static final String NOT_CMD_TEXT = "Я не знаю что ответить на это";
    private static final String SAVE_NOTIFICATION = "Записал, и напомню Вам вовремя.";

    private final TelegramBot telegramBot;

    private final NotificationService notificationService;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, NotificationService notificationService) {
        this.telegramBot = telegramBot;
        this.notificationService = notificationService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    TelegramBot bot = new TelegramBot("BOT_TOKEN");

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            Message message = update.message();
            if (message.text().startsWith(START_CMD)) {
                sendMsg(getChatId(message), WELCOME);
            } else {
                Optional<Notification> anotherData = notificationService.parse(message.text());
                if (anotherData.isPresent()) {
                    scheduleNotification(extractChatId(message), anotherData.get());
                } else {
                    sendMsg(getChatId(message), NOT_CMD_TEXT);
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void scheduleNotification (Long chatId, String s) {
        sendMsg(chatId, SAVE_NOTIFICATION);

    }

    private Long getChatId(Message message) {
        return message.chat().id();
    }

    private void sendMsg(Long chatId, String s) {
        SendMessage sendMessage = new SendMessage(chatId, s);
        telegramBot.execute(sendMessage);
    }

}
