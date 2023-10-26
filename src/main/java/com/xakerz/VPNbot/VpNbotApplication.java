package com.xakerz.VPNbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootApplication
public class VpNbotApplication extends TelegramLongPollingBot {

	final private String BOT_NAME = "TrrustvpnBot";
	final private String BOT_TOKEN = "6543447074:AAERjrCy-6UKP1Ja75_jpT2fTYqnVsdtsJc";

	static public Location staticLocation = new Location();

	static  public  Location userLocation = new Location();

	@Override
	public void onUpdateReceived(Update update) {
		staticLocation.setLongitude(40.7128);
		staticLocation.setLatitude(-74.0060);
		sendLocation(update.getMessage().getChatId(), staticLocation);
		if (update.hasMessage() && update.getMessage().hasLocation()) {
			 userLocation = update.getMessage().getLocation();

			if (!staticLocation.equals(userLocation)) {
				// Если локации не совпадают, заменяем статическую локацию на новую
				staticLocation.setLongitude(userLocation.getLongitude());
				staticLocation.setLatitude(userLocation.getLatitude());
				sendLocation(update.getMessage().getChatId(), userLocation);
			}
		}
	}

	private void sendLocation(Long chatId, Location location) {
		SendLocation sendLocation = new SendLocation();
		sendLocation.setChatId(chatId);
				sendLocation.setLatitude(location.getLatitude());
		sendLocation.setLongitude(location.getLongitude());

		try {
			execute(sendLocation);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getBotUsername() {
		return BOT_NAME;
	}

	@Override
	public String getBotToken() {
		return BOT_TOKEN;
	}


}
