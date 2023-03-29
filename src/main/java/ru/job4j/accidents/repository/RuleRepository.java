package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Rule;

import java.util.Set;

/**
 * @author: Egor Bekhterev
 * @date: 29.03.2023
 * @project: job4j_accidents
 */
public interface RuleRepository extends CrudRepository<Rule, Integer> {

    Set<Rule> findAll();
}
