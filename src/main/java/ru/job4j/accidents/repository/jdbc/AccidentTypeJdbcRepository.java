package ru.job4j.accidents.repository.jdbc;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author: Egor Bekhterev
 * @date: 27.03.2023
 * @project: job4j_accidents
 */
@AllArgsConstructor
@Repository
@ThreadSafe
public class AccidentTypeJdbcRepository implements AccidentTypeRepository {

    private final JdbcTemplate jdbc;

    private static final Logger LOGGER = LoggerFactory.getLogger(AccidentJdbcRepository.class.getName());

    private final RowMapper<AccidentType> accidentTypeRowMapper = (resultSet, rowNum) -> {
        AccidentType accidentType = new AccidentType();
        accidentType.setId(resultSet.getInt("id"));
        accidentType.setName(resultSet.getString("name"));
        return accidentType;
    };

    @Override
    public Optional<AccidentType> findTypeById(int id) {
        var type = jdbc.queryForObject("select id, name from types where id = ?", accidentTypeRowMapper, id);
        return Optional.ofNullable(type);
    }

    @Override
    public List<AccidentType> findAllTypes() {
        return jdbc.query("select id, name from types", accidentTypeRowMapper);
    }
}
