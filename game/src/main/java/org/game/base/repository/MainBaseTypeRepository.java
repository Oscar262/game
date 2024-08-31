package org.game.base.repository;

import org.game.base.model.MainBaseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainBaseTypeRepository extends JpaRepository<MainBaseType, Long> {
}
