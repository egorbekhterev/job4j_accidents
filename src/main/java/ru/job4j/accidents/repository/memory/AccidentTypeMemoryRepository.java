package ru.job4j.accidents.repository.memory;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.interfaces.AccidentTypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author: Egor Bekhterev
 * @date: 27.03.2023
 * @project: job4j_accidents
 */
@Repository
@ThreadSafe
@AllArgsConstructor
public class AccidentTypeMemoryRepository implements AccidentTypeRepository {

    private final List<AccidentType> types = new CopyOnWriteArrayList<>(List.of(
            new AccidentType(1, "Две машины"),
            new AccidentType(2, "Машина и человек"),
            new AccidentType(3, "Машина и велосипед")
    ));

    @Override
    public Optional<AccidentType> findTypeById(int id) {
        return Optional.ofNullable(types.get(id - 1));
    }

    @Override
    public List<AccidentType> findAllTypes() {
        return types;
    }
}
