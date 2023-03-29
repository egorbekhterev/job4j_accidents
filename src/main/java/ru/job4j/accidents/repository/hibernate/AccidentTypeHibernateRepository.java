package ru.job4j.accidents.repository.hibernate;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.interfaces.AccidentTypeRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author: Egor Bekhterev
 * @date: 28.03.2023
 * @project: job4j_accidents
 */
@AllArgsConstructor
@Repository
@ThreadSafe
public class AccidentTypeHibernateRepository implements AccidentTypeRepository {

    private SessionFactory sf;

    @Override
    public Optional<AccidentType> findTypeById(int id) {
        try (Session session = sf.openSession()) {
            return session.createQuery("SELECT i FROM AccidentType i WHERE i.id = :fId", AccidentType.class)
                    .setParameter("fId", id).uniqueResultOptional();
        }
    }

    @Override
    public List<AccidentType> findAllTypes() {
        try (Session session = sf.openSession()) {
            return session.createQuery(
                    "SELECT i FROM AccidentType i ORDER BY i.id", AccidentType.class).list();
        }
    }
}
