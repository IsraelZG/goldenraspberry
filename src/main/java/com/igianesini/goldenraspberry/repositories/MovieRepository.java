package com.igianesini.goldenraspberry.repositories;
import com.igianesini.goldenraspberry.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
