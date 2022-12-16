package com.nagpal.snowflakeid;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Configuration {

    @Value("${snowflake.machine-id}")
    private int machineId;
}
