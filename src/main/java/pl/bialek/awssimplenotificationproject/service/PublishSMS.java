package pl.bialek.awssimplenotificationproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.SnsException;

@Component("PublishSMS")
@RequiredArgsConstructor
public class PublishSMS {


    private final SnsClient snsClient;

    public void sendMessage(Integer id) {
        String message = "New item with id: {%s} added to dynamoDB table".formatted(String.valueOf(id));
        String phoneNumber = "+48886634674";

        try{
            PublishRequest smsNotification = PublishRequest.builder()
                    .message(message)
                    .phoneNumber(phoneNumber)
                    .build();

            snsClient.publish(smsNotification);
        }catch (SnsException exception){
            throw new RuntimeException(exception.awsErrorDetails().errorMessage());
        }

    }
}
