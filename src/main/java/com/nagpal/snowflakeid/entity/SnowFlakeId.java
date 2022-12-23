package com.nagpal.snowflakeid.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "snowflake_id")
@Getter
@Setter
public class SnowFlakeId {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private long unique_id;
}
