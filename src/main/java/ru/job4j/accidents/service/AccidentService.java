package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author: Egor Bekhterev
 * @date: 24.03.2023
 * @project: job4j_accidents
 */
@Service
@ThreadSafe
@AllArgsConstructor
public class AccidentService {

    private AccidentRepository accidentRepository;

    public Optional<Accident> save(Accident accident) {
        return accidentRepository.save(accident);
    }

    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    public List<Accident> findAll() {
        return accidentRepository.findAll();
    }

    public boolean update(Accident accident) {
        return accidentRepository.update(accident);
    }

    public Optional<AccidentType> findTypeById(int id) {
        return accidentRepository.findTypeById(id);
    }

    public List<AccidentType> findAllTypes() {
        return accidentRepository.findAllTypes();
    }

    public Optional<Rule> findRuleById(int id) {
        return accidentRepository.findRuleById(id);
    }

    public Set<Rule> findAllRules() {
        return accidentRepository.findAllRules();
    }
}
