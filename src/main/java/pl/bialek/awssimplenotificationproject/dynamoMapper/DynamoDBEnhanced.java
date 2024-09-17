package pl.bialek.awssimplenotificationproject.dynamoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bialek.awssimplenotificationproject.domain.Greeting;
import pl.bialek.awssimplenotificationproject.dynamoSchema.GreetingItems;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

@Component("DynamoDBEnhanced")

public class DynamoDBEnhanced {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    @Autowired
    public DynamoDBEnhanced(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
    }


    public void putObject(Greeting greetingObj){


        try{
            DynamoDbTable<GreetingItems> mappedTable = dynamoDbEnhancedClient.table("Greeting",
                    TableSchema.fromBean(GreetingItems.class));

            GreetingItems greetingItems = new GreetingItems();
            greetingItems.setId(greetingObj.getId());
            greetingItems.setTitle(greetingObj.getTitle());
            greetingItems.setName(greetingObj.getName());
            greetingItems.setMessageText(greetingObj.getMessageText());


            PutItemEnhancedRequest putItemEnhancedRequest = PutItemEnhancedRequest.builder(GreetingItems.class)
                    .item(greetingItems)
                    .build();

            mappedTable.putItem(putItemEnhancedRequest);
        }catch (DynamoDbException exception){
            System.err.println(exception.getMessage());
        }


    }

}
