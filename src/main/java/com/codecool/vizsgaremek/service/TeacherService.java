package com.codecool.vizsgaremek.service;

import com.codecool.vizsgaremek.exception.TeacherException;
import com.codecool.vizsgaremek.log.Log;
import com.codecool.vizsgaremek.mapper.TeacherMapper;
import com.codecool.vizsgaremek.modell.Teacher;
import com.codecool.vizsgaremek.modell.dto.TeacherDto;
import com.codecool.vizsgaremek.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {

    TeacherRepository teacherRepository;

    TeacherMapper teacherMapper;

    public TeacherService(TeacherRepository teacherRepository, TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
    }

    public void saveTeacher(TeacherDto teacher) {
        try {
            teacherRepository.save(teacherMapper.teacherDtoToEntity(teacher));

        } catch (RuntimeException e) {
            Log.log.info("Teacher can not be added" + e.getMessage());
        }

    }

    public void updateTeacherById(Long id, Teacher teacherUpdate) {
        teacherRepository.findById(id).map(teacher -> {
            teacher.setName(teacherUpdate.getName());
            teacher.setClassIdTeacher(teacherUpdate.getClassIdTeacher());
            teacher.setSubject(teacherUpdate.getSubject());
            return teacherRepository.save(teacher);
        });
        throw new TeacherException(id);

    }

    public void deleteTeacherById(Long id) {
        try {
            teacherRepository.deleteById(id);
        } catch (TeacherException e) {
            Log.log.info("Something went wrong when deleteing Teacher by id :" + id);
            Log.log.info("See details :" + e);
            throw new TeacherException(id);
        }

    }

    public List<TeacherDto> getAllTeacher() {
        List<TeacherDto> teacherListResponse = new ArrayList<>();
        List<Teacher> teacherList = teacherRepository.findAll();
        for (Teacher teacher : teacherList) {
            teacherListResponse.add(teacherMapper.teacherToDto(teacher));
        }
        return teacherListResponse;
    }

}