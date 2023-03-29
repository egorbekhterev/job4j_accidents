package ru.job4j.accidents.service.data;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;
import ru.job4j.accidents.service.RuleService;

import java.util.Optional;
import java.util.Set;

/**
 * @author: Egor Bekhterev
 * @date: 29.03.2023
 * @project: job4j_accidents
 */
@Service
@AllArgsConstructor
@ThreadSafe
public class RuleSpringDataService implements RuleService {

    private final RuleRepository ruleRepository;

    @Override
    public Optional<Rule> findRuleById(int id) {
        return ruleRepository.findById(id);
    }

    @Override
    public Set<Rule> findAllRules() {
        return ruleRepository.findAll();
    }
}
