package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

/**
 * @author: Egor Bekhterev
 * @date: 27.03.2023
 * @project: job4j_accidents
 */
public interface AccidentService {

    Optional<Accident> save(Accident accident);

    Optional<Accident> findById(int id);

    List<Accident> findAll();

    boolean update(Accident accident);
}
