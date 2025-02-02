package org.game.utils;

import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@org.springframework.stereotype.Service
public class AiService {

    @Value("${generate_images.api.key}")
    private String apiKeyImage;

    @Value("${generate_images.api.url}")
    private String urlImage;

    @Value("${generate_text.api.key}")
    private String apiKeyText;

    @Value("${generate_text.api.url}")
    private String urlText;


    public String generateImage(String description) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        String jsonBody = "{\"prompt\": \"" + description + "\", \"ratio\": \"9:16\"}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlImage + "/generate"))
                .header("Content-Type", "application/json")
                .header("API-Token", apiKeyImage)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public String get(String id) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlImage + "/get/" + id))
                .header("Content-Type", "application/json")
                .header("API-Token", apiKeyImage)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }


    public String generateDescription() throws IOException, InterruptedException {
        try {
            // Crear HttpClient
            HttpClient httpClient = HttpClient.newHttpClient();

            // Definir el prompt
            String prompt = "necesito que generes una descripcion de un personaje de fantasia con un maximo de 500 caracteres";//"I need you to generate a description for a fantasy character with a maximum of 500 characters";

            // Cuerpo de la solicitud (JSON)
            String requestBody = "{ \"inputs\": \"" + prompt + "\" }";

            // Crear la solicitud HTTP
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlText))
                    .header("Api-Key", apiKeyText) // Cabecera de autorización
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // Enviar la solicitud y obtener la respuesta
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Verificar el código de estado de la respuesta
            if (response.statusCode() == 200) {
                // Si la respuesta es exitosa, devolver el cuerpo
                return response.body();
            } else {
                // Si hubo un error, imprimir el código de estado y el cuerpo de la respuesta
                return "Error: " + response.statusCode() + " Response: " + response.body();
            }
        } catch (IOException | InterruptedException e) {
            // Manejo de excepciones
            e.printStackTrace();
            return "Error occurred during the request.";
        }
    }

}
