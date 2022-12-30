package com.nagpal.snowflakeid.service;

import com.nagpal.snowflakeid.entity.SnowFlakeId;

import java.util.List;

public interface IDGeneratorService {

    SnowFlakeId generateID();

    List<SnowFlakeId> generateIDs(int batchSize);

    void copySave(int batchSize);

}
