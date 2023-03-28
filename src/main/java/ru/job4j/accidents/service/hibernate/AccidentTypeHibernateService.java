package ru.job4j.accidents.service.hibernate;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.hibernate.AccidentTypeHibernateRepository;
import ru.job4j.accidents.service.AccidentTypeService;

import java.util.List;
import java.util.Optional;

/**
 * @author: Egor Bekhterev
 * @date: 28.03.2023
 * @project: job4j_accidents
 */
@Service
@ThreadSafe
@AllArgsConstructor
public class AccidentTypeHibernateService implements AccidentTypeService {

    private AccidentTypeHibernateRepository accidentTypeHibernateRepository;

    @Override
    public Optional<AccidentType> findTypeById(int id) {
        return accidentTypeHibernateRepository.findTypeById(id);
    }

    @Override
    public List<AccidentType> findAllTypes() {
        return accidentTypeHibernateRepository.findAllTypes();
    }
}
