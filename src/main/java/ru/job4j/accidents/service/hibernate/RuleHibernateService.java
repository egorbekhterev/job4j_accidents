package ru.job4j.accidents.service.hibernate;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.hibernate.RuleHibernateRepository;
import ru.job4j.accidents.service.RuleService;

import java.util.Optional;
import java.util.Set;

/**
 * @author: Egor Bekhterev
 * @date: 28.03.2023
 * @project: job4j_accidents
 */
@Service
@ThreadSafe
@AllArgsConstructor
public class RuleHibernateService implements RuleService {

    private RuleHibernateRepository ruleHibernateRepository;

    @Override
    public Optional<Rule> findRuleById(int id) {
        return ruleHibernateRepository.findRuleById(id);
    }

    @Override
    public Set<Rule> findAllRules() {
        return ruleHibernateRepository.findAllRules();
    }
}
