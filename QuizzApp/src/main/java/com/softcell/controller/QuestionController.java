package com.softcell.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.softcell.model.Question;
import com.softcell.service.QuestionService;

@RestController
@RequestMapping("question")
public class QuestionController {

	@Autowired
	QuestionService questionService;

	@GetMapping("allQuestions")
	public ResponseEntity<List<Question>> getAllQuestion() {
		// return "Hi these are your questions";
		return questionService.getAllQuestions();
	}

	@GetMapping("category/{category}")
	public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String category) {
		return questionService.getQuestionsByCategory(category);
	}

	@PostMapping("add")
	public ResponseEntity<String> addQuestion(@RequestBody Question question) {
		return questionService.addQuestion(question);
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<String> deleteById(@PathVariable int id) {
		return questionService.deleteById(id);
	}

	@DeleteMapping("deleteAll")
	public ResponseEntity<String> deleteAllData() {
		return questionService.deleteAllData();
	}

	@PutMapping("update/{id}")
	public ResponseEntity<Question> updatebyId(@PathVariable int id, @RequestBody Question question) {
		return questionService.updateById(id, question);
	}
}
