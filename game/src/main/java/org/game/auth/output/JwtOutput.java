package org.game.auth.output;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtOutput {

    @JsonProperty("access_token")
    private String accessToke;

    private String type = "Bearer";

    private String email;

    private String name;

    private String lastname;

    public JwtOutput(String accessToke, String email, String name, String lastname) {
        this.accessToke = accessToke;
        this.email = email;
        this.name = name;
        this.lastname = lastname;
    }

    public String getAccessToke() {
        return accessToke;
    }

    public void setAccessToke(String accessToke) {
        this.accessToke = accessToke;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
