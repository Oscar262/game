package org.game.auth.input;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.game.auth.model.User;
import org.game.utils.Filter;
import org.game.utils.SpecificationFilters;

public class UserSearch extends SpecificationFilters<User> {

    private String name;

    @JsonProperty("last_name")
    private String lastName;

    private String username;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        addFilter("name", name, Filter.FilterType.STRING);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        addFilter("lastName", lastName, Filter.FilterType.STRING);
    }

    @JsonIgnore
    public void setLast_name(String lastName){
        setLastName(lastName);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        addFilter("username", username, Filter.FilterType.STRING);
    }
}
