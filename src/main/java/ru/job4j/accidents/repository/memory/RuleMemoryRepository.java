package ru.job4j.accidents.repository.memory;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author: Egor Bekhterev
 * @date: 27.03.2023
 * @project: job4j_accidents
 */
@Repository
@ThreadSafe
@AllArgsConstructor
public class RuleMemoryRepository implements RuleRepository {

    private final Set<Rule> rules = new CopyOnWriteArraySet<>(Set.of(
            new Rule(1, "Статья. 1"),
            new Rule(2, "Статья. 2"),
            new Rule(3, "Статья. 3")
    ));

    @Override
    public Optional<Rule> findRuleById(int id) {
        return rules.stream().filter(rule -> rule.getId() == id).findFirst();
    }

    @Override
    public Set<Rule> findAllRules() {
        return rules;
    }
}
