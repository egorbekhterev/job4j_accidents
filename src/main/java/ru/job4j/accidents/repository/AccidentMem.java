package ru.job4j.accidents.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: Egor Bekhterev
 * @date: 24.03.2023
 * @project: job4j_accidents
 */
@Repository
@ThreadSafe
public class AccidentMem implements AccidentRepository {

    private final AtomicInteger count = new AtomicInteger(0);

    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    public AccidentMem() {
        Accident accident = new Accident();
        accident.setAddress("Ул. Семьи Шамшиных, дом 1");
        accident.setName("Иванов Иван");
        accident.setText("Проезд на красный свет.");
        save(accident);
    }

    @Override
    public Optional<Accident> save(Accident accident) {
        accident.setId(count.incrementAndGet());
        return Optional.ofNullable(accidents.putIfAbsent(accident.getId(), accident));
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
