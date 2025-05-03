package com.github.iwmvi.knowyourfan.repository;

import com.github.iwmvi.knowyourfan.entity.Fan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FanRepository extends JpaRepository<Fan, Long> {
}
