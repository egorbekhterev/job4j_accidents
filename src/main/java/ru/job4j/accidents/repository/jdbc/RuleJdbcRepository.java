package ru.job4j.accidents.repository.jdbc;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.interfaces.RuleRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author: Egor Bekhterev
 * @date: 27.03.2023
 * @project: job4j_accidents
 */
@AllArgsConstructor
@Repository
@ThreadSafe
public class RuleJdbcRepository implements RuleRepository {

    private final JdbcTemplate jdbc;

    private static final Logger LOGGER = LoggerFactory.getLogger(AccidentJdbcRepository.class.getName());

    private final RowMapper<Rule> ruleRowMapper = (resultSet, rowNum) -> {
        Rule rule = new Rule();
        rule.setId(resultSet.getInt("id"));
        rule.setName(resultSet.getString("name"));
        return rule;
    };

    @Override
    public Optional<Rule> findRuleById(int id) {
        var rule = jdbc.queryForObject("select id, name from rules where id = ?", ruleRowMapper, id);
        return Optional.ofNullable(rule);
    }

    @Override
    public Set<Rule> findAllRules() {
        return new HashSet<>(jdbc.query("select id, name from rules", ruleRowMapper));
    }
}
