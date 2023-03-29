package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;

/**
 * @author: Egor Bekhterev
 * @date: 29.03.2023
 * @project: job4j_accidents
 */
public interface AccidentTypeRepository extends CrudRepository<AccidentType, Integer> {

    List<AccidentType> findAll();
}
