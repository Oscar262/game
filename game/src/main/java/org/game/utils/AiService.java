package org.game.utils;

import org.game.army.character.model.Character;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Service
public class AiService {


    public String generateDescription(org.game.army.character.model.Character character) {
        StringBuilder sb = new StringBuilder();

        sb.append("Tipo: ").append(character.getType()).append("\n");
        sb.append("Subtipo: ").append(character.getSubType()).append("\n");
        sb.append("Nivel: ").append(character.getLevel()).append("\n");
        sb.append("Profesiones: ");
        if (character.getProfession() != null && !character.getProfession().isEmpty()) {
            for (Map.Entry<org.game.army.character.model.Character.Profession, org.game.army.character.model.Character.Qualification> entry : character.getProfession().entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
            }
        } else {
            sb.append("Ninguna");
        }
        sb.append("Atributos: ");
        if (character.getAttributes() != null) {
            for (Map.Entry<org.game.army.character.model.Character.Attribute, Long> entry : character.getAttributes().entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
            }
        }
        sb.append("Atributos maximos: ");
        if (character.getAttributes() != null) {
            for (Map.Entry<org.game.army.character.model.Character.Attribute, Long> entry : character.getMaxAttributes().entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
            }
        }
        sb.append("\n");
        sb.append("Habilidades: ");
        if (character.getSkills() != null) {
            for (Map.Entry<Long, org.game.army.character.model.Character.Qualification> entry : character.getSkills().entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
            }
        }
        StringBuilder prompt = new StringBuilder();
        //prompt.append(
        //                "Genera una pequeña descripción creativa de un personaje de fantasía basada en la información provista.\n" +
        //                        "- La descripción debe reflejar las habilidades, fuerza y capacidades del personaje según **sus atributos actuales**, **sin mencionar números ni valores concretos**.\n" +
        //                        "- Cada atributo tiene un valor máximo potencial (1-100) solo como referencia de su desarrollo futuro; **no debe usarse para determinar la descripción actual**.\n" +
        //                        "- Clasifica los atributos actuales de la siguiente manera:\n" +
        //                        "    * Valores bajos (aprox. 1-33): reflejan inexperiencia, debilidad o falta de dominio.\n" +
        //                        "    * Valores medios (aprox. 34-66): reflejan habilidad equilibrada y competencia razonable.\n" +
        //                        "    * Valores altos (aprox. 67-100): reflejan excelencia y dominio destacado.\n" +
        //                        "- Usa las profesiones y habilidades según su calificación interna (F a X) para reflejar talento, destreza y maestría de manera implícita:\n" +
        //                        "    * F: principiante, poco dominio.\n" +
        //                        "    * D-C: competente, habilidad media.\n" +
        //                        "    * B-A: avanzado, gran destreza.\n" +
        //                        "    * S: experto, prácticamente inigualable.\n" +
        //                        "    * X: EL MEJOR, sin comparación.\n" +
        //                        "- Describe atributos y habilidades **solo en función de su valor actual y su experiencia**, sin usar el máximo potencial ni revelar números exactos.\n" +
        //                        "- Haz que la descripción sea coherente con el tipo, subtipo, talentos y personalidad implícita del personaje.\n" +
        //                        "Devuelve únicamente un JSON con los campos: {\"name\":\"\",\"surname\":\"\",\"gender\":\"\",\"description\":\"\"}.\n\n" +
        //                        "Información del personaje:\n"
        //        )
        prompt.append(
                        "Genera descripciones creativas de un personaje de fantasía basado en la información provista.\n" +
                                "- Debes generar la descripción general del personaje (current) y además seis niveles de desarrollo de experiencia:\n" +
                                "    * Nivel 1: completamente inexperto, apariencia joven, vestimenta sencilla, postura insegura\n" +
                                "    * Nivel 2: un poco de experiencia, ligera madurez, más seguro, armas básicas o accesorio característico\n" +
                                "    * Nivel 3: aprendiz competente, apariencia más formada, vestimenta adaptada a su rol, postura confiada\n" +
                                "    * Nivel 4: nivel avanzado, madurez física y emocional, apariencia profesional, equipo más elaborado\n" +
                                "    * Nivel 5: casi experto, físico fuerte o ágil según tipo, expresión determinada, armas/armadura avanzada\n" +
                                "    * Nivel 6: experto, maestría total, apariencia madura y experta, porte imponente, vestimenta y armas distintivas\n" +
                                "- La descripción 'current' debe centrarse únicamente en quién es el personaje: su historia, personalidad, motivaciones y valores. No incluir apariencia, edad, vestimenta, armas o postura.\n" +
                                "- Cada nivel debe incluir personalidad, habilidades, experiencia, edad aproximada, apariencia, vestimenta, armas, postura, expresión y accesorios característicos de ese nivel.\n" +
                                "- Clasifica los atributos según su nivel:\n" +
                                "    * Valores bajos (1-33 aprox.): inexperiencia, debilidad o falta de dominio\n" +
                                "    * Valores medios (34-66 aprox.): habilidad equilibrada y competencia razonable\n" +
                                "    * Valores altos (67-100 aprox.): excelencia y dominio destacado\n" +
                                "- Usa las profesiones y habilidades según su calificación interna (F a X) para reflejar talento y maestría implícitamente.\n" +
                                "- Haz que todas las descripciones sean coherentes con el mismo personaje, tipo, subtipo, talentos y personalidad implícita.\n" +
                                "- Cada descripción debe tener como máximo 100 palabras.\n" +
                                "- Devuelve únicamente un JSON con los siguientes campos:\n" +
                                "{\n" +
                                "  \"name\":\"\",\n" +
                                "  \"surname\":\"\",\n" +
                                "  \"gender\":\"\",\n" +
                                "  \"description\":{\n" +
                                "      \"current\":\"\",  // Quién es el personaje, su historia y motivación\n" +
                                "      \"level1\":\"\",\n" +
                                "      \"level2\":\"\",\n" +
                                "      \"level3\":\"\",\n" +
                                "      \"level4\":\"\",\n" +
                                "      \"level5\":\"\",\n" +
                                "      \"level6\":\"\"\n" +
                                "  }\n" +
                                "}\n\n" +
                                "Información del personaje:\n"
                ).append("\n")
                .append(sb)
                .append("Haz que la descripción 'current' se centre solo en quién es el personaje. Los niveles 1-6 deben incluir edad, apariencia, vestimenta, armas, postura, expresión y accesorios, manteniendo coherencia con su desarrollo y experiencia.\n");

        return prompt.toString();
    }

    public String generateCharacter(Character character, int seed) throws IOException, InterruptedException {
        String prompt = generateDescription(character);
        try {
            String encodedPrompt = URLEncoder.encode(prompt, "UTF-8");
            String url = "https://text.pollinations.ai/" + encodedPrompt + "?lang=es&format=json&seed" + seed;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            while (response.statusCode() != 200) {
                System.err.println(response.body());
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            }
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
            while (response.statusCode() != 200) {
                response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
            }
            return response.body();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}

