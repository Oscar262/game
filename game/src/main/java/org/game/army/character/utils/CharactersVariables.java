package org.game.army.character.utils;

import org.game.army.character.model.Card;
import org.game.army.character.model.Character;
import org.game.army.character.model.Skill;
import org.game.base.model.MainBase;
import org.game.base.model.MainBaseType;
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
        return random.nextInt(max);
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


    public Character.BasicType getType(MainBaseType mainBaseType) {
        return Character.BasicType.values()[random.nextInt(getRandomNum(mainBaseType.getAvailableCharacter().size()))];
    }

    public Map<Long, Character.Qualification> getSkills(Long level, List<Skill> skills) {
        int skillsNum = 0;
        if (level <= 5)
            skillsNum = 1;
        else if (level <= 20)
            skillsNum = 2;
        else
            skillsNum = 5;
        Map<Long, Character.Qualification> map = new HashMap<>();
        for (int i = 0; i < skillsNum; i++) {
            Skill skill = skills.get(getRandomNum(skills.size()));
            Character.Qualification qualification = Character.Qualification.values()[getRandomNum(skills.size())];
            map.put(skill.getId(), qualification);
        }
        return map;
    }

    public Map<Character.Attribute, Long> getAttributes(Long level) {
        Map<Character.Attribute, Long> map = new HashMap<>();
        for (int i = 0; i < Character.Attribute.values().length; i++) {
            Character.Attribute attribute = Character.Attribute.values()[i];
            Long value = null;

            if (level < 5)
                value = (long) getRandomNum(20);
            else if (level < 20)
                value = (long) (10 + getRandomNum(20));
            else if (level < 50)
                value = (long) (30 + getRandomNum(20));

            map.put(attribute, value);
        }
        return map;
    }

    public Map<Character.Attribute, Long> getMaxAttributes(Map<Character.Attribute, Long> attributes) {
        Map<Character.Attribute, Long> map = new HashMap<>();
        for (int i = 0; i < Character.Attribute.values().length; i++) {
            Character.Attribute attribute = Character.Attribute.values()[i];
            Long value = (long) 100 - getRandomNum(30);
            Long minAttribute = attributes.get(attribute);

            if (minAttribute > value)
                value = minAttribute;

            map.put(attribute, value);
        }
        return map;
    }

    public Character.SubType getSubTypes(Card.Type card, Character.BasicType type) {
        if (card != Card.Type.NONE && card != Card.Type.BRONZE & card != Card.Type.SILVER){
            return type.getSubTypes().get(getRandomNum(type.getSubTypes().size()));
        }
        else
            return null;
    }

    public Map<Character.Profession, Character.Qualification> getProfessions(Long level){
        Map<Character.Profession, Character.Qualification> map = new HashMap<>();
        for (int i = 0; i < Character.Profession.values().length; i++) {
            Character.Profession profession = Character.Profession.values()[i];
            Character.Qualification qualification = null;

            if (level < 5)
                qualification = Character.Qualification.values()[getRandomNum(1)];
            else if (level < 20)
                qualification = Character.Qualification.values()[getRandomNum(3)];
            else if (level < 50)
                qualification = Character.Qualification.values()[getRandomNum(Character.Qualification.values().length)];

            map.put(profession, qualification);
        }
        return map;
    }


    private final List<String> maleNames = List.of(
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

    private final List<String> femaleNames = List.of(
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

    private final List<String> lastNames = List.of(
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
