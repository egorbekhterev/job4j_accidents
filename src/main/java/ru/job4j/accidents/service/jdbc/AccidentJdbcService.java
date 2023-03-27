package ru.job4j.accidents.service.jdbc;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.jdbc.AccidentJdbcRepository;
import ru.job4j.accidents.service.AccidentService;

import java.util.List;
import java.util.Optional;

/**
 * @author: Egor Bekhterev
 * @date: 27.03.2023
 * @project: job4j_accidents
 */
@Service
@ThreadSafe
@AllArgsConstructor
public class AccidentJdbcService implements AccidentService {

    private AccidentJdbcRepository accidentJdbcRepository;

    @Override
    public Optional<Accident> save(Accident accident) {
        return accidentJdbcRepository.save(accident);
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentJdbcRepository.findById(id);
    }

    @Override
    public List<Accident> findAll() {
        return accidentJdbcRepository.findAll();
    }

    @Override
    public boolean update(Accident accident) {
        return accidentJdbcRepository.update(accident);
    }
}
