package com.codecool.vizsgaremek.service;

import com.codecool.vizsgaremek.exception.ClassException;
import com.codecool.vizsgaremek.log.Log;
import com.codecool.vizsgaremek.mapper.ClassMapper;
import com.codecool.vizsgaremek.modell.Class;
import com.codecool.vizsgaremek.modell.dto.ClassDto;
import com.codecool.vizsgaremek.repository.ClassRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClassService {

    ClassRepository classRepository;

    ClassMapper classMapper;

    public ClassService(ClassRepository classRepository, ClassMapper classMapper) {
        this.classRepository = classRepository;
        this.classMapper = classMapper;
    }

   /* @Transactional*/
    public void addClass(ClassDto newClass) {
        Class classE = classMapper.convertClassDtoToEntity(newClass);
        classRepository.save(classE);
        log.info("Szuper");

    }

    public List<ClassDto> listAllClass() {
        List<Class> classList = classRepository.findAll();
        return classList.stream().map(aClass ->
                classMapper.convertClassToDto(aClass)).collect(Collectors.toList());
    }

    public ClassDto getClassByName(String className) {
        ClassDto classResponse = new ClassDto();
        List<Class> classResponseList;
        classResponseList = classRepository.findAll();

        for (Class classEntity : classResponseList) {
            if (classEntity.getName().equals(className)) {
                classResponse = classMapper.convertClassToDto(classEntity);
                return classResponse;
            }

        }
        Log.log.info("(!getClassByName!)Something went wrong when looking for class by given name :" + className);
        return classResponse;
    }

    public ClassDto getClassById(Long id) {
        ClassDto classResponse;
        List<Class> classResponseList;
        classResponseList = classRepository.findAll();
        for (Class aClass : classResponseList) {
            if (aClass.getId().equals(id)) {
                classResponse = classMapper.convertClassToDto(aClass);
                Log.log.info("(!GetClassByID!) Class Found ! id: " + id);
                return classResponse;
            }
        }
        Log.log.info("(!GetClassByID! something went wrong");
        throw new ClassException(id);

    }


    public void deleteClass(long id) {
        classRepository.deleteById(id);
    }

    public void updateClass(Long id, Class update) {

        classRepository.findById(id).map(classById -> {
            classById.setName(update.getName());
            classById.setName(update.getName());
            return classRepository.save(classById);
        });
        Log.log.info("(!updateClass! something went wrong");
        throw new ClassException(id);
    }

}