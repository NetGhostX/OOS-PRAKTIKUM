package bank;

import com.google.gson.*;

import java.io.ObjectOutput;
import java.lang.reflect.Type;

public class TransactionSerializer implements JsonSerializer<Transaction>, JsonDeserializer<Transaction>{

    /**
     * @param transaction
     * @param type
     * @param context
     * @return
     */
    @Override
    public JsonElement serialize(Transaction transaction, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        //adding common fields
        jsonObject.addProperty("date:", transaction.getDate());
        jsonObject.addProperty("Description:", transaction.getDescription());
        jsonObject.addProperty("amount:", transaction.getAmount() );

        //Add type information
        if(transaction instanceof Payment payment){
            jsonObject.addProperty("type:", "Payment");
            jsonObject.addProperty("incomingInterest", payment.getOutgoingInterest());
            jsonObject.addProperty("outgoingInterest", payment.getOutgoingInterest());
        } else if (transaction instanceof IncomingTransfer transfer) {
            jsonObject.addProperty("type", "IncomingTransfer");
            jsonObject.addProperty("sender", transfer.getSender());
            jsonObject.addProperty("recipient", transfer.getRecipient());
        } else if (transaction instanceof OutgoingTransfer transfer) {
            jsonObject.addProperty("type:" ,"OutgoingTransfer");
            jsonObject.addProperty("sender:", transfer.getSender());
            jsonObject.addProperty("recipient", transfer.getRecipient());
        }
        return jsonObject;
    }



    /**
     * @param jsonElement
     * @param typeOft
     * @return
     * @throws JsonParseException
     */
    @Override
    public Transaction deserialize(JsonElement jsonElement, Type typeOft, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        String date = jsonObject.get("date").getAsString();
        String description = jsonObject.get("description").getAsString();
        double amount = jsonObject.get("amount").getAsDouble();

        switch(type){
            case "Payment":
                double incomingInterest = jsonObject.get("incomingInterest").getAsDouble();
                double outgoingInterest = jsonObject.get("outgoingInterest").getAsDouble();
                return new Payment(date,description,amount,incomingInterest,outgoingInterest);
            case "IncomingTransfer":
                String incomingSender = jsonObject.get("sender").getAsString();
                String incomingRecipient = jsonObject.get("recipient").getAsString();
                return new IncomingTransfer(date, description, amount, incomingSender, incomingRecipient);

            case "OutgoingTransfer":
                String outgoingSender = jsonObject.get("sender").getAsString();
                String outgoingRecipient = jsonObject.get("recipient").getAsString();
                return new OutgoingTransfer(date, description, amount, outgoingSender, outgoingRecipient);

            default:
                throw new JsonParseException("Unknown transaction type: " + type);

        }
    }


}