package ru.job4j.accidents.repository.memory;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.interfaces.AccidentRepository;
import ru.job4j.accidents.repository.interfaces.AccidentTypeRepository;
import ru.job4j.accidents.repository.interfaces.RuleRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: Egor Bekhterev
 * @date: 24.03.2023
 * @project: job4j_accidents
 */
@Repository
@ThreadSafe
@AllArgsConstructor
public class AccidentMemoryRepository implements AccidentRepository {

    private final AtomicInteger count = new AtomicInteger(0);

    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    private AccidentTypeRepository accidentTypeRepository;

    private RuleRepository ruleRepository;

    public AccidentMemoryRepository() {
        Accident accident = new Accident();
        accident.setAddress("Ул. Семьи Шамшиных, дом 1");
        accident.setName("Иванов Иван");
        accident.setText("Проезд на красный свет.");
        accident.setCarNumber("o000oo999");
        accident.setType(new AccidentType(3, "Машина и велосипед"));
        accident.setRules(Set.of(new Rule(1, "Статья. 1")));
        save(accident);
    }

    @Override
    public Optional<Accident> save(Accident accident) {
        accident.setId(count.incrementAndGet());
        var result = accidents.putIfAbsent(accident.getId(), accident);
        return Optional.ofNullable(result == null ? accident : null);
    }

    @Override
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    @Override
    public List<Accident> findAll() {
        return new ArrayList<>(accidents.values());
    }

    @Override
    public boolean update(Accident accident) {
        return accidents.replace(accident.getId(), accidents.get(accident.getId()), accident);
    }
}
