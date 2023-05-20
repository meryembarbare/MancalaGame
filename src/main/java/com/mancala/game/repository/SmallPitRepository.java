package com.mancala.game.repository;

import com.mancala.game.domain.SmallPit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmallPitRepository extends CrudRepository<SmallPit, Long> {


}
