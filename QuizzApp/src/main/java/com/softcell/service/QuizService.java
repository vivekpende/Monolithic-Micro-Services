package com.softcell.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.softcell.dao.QuestionDao;
import com.softcell.dao.QuizDao;
import com.softcell.exception.ResourceNotFoundException;
import com.softcell.model.Question;
import com.softcell.model.QuestionWrapper;
import com.softcell.model.Quiz;
import com.softcell.model.Response;

@Service
public class QuizService {

	@Autowired
	QuizDao quizDao;
	@Autowired
	QuestionDao questionDao;
	
	public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
		
		List<Question> que= questionDao.findQuestionsByCategory(category, numQ);
		
		
		Quiz quiz = new Quiz();
		quiz.setTitle(title);
		quiz.setQuestions(que);
		quizDao.save(quiz);
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
		Optional<Quiz> quizque= quizDao.findById(id);
		List<Question> quiz = quizque.get().getQuestions();
		List<QuestionWrapper> questions = new ArrayList<>();
		
		for(Question q : quiz) {
			QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getOption1(), q.getOption2(), q.getOption3(),q.getOption4(), q.getQuestionTitle());
			questions.add(qw);
		}
		
		return new ResponseEntity<>(questions, HttpStatus.OK);
	}

//	public ResponseEntity<Integer> calculateResult(Integer id, List<Response> response) {
//		Quiz quiz = quizDao.findById(id).get();
//		List<Question> questions = quiz.getQuestions();
//		int right = 0, inital = 0;
//		for(Response res : response) {
//			if(res.getResponse().equals(questions.get(inital).getRightAnswer()))
//				right++;
//				
//			inital++;
//		}		
//		return new ResponseEntity<>(right, HttpStatus.OK);
//	}
	
	public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
	    // Retrieve quiz or throw an exception if not found
	    Quiz quiz = quizDao.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Quiz not found for ID: " + id));
	    
	    List<Question> questions = quiz.getQuestions();

	    // Validate that questions and responses are not null or empty
	    if (questions == null || questions.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }

	    if (responses == null || responses.isEmpty()) {
	        return new ResponseEntity<>(0, HttpStatus.OK);
	    }

	    // Calculate the number of correct answers
	    int correctAnswers = 0, initial = 0;
	    
	    for(Response res : responses) {
	    	for(Question que : questions) {
	    		if(res.getResponse().equalsIgnoreCase(que.getRightAnswer()) && res.getId() == que.getId()) {
	    			correctAnswers++;
	    			break;
	    		}
	    	}
//	    	if(res.getResponse().equals(questions.get(initial).getRightAnswer()))
//	    		correctAnswers++;
	    		
	   // 	initial++;
	    }

//	    for (int i = 0; i < Math.min(questions.size(), responses.size()); i++) {
//	        if (responses.get(i).getResponse().equalsIgnoreCase(questions.get(i).getRightAnswer())) {
//	            correctAnswers++;
//	        }
//	    }

	    // Return the number of correct answers
	    return new ResponseEntity<>(correctAnswers, HttpStatus.OK);
	}

//	Math.min(questions.size(), responses.size())
//	The purpose of using Math.min(questions.size(), responses.size()) is to ensure that the loop iterates only up to the number of 
//	elements in the smaller list, preventing an IndexOutOfBoundsException.
//	By using Math.min(), you ensure that the loop runs only for the number of elements that both lists can safely support, 
//	avoiding errors and ensuring correct comparisons.
}
