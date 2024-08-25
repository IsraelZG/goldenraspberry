package com.igianesini.goldenraspberry.repositories;

import com.igianesini.goldenraspberry.domain.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {
    Optional<Producer> findByName(String name);

    List<Producer> findByNameIn(List<String> names);

    @Query("SELECT p FROM Producer p JOIN p.movies m WHERE m.winner = true GROUP BY p HAVING COUNT(m) > 1")
    List<Producer> findProducersWithMultipleWinningMovies();
}
