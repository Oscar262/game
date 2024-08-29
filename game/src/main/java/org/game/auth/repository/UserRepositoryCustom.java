package org.game.auth.repository;

import org.game.auth.input.UserPagination;
import org.game.auth.input.UserSearch;
import org.game.auth.model.User;
import org.springframework.data.domain.Page;

import javax.transaction.Transactional;

public interface UserRepositoryCustom {

    @Transactional
    Page<User> findForLogin(UserPagination userPagination, UserSearch userSearch);
}
