package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

/**
 * @author: Egor Bekhterev
 * @date: 27.03.2023
 * @project: job4j_accidents
 */
public interface AccidentTypeRepository {

    Optional<AccidentType> findTypeById(int id);

    List<AccidentType> findAllTypes();
}
