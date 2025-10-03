package org.game.army.character.input;

import lombok.Getter;
import org.game.army.character.model.Character;
import org.game.utils.Filter;
import org.game.utils.SpecificationFilters;

@Getter
public class CharacterSearch extends SpecificationFilters<Character> {

    private String userId;

    private String name;

    private String image;

    public void setUserId(String userId) {
        this.userId = userId;
        addFilter("user.id", userId, Filter.FilterType.NUMERIC);
    }

    public void setName(String name) {
        this.name = name;
        addFilter("name", name, Filter.FilterType.STRING);
    }

    public void setImage(String image) {
        this.image = image;
        addFilter("image", image, Filter.FilterType.NUMERIC);
    }
}
