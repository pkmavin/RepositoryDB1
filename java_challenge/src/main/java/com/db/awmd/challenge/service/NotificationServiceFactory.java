package com.db.awmd.challenge.service;

public class NotificationServiceFactory {

	public enum NotificationTypeEnum {
		EMAIL, SMS
	};

	public static final NotificationService emailService = new EmailNotificationService();

	public static NotificationService getNotificatioNService(NotificationTypeEnum type) {
		if (type == NotificationTypeEnum.EMAIL) {
			return emailService;
		}
		else

			return emailService;  // Return emailService since thats the only one available
	}
}