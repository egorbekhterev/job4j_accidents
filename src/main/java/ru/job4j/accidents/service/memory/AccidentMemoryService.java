package ru.job4j.accidents.service.memory;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.memory.AccidentMemoryRepository;
import ru.job4j.accidents.service.AccidentService;

import java.util.List;
import java.util.Optional;

/**
 * @author: Egor Bekhterev
 * @date: 24.03.2023
 * @project: job4j_accidents
 */
@Service
@ThreadSafe
@AllArgsConstructor
public class AccidentMemoryService implements AccidentService {

    private AccidentMemoryRepository accidentRepository;

    public Optional<Accident> save(Accident accident) {
        return accidentRepository.save(accident);
    }

    public List<Accident> findAll() {
        return accidentRepository.findAll();
    }

    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    public boolean update(Accident accident) {
        return accidentRepository.update(accident);
    }
}
