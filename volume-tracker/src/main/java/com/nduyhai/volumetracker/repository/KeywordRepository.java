package com.nduyhai.volumetracker.repository;

import com.nduyhai.volumetracker.entity.KeywordEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<KeywordEntity, Long> {

  List<KeywordEntity> findByNameIn(List<String> names);
}
