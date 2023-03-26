package ru.job4j.accidents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author: Egor Bekhterev
 * @date: 25.03.2023
 * @project: job4j_accidents
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Rule {

    @EqualsAndHashCode.Include
    private int id;

    private String name;
}
