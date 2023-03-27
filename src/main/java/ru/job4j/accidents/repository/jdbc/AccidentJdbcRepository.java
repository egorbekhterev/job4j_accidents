package ru.job4j.accidents.repository.jdbc;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.*;

/**
 * @author: Egor Bekhterev
 * @date: 26.03.2023
 * @project: job4j_accidents
 */
@AllArgsConstructor
@Repository
@ThreadSafe
public class AccidentJdbcRepository implements AccidentRepository {

    private final JdbcTemplate jdbc;

    private static final Logger LOGGER = LoggerFactory.getLogger(AccidentJdbcRepository.class.getName());

    private AccidentTypeJdbcRepository accidentTypeJdbcRepository;

    private RuleJdbcRepository ruleJdbcRepository;

    /**
     * Получаем множество уникальных значений {@link Rule} из внешней таблицы @ManyToMany accidents_rules.
     * @param id идентификатор
     * @return множество уникальных значений {@link Rule}
     */
    private Set<Rule> findRulesByAccidentId(int id) {
        return new HashSet<>(jdbc.query("select rule_id from accidents_rules where accident_id = ?",
                (resultSet, rowNum) -> {
                    Rule rule = new Rule();
                    rule.setId(resultSet.getInt("rule_id"));
                    rule.setName(ruleJdbcRepository
                            .findRuleById(resultSet.getInt("rule_id")).get().getName());
                    return rule;
                }, id));
    }

    private final RowMapper<Accident> accidentRowMapper = (resultSet, rowNum) -> {
        Accident accident = new Accident();
        accident.setId(resultSet.getInt("id"));
        accident.setName(resultSet.getString("name"));
        accident.setText(resultSet.getString("text"));
        accident.setAddress(resultSet.getString("address"));
        accident.setCarNumber(resultSet.getString("car_number"));
        accident.setType(accidentTypeJdbcRepository.findTypeById(resultSet.getInt("type_id")).get());
        accident.setRules(findRulesByAccidentId(resultSet.getInt("id")));
        return accident;
    };

    @Override
    public Optional<Accident> save(Accident accident) {
        Optional<Accident> rsl = Optional.empty();
        try {
            var generatedID = jdbc.queryForObject(
                    "insert into accidents (name, text, address, car_number, type_id) "
                            + "values (?, ?, ?, ?, ?) RETURNING id",
                    Integer.class,
                    accident.getName(), accident.getText(), accident.getAddress(),
                    accident.getCarNumber(), accident.getType().getId()
            );
            accident.setId(Objects.requireNonNull(generatedID));

            accident.getRules().forEach(rule -> jdbc.update(
                    "insert into accidents_rules (accident_id, rule_id) values (?, ?)",
                    accident.getId(), rule.getId()
            ));
            rsl = Optional.of(accident);
        } catch (Exception e) {
            LOGGER.error("Error in the save(Accident accident) method.", e);
        }
        return rsl;
    }

    @Override
    public List<Accident> findAll() {
        return jdbc.query("select id, name, text, address, car_number, type_id from accidents", accidentRowMapper);
    }

    @Override
    public Optional<Accident> findById(int id) {
        var accident = jdbc.queryForObject(
                "select id, name, text, address, car_number, type_id from accidents where id = ?",
                accidentRowMapper, id);
        return Optional.ofNullable(accident);
    }

    @Override
    public boolean update(Accident accident) {
        var affectedRows = jdbc.update("update accidents set name = ?, text = ?, address = ?, car_number = ?, type_id = ? "
                + "where id = ?", accident.getName(), accident.getText(), accident.getAddress(),
                accident.getCarNumber(), accident.getType().getId(), accident.getId());
        return affectedRows > 0;
    }
}
