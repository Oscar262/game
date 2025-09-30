package org.game.ia;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import org.game.army.character.model.Character;
import org.springframework.stereotype.Service;

@Service
public class IaService {


    public String generateDescription(Character character) {
        StringBuilder sb = new StringBuilder();

        sb.append("Tipo: ").append(character.getType()).append("\n");
        sb.append("Subtipo: ").append(character.getSubType()).append("\n");
        sb.append("Nivel: ").append(character.getLevel()).append("\n");
        sb.append("Profesiones: ");
        if (character.getProfession() != null && !character.getProfession().isEmpty()) {
            for (Map.Entry<Character.Profession, Character.Qualification> entry : character.getProfession().entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
            }
        } else {
            sb.append("Ninguna");
        }
        sb.append("Atributos: ");
        if (character.getAttributes() != null) {
            for (Map.Entry<Character.Attribute, Long> entry : character.getAttributes().entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
            }
        }
        sb.append("Atributos maximos: ");
        if (character.getAttributes() != null) {
            for (Map.Entry<Character.Attribute, Long> entry : character.getMaxAttributes().entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
            }
        }
        sb.append("\n");
        sb.append("Habilidades: ");
        if (character.getSkills() != null) {
            for (Map.Entry<Long, Character.Qualification> entry : character.getSkills().entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
            }
        }
        StringBuilder prompt = new StringBuilder();
        prompt.append(
                        "Genera una pequeña descripción creativa de un personaje de fantasía basada en la información provista.\n" +
                                "- La descripción debe reflejar las habilidades, fuerza y capacidades del personaje según **sus atributos actuales**, **sin mencionar números ni valores concretos**.\n" +
                                "- Cada atributo tiene un valor máximo potencial (1-100) solo como referencia de su desarrollo futuro; **no debe usarse para determinar la descripción actual**.\n" +
                                "- Clasifica los atributos actuales de la siguiente manera:\n" +
                                "    * Valores bajos (aprox. 1-33): reflejan inexperiencia, debilidad o falta de dominio.\n" +
                                "    * Valores medios (aprox. 34-66): reflejan habilidad equilibrada y competencia razonable.\n" +
                                "    * Valores altos (aprox. 67-100): reflejan excelencia y dominio destacado.\n" +
                                "- Usa las profesiones y habilidades según su calificación interna (F a X) para reflejar talento, destreza y maestría de manera implícita:\n" +
                                "    * F: principiante, poco dominio.\n" +
                                "    * D-C: competente, habilidad media.\n" +
                                "    * B-A: avanzado, gran destreza.\n" +
                                "    * S: experto, prácticamente inigualable.\n" +
                                "    * X: EL MEJOR, sin comparación.\n" +
                                "- Describe atributos y habilidades **solo en función de su valor actual y su experiencia**, sin usar el máximo potencial ni revelar números exactos.\n" +
                                "- Haz que la descripción sea coherente con el tipo, subtipo, talentos y personalidad implícita del personaje.\n" +
                                "Devuelve únicamente un JSON con los campos: {\"name\":\"\",\"surname\":\"\",\"gender\":\"\",\"description\":\"\"}.\n\n" +
                                "Información del personaje:\n"
                )
                .append(sb)
                .append("\n")
                .append("Haz que la descripción tenga como máximo 100 palabras.\n");

        return prompt.toString();
    }

    public String generateCharacter(Character character) throws IOException, InterruptedException {
        String prompt = generateDescription(character);
        try {
            String encodedPrompt = URLEncoder.encode(prompt, "UTF-8");
            String url = "https://text.pollinations.ai/" + encodedPrompt + "?lang=es&format=json";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            return response.body();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] generateCharacterImage(String character) throws IOException, InterruptedException {
        try {
            String encodedPrompt = URLEncoder.encode(character, "UTF-8");
            String url = "https://image.pollinations.ai/prompt/" + encodedPrompt + "?lang=es&format=json";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
            return response.body();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }    }
}
