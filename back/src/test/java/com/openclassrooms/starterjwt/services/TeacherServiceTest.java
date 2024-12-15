package com.openclassrooms.starterjwt.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {
	
	@Mock
	TeacherRepository teacherRepository;
	
	TeacherService teacherService;
	
	@BeforeEach
	public void init() {
    	teacherService = new TeacherService(teacherRepository);
	}
	
	@Test
    void testFindAll() {
        List<Teacher> teachers = List.of(new Teacher(), new Teacher());
        when(teacherRepository.findAll()).thenReturn(teachers);

        List<Teacher> result = teacherService.findAll();

        assertEquals(teachers, result);
        verify(teacherRepository, times(1)).findAll();
    }
	
    @Test
    void testFindById() {
        Long id = 1L;
        Teacher teacher = new Teacher();
        when(teacherRepository.findById(id)).thenReturn(Optional.of(teacher));

        Teacher result = teacherService.findById(id);

        assertEquals(teacher, result);
        verify(teacherRepository, times(1)).findById(id);
    }

    @Test
    void testFindById_TeacherDoesNotExist() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        Teacher foundTeacher = teacherService.findById(1L);

        assertNull(foundTeacher);

        verify(teacherRepository, times(1)).findById(1L);
    }

}
