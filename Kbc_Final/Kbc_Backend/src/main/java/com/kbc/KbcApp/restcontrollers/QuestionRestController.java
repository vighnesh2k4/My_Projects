package com.kbc.KbcApp.restcontrollers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kbc.KbcApp.handler.KbcException;
import com.kbc.KbcApp.pojos.Question;
import com.kbc.KbcApp.pojos.ResponseObject;
import com.kbc.KbcApp.service.QuestionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/questions")
public class QuestionRestController {
	

	@Autowired
	private QuestionService questionService;

	@PostMapping("/addQuestion")
	public ResponseObject addQuestion(@Valid @RequestBody Question question, BindingResult bindingResult, HttpSession session)
			throws KbcException {
		String name=(String) session.getAttribute("userName");
		String userId=(String) session.getAttribute("userId").toString();
		ResponseObject res=new ResponseObject();
		if (bindingResult.hasErrors()) {
			res.setStatus(ResponseObject.Status.FAILURE);
			res.setMessage("Error in quetions fields");
			//String question1=question.toString();
			res.setDataObject(question);
			log.warn(name +" with ID "+userId+" found error with the field names while adding");
			return res;
			
			//ResponseEntity.status(HttpStatus.CONFLICT).body(res);
			//return ResponseEntity.status(HttpStatus.CONFLICT).body("Invalid Fields");
		}
		boolean result = questionService.addQuestion(question,name);
		if (result) {
			log.info(name+" with ID "+userId+" added Question Successfully");
			res.setStatus(ResponseObject.Status.SUCCESS);
			res.setMessage("Added Question Successfully");
			res.setDataObject(res);
			return res;
			
			//ResponseEntity.status(HttpStatus.OK).body(res);
			//return ResponseEntity.ok("Question added successfully.");
		} else {
			log.error(name+" with ID "+userId+" got an error while adding");
			res.setStatus(ResponseObject.Status.FAILURE);
			res.setMessage("Failed to ad Question");
			res.setDataObject(question);
			return res;
			
			//ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
			//return ResponseEntity.status(HttpStatus.CONFLICT).body("Failed to add question.");
		}
	}

	@PutMapping("/updateQuestion")
	public ResponseObject updateQuestion(@Valid @RequestBody Question question, BindingResult bindingResult, HttpSession session)
			throws KbcException {
		String name=(String) session.getAttribute("userName");
		String userId=(String) session.getAttribute("userId").toString();
		ResponseObject res=new ResponseObject();
		if (bindingResult.hasErrors()) {
			log.warn(name+" with ID "+userId+" has error with the field names");
			res.setStatus(ResponseObject.Status.FAILURE);
			res.setMessage("Error in quetions fields");
			res.setDataObject(question);
			return res;
			//ResponseEntity.status(HttpStatus.CONFLICT).body(res);

			//return ResponseEntity.status(HttpStatus.CONFLICT).body("Invalid Fields");
		}
		log.warn(name+" with ID "+userId+" Entered UpdateQuestion");
		boolean updated = questionService.updateQuestion(question,name );
		if (updated) {
			log.info(name+" with ID "+userId+" Updated Question Successfully");
			res.setStatus(ResponseObject.Status.SUCCESS);
			res.setMessage("Updated Question Successfully");
			res.setDataObject(question);
			return res;
			//ResponseEntity.status(HttpStatus.OK).body(res);

			//return ResponseEntity.ok("Question updated successfully.");
		} else {
			log.error(name+" with ID "+userId+" got an error while updatinging");
			res.setStatus(ResponseObject.Status.FAILURE);
			res.setMessage("Failed to ad Question");
			res.setDataObject(question);
			return res;
					//ResponseEntity.status(HttpStatus.CONFLICT).body(res);
			//return ResponseEntity.status(HttpStatus.CONFLICT).body("Failed to update question.");
		}
	}

	
	@GetMapping("/checkAnswer/{id}/{selectedAnswer}")
	public ResponseEntity<Boolean> checkAnswer(@PathVariable String selectedAnswer, @PathVariable int id, HttpSession session) {
		String name=(String) session.getAttribute("userName");
		String userId=(String) session.getAttribute("userId").toString();
		List<Question> list = questionService.showAllQuestions(id, null, null);
		if (list.get(0).getOption1().equals(selectedAnswer)) {
			log.info(name+"with "+userId+" sent that the answer is correct");
			return ResponseEntity.status(HttpStatus.OK).body(true);
		}
		log.error(name+"with "+userId+" sent that the answer is false");
		return ResponseEntity.ok(false);
	}
	
