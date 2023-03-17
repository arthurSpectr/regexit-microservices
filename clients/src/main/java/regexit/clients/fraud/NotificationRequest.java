package regexit.clients.fraud;

public record NotificationRequest(Integer customerId, String customerEmail, String message) {
}
