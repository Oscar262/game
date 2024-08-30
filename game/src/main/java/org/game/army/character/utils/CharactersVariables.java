package org.game.army.character.utils;

import org.game.army.character.model.Card;
import org.game.army.character.model.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class CharactersVariables {

    @Autowired
    private Random random;


    private int getRandomNum(int max) {
        return random.nextInt(max) + 1;
    }

    public Character.Gender getGender() {
        return Character.Gender.values()[(getRandomNum(1))];
    }

    public String getMaleName() {
        int randomNum = getRandomNum(50);
        return maleNames.get(randomNum);
    }

    public String getFemaleName() {
        int randomNum = getRandomNum(50);
        return femaleNames.get(randomNum);
    }

    public String getLastName() {
        int randomNum = getRandomNum(10);
        return lastNames.get(randomNum);
    }

    public long totalXPNextLevel(Long x) {
        return 500 * x * (x + 1);
    }

    public Card.Type getCharacterCard(Long userLevel) {
        double percentage = random.nextDouble() * 100;
        if (percentage == 100)
            return Card.Type.EPIC;
        if (percentage == 0)
            return Card.Type.EPIC;

        if (userLevel <= 5) {
            if (percentage > 5)
                return Card.Type.NONE;
            else
                return Card.Type.BRONZE;

        } else if (userLevel <= 20) {
            if (percentage > 20)
                return Card.Type.NONE;
            else if (percentage > 5)
                return Card.Type.BRONZE;
            else
                return Card.Type.SILVER;

        } else if (userLevel <= 50) {
            if (percentage > 35)
                return Card.Type.NONE;
            else if (percentage > 7)
                return Card.Type.BRONZE;
            else
                return Card.Type.SILVER;
        } else {
            if (percentage > 40)
                return Card.Type.BRONZE;
            else if (percentage > 12)
                return Card.Type.BRONZE;
            else if (percentage > 3)
                return Card.Type.SILVER;
            else
                return Card.Type.GOLD;
        }
    }

    private List<String> maleNames = List.of(
            "Alejandro",
            "Liam",
            "Matteo",
            "Sven",
            "Yusuf",
            "Dmitry",
            "Takeshi",
            "Hugo",
            "Pablo",
            "Johan",
            "Khaled",
            "Giovanni",
            "Arjun",
            "Björn",
            "Nikolai",
            "Miguel",
            "Aiden",
            "Omar",
            "Leonardo",
            "Hans",
            "Raúl",
            "Felix",
            "Ibrahim",
            "Paolo",
            "Rajesh",
            "Einar",
            "Diego",
            "Viktor",
            "Henrik",
            "Xavier",
            "Ali",
            "Tomás",
            "Kaspar",
            "Akira",
            "Carlos",
            "Luca",
            "Santiago",
            "Hassan",
            "Lars",
            "Mikhail",
            "André",
            "Bruno",
            "Gustav",
            "Ismail",
            "Elias",
            "Manuel",
            "Kai",
            "Fernando",
            "Jakob",
            "Emilio"
    );

    private List<String> femaleNames = List.of(
            "Isabella",
            "Sofía",
            "Emma",
            "Yasmin",
            "Chiara",
            "Olga",
            "Haruka",
            "Nina",
            "María",
            "Greta",
            "Aisha",
            "Clara",
            "Maya",
            "Elsa",
            "Irina",
            "Camila",
            "Lucía",
            "Hana",
            "Lea",
            "Fatima",
            "Gisela",
            "Elena",
            "Rina",
            "Anastasia",
            "Julia",
            "Noor",
            "Giulia",
            "Lena",
            "Naomi",
            "Vera",
            "Mónica",
            "Dalia",
            "Anja",
            "Elisabetta",
            "Sara",
            "Zara",
            "Katja",
            "Laura",
            "Alina",
            "Reina",
            "Florencia",
            "Malika",
            "Viktoria",
            "Amalia",
            "Mila",
            "Ingrid",
            "Carla",
            "Aya",
            "Paola",
            "Alicia"
    );

    private List<String> lastNames = List.of(
            "García",
            "Smith",
            "Fernández",
            "Müller",
            "López",
            "Rossi",
            "González",
            "Johnson",
            "Pérez",
            "Martínez",
            "Brown",
            "Hernández",
            "Silva",
            "Jones",
            "Sánchez",
            "Anderson",
            "Rodríguez",
            "Kim",
            "Nguyen",
            "Torres",
            "González",
            "Patel",
            "Wang",
            "Costa",
            "Jackson",
            "Ramírez",
            "Wilson",
            "Fischer",
            "Reyes",
            "Clark",
            "Rivera",
            "Thompson",
            "Duarte",
            "Young",
            "Flores",
            "Moore",
            "Oliveira",
            "Lee",
            "Cruz",
            "Pérez",
            "Taylor",
            "Moreira",
            "King",
            "Sousa",
            "Robinson",
            "Almeida",
            "Carter",
            "Rojas",
            "White",
            "Lima",
            "Walker",
            "Bianchi",
            "Davis",
            "Mendes",
            "Hall",
            "Costa",
            "Allen",
            "Russo",
            "Wright",
            "Moreno",
            "Scott",
            "Vargas",
            "Harris",
            "Weber",
            "Edwards",
            "Sousa",
            "Nelson",
            "Ferrari",
            "Mitchell",
            "Castillo",
            "Evans",
            "Carvalho",
            "Roberts",
            "Torres",
            "Phillips",
            "Martins",
            "Turner",
            "Gomes",
            "Campbell",
            "Ferreira",
            "Collins",
            "Lima",
            "Parker",
            "Souza",
            "Cook",
            "Ortiz",
            "Bailey",
            "Almeida",
            "Murphy",
            "Medina",
            "Cooper",
            "Pereira",
            "Morgan",
            "Santos",
            "Reed",
            "Castro",
            "Murphy",
            "Herrera",
            "Flores",
            "Bennett"
    );
}
