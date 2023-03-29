package ru.job4j.accidents.service.data;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;
import ru.job4j.accidents.service.AccidentTypeService;

import java.util.List;
import java.util.Optional;

/**
 * @author: Egor Bekhterev
 * @date: 29.03.2023
 * @project: job4j_accidents
 */
@Service
@ThreadSafe
@AllArgsConstructor
public class AccidentTypeSpringDataService implements AccidentTypeService {

    private final AccidentTypeRepository accidentTypeRepository;

    @Override
    public Optional<AccidentType> findTypeById(int id) {
        return accidentTypeRepository.findById(id);
    }

    @Override
    public List<AccidentType> findAllTypes() {
        return accidentTypeRepository.findAll();
    }
}
