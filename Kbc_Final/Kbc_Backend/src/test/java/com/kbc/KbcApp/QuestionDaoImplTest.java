package com.kbc.KbcApp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kbc.KbcApp.enums.LevelEnum;
import com.kbc.KbcApp.enums.StatusEnum;
import com.kbc.KbcApp.pojos.Question;
import com.kbc.KbcApp.service.QuestionService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionDaoImplTest {

    @Autowired
    private QuestionService questionService;

    @Test
    public void testAddQuestion_Success() throws Exception {
        Question question = new Question("Who is Prabas?", "Actor", "Politician", "Dancer", "Singer", LevelEnum.EASY, StatusEnum.ACTIVE, "Ram");
        boolean result = questionService.addQuestion(question,"Ram");
        assertTrue("Question should be added successfully", result);
    }

    @Test
    public void testUpdateQuestion_Success() throws Exception {

        Question updatedQuestion = new Question(1,"Who is Kalyan?", "Hero", "Politician", "Dancer", "Singer", LevelEnum.HARD, StatusEnum.ACTIVE,LocalDateTime.now(),"Ram");
        boolean updated = questionService.updateQuestion(updatedQuestion,"Ram");
        assertTrue("Question should be updated successfully", updated);

        List<Question> fetched = questionService.showAllQuestions(1,null,null);
        assertEquals("Hero", fetched.get(0).getOption1());
    }


    @Test
    public void testGetQuestion_Success() throws Exception {

        List<Question> fetched = questionService.showAllQuestions(1,null,null);
        assertNotNull("Fetched question should not be null", fetched);
        assertEquals("Who is Kalyan?", fetched.get(0).getQuestion());
    }
}