package pro.sky.telegrambot.service.impl;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.repository.NotificationRepository;
import pro.sky.telegrambot.service.NotificationService;

import java.util.regex.Pattern;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final String REGULAR_MESSAGE = "([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)";



    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

}
