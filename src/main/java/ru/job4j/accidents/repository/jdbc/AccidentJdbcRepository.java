package ru.job4j.accidents.repository.jdbc;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    private static final String SQL_FIND_BY_ID =
            "select a.id, a.name, a.text, a.address, a.car_number, "
                    + "a.type_id, t.name as Type_Name, ar.rule_id, r.name as Rule_Name "
                    + "from accidents a "
                    + "left join types t on a.type_id = t.id "
                    + "left join accidents_rules ar on a.id = ar.accident_id "
                    + "left join rules r on ar.rule_id = r.id "
                    + "where a.id = ?;";

    private static final String SQL_FIND_ALL =
            "select a.id, a.name, a.text, a.address, a.car_number, "
                    + "a.type_id, t.name as Type_Name, ar.rule_id, r.name as Rule_Name "
                    + "from accidents a "
                    + "left join types t on a.type_id = t.id "
                    + "left join accidents_rules ar on a.id = ar.accident_id "
                    + "left join rules r on ar.rule_id = r.id "
                    + "ORDER BY a.id;";

    private Accident extractAccident(ResultSet rs) throws SQLException {
        var accident = new Accident();
        accident.setId(rs.getInt("id"));
        accident.setName(rs.getString("name"));
        accident.setText(rs.getString("text"));
        accident.setAddress(rs.getString("address"));
        accident.setCarNumber(rs.getString("car_number"));
        accident.setType(
                new AccidentType(rs.getInt("type_id"), rs.getString("Type_Name")));
        accident.setRules(new HashSet<>());
        return accident;
    }

    private void extractRules(Accident accident, ResultSet rs) throws SQLException {
        var ruleId = rs.getInt("rule_id");
        accident.getRules().add(new Rule(ruleId, rs.getString("Rule_Name")));
    }

    @Override
    public List<Accident> findAll() {
        return jdbc.query(SQL_FIND_ALL, rs -> {
            Map<Integer, Accident> map = new HashMap<>();
            List<Accident> accidents = new ArrayList<>();
            while (rs.next()) {
                var accidentId = rs.getInt("id");
                var accident = map.get(accidentId);
                if (accident == null) {
                    accident = extractAccident(rs);
                    map.putIfAbsent(accidentId, accident);
                    accidents.add(accident);
                }
                extractRules(accident, rs);
            }
            return accidents;
        });
    }

    @Override
    public Optional<Accident> findById(int id) {
        return jdbc.query(SQL_FIND_BY_ID, rs -> {
            Map<Integer, Accident> map = new HashMap<>();
            List<Accident> accidents = new ArrayList<>();
            while (rs.next()) {
                var accidentId = rs.getInt("id");
                var accident = map.get(accidentId);
                if (accident == null) {
                    accident = extractAccident(rs);
                    map.putIfAbsent(accidentId, accident);
                    accidents.add(accident);
                }
                extractRules(accident, rs);
            }
            return Optional.ofNullable(accidents.get(0));
        }, id);
    }

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

    private boolean deleteFromAccidentsRulesById(int id) {
        var affectedRows = jdbc.update("delete from accidents_rules where accident_id = ?", id);
        return affectedRows > 0;
    }

    @Override
    public boolean update(Accident accident) {
        if (!deleteFromAccidentsRulesById(accident.getId())) {
            return false;
        }

        var affectedRows = jdbc.update("update accidents set name = ?, text = ?, address = ?, car_number = ?, type_id = ? "
                        + "where id = ?", accident.getName(), accident.getText(), accident.getAddress(),
                accident.getCarNumber(), accident.getType().getId(), accident.getId());

        accident.getRules().forEach(rule -> jdbc.update("insert into accidents_rules (accident_id, rule_id) "
                + "values (?, ?)", accident.getId(), rule.getId()));
        return affectedRows > 0;
    }
}
