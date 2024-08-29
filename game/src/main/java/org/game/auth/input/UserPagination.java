package org.game.auth.input;

import org.game.utils.OffsetPagination;

public class UserPagination extends OffsetPagination {

    private String q;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }
}
