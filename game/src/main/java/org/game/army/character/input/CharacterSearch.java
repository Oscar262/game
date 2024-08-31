package org.game.army.character.input;

import lombok.Getter;
import org.game.army.character.model.Character;
import org.game.utils.Filter;
import org.game.utils.SpecificationFilters;

@Getter
public class CharacterSearch extends SpecificationFilters<Character> {

    private String userId;

    public void setUserId(String userId) {
        this.userId = userId;
        addFilter("user.id", userId, Filter.FilterType.NUMERIC);
    }
}
