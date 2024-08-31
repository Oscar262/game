package org.game.base.repository;

import org.game.base.model.MainBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainBaseRepository extends JpaRepository<MainBase, Long> {
}
