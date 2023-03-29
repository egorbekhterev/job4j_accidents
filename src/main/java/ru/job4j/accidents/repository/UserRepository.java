package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.User;

import java.util.Optional;

/**
 * @author: Egor Bekhterev
 * @date: 30.03.2023
 * @project: job4j_accidents
 */
public interface UserRepository extends CrudRepository<User, Integer> {
}
