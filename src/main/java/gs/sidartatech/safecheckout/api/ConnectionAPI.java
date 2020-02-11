package gs.sidartatech.safecheckout.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.ParameterizedType;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import com.google.gson.Gson;

import gs.sidartatech.safecheckout.Main;

public class ConnectionAPI<S, R> {
	protected R post(String apiURL, S entitySend) throws IOException {
        return makeConnection("POST", apiURL, entitySend);
    }

    protected R get(String apiURL, S entitySend) throws IOException {
        return makeConnection("GET", apiURL, entitySend);
    }

    protected R put(String apiURL, S entitySend) throws IOException {
        return makeConnection("PUT", apiURL, entitySend);
    }

    protected R delete(String apiURL, S entitySend) throws IOException {
        return makeConnection("DELETE", apiURL, entitySend);
    }

    protected R makeConnection(String connectionType, String apiURL, S entitySend) throws IOException {
        Gson gson = new Gson();
        String json = null;
        if (entitySend != null) {
        	json = gson.toJson(entitySend);
        } 
        URL url = new URL(Main.API_URL + apiURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(connectionType);
        connection.setRequestProperty("Content-type", "application/json");
        connection.setRequestProperty ("Authorization", Main.token);
        connection.setDoOutput(true);
        if (json != null) {
        	PrintStream printStream = new PrintStream(connection.getOutputStream());
            printStream.println(json);
        }
        connection.connect();
        InputStreamReader isReader = new InputStreamReader(connection.getInputStream());
        BufferedReader reader = new BufferedReader(isReader);
        StringBuilder sb = new StringBuilder();
        String str;
        while((str = reader.readLine())!= null){
            sb.append(str);
        }
        json = sb.toString();
        R entityReceive = null;
        entityReceive = gson.fromJson(json, ((ParameterizedType) Objects.requireNonNull(getClass().getGenericSuperclass())).getActualTypeArguments()[1]);
        return entityReceive;
    }
}
