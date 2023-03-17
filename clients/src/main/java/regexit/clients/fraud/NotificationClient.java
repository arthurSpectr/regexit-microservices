package regexit.clients.fraud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("notification")
public interface NotificationClient {
    @PostMapping(path = "api/v1/notification/{customerId}")
    void sendNotification(NotificationRequest notificationRequest);
}
