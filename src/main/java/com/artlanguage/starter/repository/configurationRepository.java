package com.artlanguage.starter.repository;

import com.artlanguage.starter.models.Config;
import org.springframework.data.jpa.repository.JpaRepository;

public interface configurationRepository extends JpaRepository<Config, Integer> {

    Config findByName(String name);
}
