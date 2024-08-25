package com.igianesini.goldenraspberry.repositories;
import com.igianesini.goldenraspberry.domain.Producer;
import com.igianesini.goldenraspberry.domain.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudioRepository extends JpaRepository<Studio, Long> {
    Optional<Studio> findByName(String name);

    List<Studio> findByNameIn(List<String> names);
}


