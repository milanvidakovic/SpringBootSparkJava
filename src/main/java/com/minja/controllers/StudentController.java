package com.minja.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minja.entities.Student;
import com.minja.services.StudentService;
import com.minja.spark.ISpark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;

@Component
@Order(value = 1)
public class StudentController implements ISpark {

	@Autowired
	StudentService studentService;

	@Autowired
	ObjectMapper om;

	private String toJson(Object o) throws JsonProcessingException {
		return om.writeValueAsString(o);
	}

	private Student fromJson(String s) throws JsonMappingException, JsonProcessingException {
		return om.readValue(s, Student.class);
	}

	@Override
	public void register() {
		get("/test", (req, res) -> {
			return "OK";
		});
		get("/rest/student/getall", (req, res) -> {
			return toJson(studentService.getStudents());
		});
		post("/rest/student/insert", (req, res) -> {
			return toJson(studentService.insert(fromJson(req.body())));
		});
		put("/rest/student/update", (req, res) -> {
			return toJson(studentService.update(fromJson(req.body())));
		});
		delete("/rest/student/delete/:id", (req, res) -> {
			return toJson(studentService.delete(Long.parseLong(req.params("id"))));
		});
	}
	/*
	 * @RequestMapping(value = "/rest/student/getByIme/{ime}", method =
	 * RequestMethod.GET) public Collection<Student> getByIme(@PathVariable String
	 * ime) throws IOException { return studentService.findByImeLike(ime); }
	 */

}
