package ru.job4j.accidents.service.data;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;
import ru.job4j.accidents.service.AccidentService;

import java.util.List;
import java.util.Optional;

/**
 * @author: Egor Bekhterev
 * @date: 29.03.2023
 * @project: job4j_accidents
 */
@Service
@AllArgsConstructor
@ThreadSafe
public class AccidentSpringDataService implements AccidentService {

    private final AccidentRepository accidentRepository;

    @Override
    public Optional<Accident> save(Accident accident) {
        return Optional.of(accidentRepository.save(accident));
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    @Override
    public List<Accident> findAll() {
        return accidentRepository.findAll();
    }

    @Override
    public boolean update(Accident accident) {
        if (accidentRepository.existsById(accident.getId())) {
            accidentRepository.save(accident);
            return true;
        }
        return false;
    }
}
