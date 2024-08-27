package org.game.admin.repository;

import org.game.admin.input.UserPagination;
import org.game.admin.input.UserSearch;
import org.game.admin.model.User;
import org.springframework.data.domain.Page;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepositoryCustom {

    @Transactional
    Page<User> findByQuery(UserPagination userPagination, UserSearch userSearch);
}
