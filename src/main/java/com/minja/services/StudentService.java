package com.minja.services;

import java.util.Collection;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.minja.entities.Student;
import com.minja.repositories.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

	@Autowired
	StudentRepository studentRepository;

	public Collection<Student> getStudents() {
		return studentRepository.findAll();
	}

	public Student insert(Student s) {
		System.out.println("save student: " + s);
		if (studentRepository.existsById(s.getId())) {
			throw new RuntimeException("Student sa ovim id-om vec postoji.");
		} else {
			if (s.getId() == 0)
				throw new RuntimeException("Student nema dobar ID.");
			try {
				return studentRepository.save(s);
			} catch (RuntimeException ex) {
				Throwable e = ex.getCause();
				while (e != null) {
					if (e instanceof ConstraintViolationException) {
						ConstraintViolationException cve = (ConstraintViolationException) e;
						Set<ConstraintViolation<?>> errors = cve.getConstraintViolations();
						StringBuilder sb = new StringBuilder();
						for (ConstraintViolation<?> error : errors) {
							sb.append(error.getMessage() + "\n");
						}
						throw new RuntimeException(sb.toString());
					}
					e = e.getCause();
				}
				throw ex;
			}
		}
	}

	public Student update(Student s) {
		System.out.println("update student: " + s);
		try {
			return studentRepository.save(s);
		} catch (RuntimeException ex) {
			Throwable e = ex.getCause();
			while (e != null) {
				if (e instanceof ConstraintViolationException) {
					ConstraintViolationException cve = (ConstraintViolationException) e;
					Set<ConstraintViolation<?>> errors = cve.getConstraintViolations();
					StringBuilder sb = new StringBuilder();
					for (ConstraintViolation<?> error : errors) {
						sb.append(error.getMessage() + "\n");
					}
					throw new RuntimeException(sb.toString());
				}
				e = e.getCause();
			}
			throw ex;
		}
	}

	public boolean delete(long id) {
		Student s = studentRepository.getOne(id);
		studentRepository.delete(s);
		return true;
	}

	public Collection<Student> findByImeLike(String ime) {
		return studentRepository.findByImeStartingWith(ime);
	}

}
