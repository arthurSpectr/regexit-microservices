package regexit.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import regexit.clients.fraud.FraudCheckResponse;
import regexit.clients.fraud.FraudClient;
import regexit.clients.fraud.NotificationClient;
import regexit.clients.fraud.NotificationRequest;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final RestTemplate restTemplate;

    private final FraudClient fraudClient;

    private final NotificationClient notificationClient;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstname(request.firstName())
                .lastname(request.lastName())
                .email(request.email())
                .build();

        //todo: check if email valid
        //todo: check if email not taken
        customerRepository.saveAndFlush(customer);
        //todo: check if fraudster

        FraudCheckResponse fraudResponse = fraudClient.isFraudster(customer.getId());

        if(fraudResponse.isFraudster()) {
            throw new IllegalArgumentException("fraudster");
        }

        //todo: send notification
        notificationClient.sendNotification(
                new NotificationRequest(
                        customer.getId(),
                        customer.getEmail(),
                        String.format("Hi %s wlecome to regexit services", customer.getFirstname())
                )
        );
    }
}
