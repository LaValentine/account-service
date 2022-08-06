package lav.valentine.accounttestclient;

import lav.valentine.accounttestclient.domain.RequestMethod;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Request implements  Runnable {
    private final HttpClient client = HttpClient.newHttpClient();

    private final int idAccount;
    private final RequestMethod method;

    public Request(int idAccount, RequestMethod method) {
        this.idAccount = idAccount;
        this.method = method;
    }

    @Override
    public void run() {
        HttpRequest request = null;

        switch (method) {
            case GET_AMOUNT : request = HttpRequest.newBuilder()
                    .uri(URI.create(String.format("http://localhost:8080/api/%d/amount", idAccount)))
                    .GET()
                    .build(); break;
            case ADD_AMOUNT : request = HttpRequest.newBuilder()
                    .uri(URI.create(String.format("http://localhost:8080/api/%d/amount?value=%s", idAccount, (int) (Math.random() * Integer.MAX_VALUE))))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build(); break;
        }

        try {
            System.out.println(client.send(request, HttpResponse.BodyHandlers.ofString()).body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
