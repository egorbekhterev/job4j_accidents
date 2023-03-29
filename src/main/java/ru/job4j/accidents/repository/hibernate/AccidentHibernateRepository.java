package ru.job4j.accidents.repository.hibernate;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.jpa.QueryHints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.interfaces.AccidentRepository;

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
public class AccidentHibernateRepository implements AccidentRepository {

    private SessionFactory sf;

    private static final Logger LOGGER = LoggerFactory.getLogger(AccidentHibernateRepository.class.getName());

    @Override
    public Optional<Accident> save(Accident accident) {
        var session = sf.openSession();
        Optional<Accident> rsl = Optional.empty();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            session.persist(accident);
            tx.commit();
            rsl = Optional.of(accident);
        } catch (Exception e) {
            LOGGER.error("Error in the save(Accident accident) method.", e);
            tx = session.getTransaction();
            if (tx.isActive()) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return rsl;
}

    @Override
    public Optional<Accident> findById(int id) {
        try (Session session = sf.openSession()) {
            return session.createQuery(
                    "SELECT DISTINCT a FROM Accident a JOIN FETCH a.type t JOIN FETCH a.rules r WHERE a.id = :fId",
                            Accident.class)
                    .setParameter("fId", id).uniqueResultOptional();
        }
    }

    @Override
    public List<Accident> findAll() {
        try (Session session = sf.openSession()) {
            return session.createQuery(
                    "SELECT DISTINCT a FROM Accident a JOIN FETCH a.type t JOIN FETCH a.rules r ORDER BY a.id",
                            Accident.class).setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false).getResultList();
        }
    }

    @Override
    public boolean update(Accident accident) {
        var session = sf.openSession();
        var rsl = false;
        Transaction tx;
        try {
            tx = session.beginTransaction();
            session.update(accident);
            tx.commit();
            rsl = true;
        } catch (Exception e) {
            tx = session.getTransaction();
            if (tx.isActive()) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return rsl;
    }
}
