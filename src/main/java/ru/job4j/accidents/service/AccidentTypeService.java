package ru.job4j.accidents.service;

import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

/**
 * @author: Egor Bekhterev
 * @date: 27.03.2023
 * @project: job4j_accidents
 */
public interface AccidentTypeService {

    Optional<AccidentType> findTypeById(int id);

    List<AccidentType> findAllTypes();
}