	@GetMapping("/{level}/{numOfQuestions}")
	public ResponseEntity<?> getQuestions(@PathVariable String level, @PathVariable int numOfQuestions) {
		if(level.equalsIgnoreCase("combination")) {
			int minQns=numOfQuestions/3;
			int easyQns=minQns;
			int mediumQns=minQns;
			int hardQns=minQns;
			int rem=numOfQuestions%3;
			if(rem == 1) {
				hardQns++;
			}else if(rem==2) {
				mediumQns++;
				hardQns++;
			}
			List<Question> easyList = questionService.showAllQuestions(null, "ACTIVE", "easy");
			List<Question> mediumList = questionService.showAllQuestions(null, "ACTIVE", "medium");
			List<Question> hardList = questionService.showAllQuestions(null, "ACTIVE", "hard");
			if (easyList.size() == 0 || mediumList.size() == 0 || hardList.size() == 0) {
				return ResponseEntity.status(HttpStatus.OK).body("Questions List is Empty");
			}
			List<Map<String,String>> easyShuffled=questionService.shuffle(easyList, easyQns);
			List<Map<String,String>> mediumShuffled=questionService.shuffle(mediumList, mediumQns);
			List<Map<String,String>> hardShuffled=questionService.shuffle(hardList, hardQns);
			List<Map<String,String>> combinationList=new ArrayList<>();
			combinationList.addAll(easyShuffled);
			combinationList.addAll(mediumShuffled);
			combinationList.addAll(hardShuffled);
			return ResponseEntity.ok(combinationList);
		}else {
			List<Question> list = questionService.showAllQuestions(null, "ACTIVE", level.toLowerCase());
			if (list.size() == 0) {
				return ResponseEntity.status(HttpStatus.OK).body("Questions List is Empty");
			}
			List<Map<String,String>> list1=questionService.shuffle(list, numOfQuestions);
			return ResponseEntity.ok(list1);
		}
	}

	@GetMapping("/getAllQuestions")
	public ResponseObject getAllQuestions(HttpSession session) {
		ResponseObject res=new ResponseObject();
		String name=(String) session.getAttribute("userName");
		String userId="";
		try{
			userId=(String) session.getAttribute("userId").toString();
		}catch(Exception e) {
			
		}
		log.info(name+"("+userId+") - trying to fetch the questions");
		List<Question> list = questionService.showAllQuestions(null, null, null);
		if (list.size() == 0) {
			log.warn(name+" ("+userId+") -  come to know that there are no questions in the table");
			res.setStatus(ResponseObject.Status.SUCCESS);
			res.setMessage("Question List is Empty");
			res.setDataObject(null);
			return res;
			//ResponseEntity.status(HttpStatus.OK).body(res);
//			return ResponseEntity.status(HttpStatus.OK).body("Questions List is Empty");
		}
		log.info(name+" ("+userId+") - fetched all the questions");
		res.setStatus(ResponseObject.Status.SUCCESS);
		res.setMessage("Successfully Fetched");
		res.setDataObject(list);
		return res;
		//ResponseEntity.status(HttpStatus.OK).body(res);
//		return ResponseEntity.ok(list);
	}

	@GetMapping("/filter/{status}")
	public ResponseEntity<?> getQuestionByParameter(@PathVariable String status) {
		List<Question> list;
		if (status.toUpperCase().equals("ACTIVE") || status.toUpperCase().equals("INACTIVE")) {
			list = questionService.showAllQuestions(null, status.toUpperCase(), null);
		} else {
			list = questionService.showAllQuestions(null, null, status.toUpperCase());
		}
		return ResponseEntity.ok(list);
	}

	@GetMapping("/filter/{status}/{level}")
	public ResponseEntity<?> getQuestionByParameters(@PathVariable String status, @PathVariable String level) {
		List<Question> list = questionService.showAllQuestions(null, status.toUpperCase(), level.toUpperCase());
		return ResponseEntity.ok(list);
	}

}