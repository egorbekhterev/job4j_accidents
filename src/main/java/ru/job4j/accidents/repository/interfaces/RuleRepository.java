package ru.job4j.accidents.repository.interfaces;

import ru.job4j.accidents.model.Rule;

import java.util.Optional;
import java.util.Set;

/**
 * @author: Egor Bekhterev
 * @date: 27.03.2023
 * @project: job4j_accidents
 */
public interface RuleRepository {

    Optional<Rule> findRuleById(int id);

    Set<Rule> findAllRules();
}
