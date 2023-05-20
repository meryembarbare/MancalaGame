package com.mancala.game.repository;

import com.mancala.game.domain.BigPit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BigPitRepository extends CrudRepository<BigPit, Long> {


}
