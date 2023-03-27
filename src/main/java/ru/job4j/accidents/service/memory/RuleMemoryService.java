package ru.job4j.accidents.service.memory;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.memory.RuleMemoryRepository;
import ru.job4j.accidents.service.RuleService;

import java.util.Optional;
import java.util.Set;

/**
 * @author: Egor Bekhterev
 * @date: 27.03.2023
 * @project: job4j_accidents
 */
@Service
@ThreadSafe
@AllArgsConstructor
public class RuleMemoryService implements RuleService {

    private RuleMemoryRepository ruleRepository;

    @Override
    public Optional<Rule> findRuleById(int id) {
        return ruleRepository.findRuleById(id);
    }

    @Override
    public Set<Rule> findAllRules() {
        return ruleRepository.findAllRules();
    }
}
