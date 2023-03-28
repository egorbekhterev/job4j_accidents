package ru.job4j.accidents.service.hibernate;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.hibernate.AccidentHibernateRepository;
import ru.job4j.accidents.service.AccidentService;

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
public class AccidentHibernateService implements AccidentService {

    private AccidentHibernateRepository accidentHibernateRepository;

    @Override
    public Optional<Accident> save(Accident accident) {
        return accidentHibernateRepository.save(accident);
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentHibernateRepository.findById(id);
    }

    @Override
    public List<Accident> findAll() {
        return accidentHibernateRepository.findAll();
    }

    @Override
    public boolean update(Accident accident) {
        return accidentHibernateRepository.update(accident);
    }
}
