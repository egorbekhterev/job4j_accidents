package ru.job4j.accidents.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: Egor Bekhterev
 * @date: 29.03.2023
 * @project: job4j_accidents
 */
@Data
@Entity
@Table(name = "authorities")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String authority;
}
