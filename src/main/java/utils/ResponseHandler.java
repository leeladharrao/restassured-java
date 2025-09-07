package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

import java.io.IOException;

public class ResponseHandler {

    public static <T> T deserializedResponse(Response response, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        T responseDeserialized = null;
        try {
            responseDeserialized = mapper.readValue(response.asString(), clazz);
            // Pretty print JSON
            String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDeserialized);
            System.out.println("Handling Response:\n" + jsonStr);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return responseDeserialized;
    }
}
