package com.nagpal.snowflakeid.repository;

import com.nagpal.snowflakeid.entity.SnowFlakeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDGeneratorRepo extends JpaRepository<SnowFlakeId, Integer> {
}
