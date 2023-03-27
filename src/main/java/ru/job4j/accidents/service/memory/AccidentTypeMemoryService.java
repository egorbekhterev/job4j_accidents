package ru.job4j.accidents.service.memory;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.memory.AccidentTypeMemoryRepository;
import ru.job4j.accidents.service.AccidentTypeService;

import java.util.List;
import java.util.Optional;

/**
 * @author: Egor Bekhterev
 * @date: 27.03.2023
 * @project: job4j_accidents
 */
@Service
@ThreadSafe
@AllArgsConstructor
public class AccidentTypeMemoryService implements AccidentTypeService {

    private AccidentTypeMemoryRepository accidentTypeRepository;

    @Override
    public Optional<AccidentType> findTypeById(int id) {
        return accidentTypeRepository.findTypeById(id);
    }

    @Override
    public List<AccidentType> findAllTypes() {
        return accidentTypeRepository.findAllTypes();
    }
}
