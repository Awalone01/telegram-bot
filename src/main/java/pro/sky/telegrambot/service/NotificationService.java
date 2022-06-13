package pro.sky.telegrambot.service;

import pro.sky.telegrambot.model.Notification;

import java.util.Optional;

public interface NotificationService {
    Optional<Notification> parse(String notificationBotAnswer);
}
