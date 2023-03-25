package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

/**
 * @author: Egor Bekhterev
 * @date: 24.03.2023
 * @project: job4j_accidents
 */
public interface AccidentRepository {

    Optional<Accident> save(Accident accident);

    Optional<Accident> findById(int id);

    List<Accident> findAll();

    boolean update(Accident accident);

    Optional<AccidentType> findTypeById(int id);

    List<AccidentType> findAllTypes();
}
