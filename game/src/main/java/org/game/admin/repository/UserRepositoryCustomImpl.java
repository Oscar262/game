package org.game.admin.repository;

import org.game.admin.input.UserPagination;
import org.game.admin.input.UserSearch;
import org.game.admin.model.User;

import java.util.List;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {


    @Override
    public List<User> findByQuery(UserPagination userPagination, UserSearch userSearch) {
        return null;
    }
}
