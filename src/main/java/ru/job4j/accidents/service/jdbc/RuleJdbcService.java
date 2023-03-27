package ru.job4j.accidents.service.jdbc;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.jdbc.RuleJdbcRepository;
import ru.job4j.accidents.service.RuleService;

import java.util.Optional;
import java.util.Set;

/**
 * @author: Egor Bekhterev
 * @date: 27.03.2023
 * @project: job4j_accidents
 */
@Service
@ThreadSafe
@AllArgsConstructor
public class RuleJdbcService implements RuleService {

    private RuleJdbcRepository ruleJdbcRepository;

    @Override
    public Optional<Rule> findRuleById(int id) {
        return ruleJdbcRepository.findRuleById(id);
    }

    @Override
    public Set<Rule> findAllRules() {
        return ruleJdbcRepository.findAllRules();
    }
}
