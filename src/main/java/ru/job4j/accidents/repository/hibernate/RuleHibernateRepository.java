package ru.job4j.accidents.repository.hibernate;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: Egor Bekhterev
 * @date: 28.03.2023
 * @project: job4j_accidents
 */
@AllArgsConstructor
@Repository
@ThreadSafe
public class RuleHibernateRepository implements RuleRepository {

    private SessionFactory sf;

    @Override
    public Optional<Rule> findRuleById(int id) {
        try (Session session = sf.openSession()) {
            return session.createQuery("SELECT i FROM Rule i WHERE i.id = :fId", Rule.class)
                    .setParameter("fId", id).uniqueResultOptional();
        }
    }

    @Override
    public Set<Rule> findAllRules() {
        try (Session session = sf.openSession()) {
            return session.createQuery(
                    "SELECT i FROM Rule i ORDER BY i.id", Rule.class).stream().collect(Collectors.toSet());
        }
    }
}
