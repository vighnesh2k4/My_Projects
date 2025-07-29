import { useState, useEffect } from 'react';
import axios from 'axios';
import DisplayQuestion from './DisplayQuestion';
import Level from './Level';
import Amount from './Amount';
import '../../App.css';

function DisplayGame() {
  const [level, setLevel] = useState(null);
  const [questions, setQuestions] = useState([]);
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  const [timer, setTimer] = useState(60);
  const [score, setScore] = useState(0);
  const [quizOver, setQuizOver] = useState(false);
  const [showResultPopup, setShowResultPopup] = useState(false);
  const [selectedOption, setSelectedOption] = useState(null);
  const [numOfQuestions, setNumOfQuestions] = useState();
  const [errorMessage, setErrorMessage] = useState(null);
  const [errorMessage2, setErrorMessage2] = useState(null);
  const [optionColor, setOptionColor] = useState(null);
  const [timeAllocated, setTimeAllocated] = useState(null);

  const fetchGameConfigDetails = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/game-config/get-questions-and-timer');
      if (response.data.dataObject.noQuestions === 0 || response.data.dataObject.timeAllocated===0) {
        setErrorMessage2("Please try after sometime.(Game Config Details are Incorrect)")
      } else {
        setNumOfQuestions(response.data.dataObject.noQuestions);
        setTimeAllocated(response.data.dataObject.timeAllocated);
        setTimer(response.data.dataObject.timeAllocated);
      }
    } catch (error) {
      setErrorMessage2('Error while fetching game configuration details.');
      console.error(error);
    }
  };

  const fetchQuestions = async (level, numOfQuestions) => {
    try {
      const response = await axios.get(`http://localhost:8080/api/questions/${level}/${numOfQuestions}`);
      if (response.data === "Questions List is Empty" || (response.data !== "Questions List is Empty" && response.data.length < numOfQuestions)) {
        setErrorMessage2("Not Enough Questions in this level. Please Choose Another Level.");
      }
      else {
        setQuestions(response.data);
      }
    } catch (error) {
      setErrorMessage2('Error while fetching Questions.');
      console.error(error);
    }
  };

  const handleLevel = async (selectedLevel) => {
    setLevel(selectedLevel);
    await fetchGameConfigDetails();
  };

  useEffect(() => {
    if (level && numOfQuestions) {
      fetchQuestions(level, numOfQuestions);
    }
  }, [level, numOfQuestions]);

  const handleNextClick = () => {
    if (!selectedOption) return;

    const currentQuestion = questions[currentQuestionIndex];
    const questionId = currentQuestion.questionId;

    axios.get(`http://localhost:8080/api/questions/checkAnswer/${questionId}/${selectedOption}`)
      .then((response) => {
        const isCorrect = response.data;

        if (isCorrect) {
          setOptionColor('green');

          setTimeout(() => {
            const updatedScore = score + 1;

            if (currentQuestionIndex + 1 >= questions.length) {
              gameEnd(updatedScore);
            } else {
              setScore(updatedScore);
              setCurrentQuestionIndex(prev => prev + 1);
              setSelectedOption(null);
              setOptionColor(null);
              setTimer(timeAllocated);
            }
          }, 1000);

        } else {
          setOptionColor('red');

          setTimeout(() => {
            gameEnd(score);
          }, 1000);
        }
      })
      .catch((error) => {
        setErrorMessage('Error while checking answer.');
        console.error(error);
      });
  };

  const handleExitClick = () => {
    if (!selectedOption) {
      gameEnd(score);
      return;
    }

    const currentQuestion = questions[currentQuestionIndex];
    const questionId = currentQuestion.questionId;

    axios.get(`http://localhost:8080/api/questions/checkAnswer/${questionId}/${selectedOption}`)
      .then((response) => {
        const isCorrect = response.data;
        if (isCorrect) {
          gameEnd(score + 1);
        } else {
          gameEnd(score);
        }
      })
      .catch((error) => {
        setErrorMessage('Error while exiting game.');
        console.error(error);
      });
  };

  const submitGameResult = async (finalScore) => {
    try {
      const response = await axios.get('http://localhost:8080/api/user/user-info');
      const userId = response.data.dataObject.userId;
      let amount;
      if(finalScore>0){
      amount = Math.ceil(10000000 / Math.pow(2, numOfQuestions - finalScore));
      }
      else if(finalScore===0){
        amount = 0;

      }

      const postBody = {
        userId: userId,
        numOfQuestions: numOfQuestions,
        score: finalScore,
        amount :amount

      };

      await axios.post('http://localhost:8080/api/game/add', postBody);
      console.log("Game added to DB successfully.");
    } catch (error) {
      console.log("Error adding game to DB");
    }
  };

  const gameEnd = async (finalScore) => {
    await submitGameResult(finalScore);
    setScore(finalScore);
    setQuizOver(true);
    setShowResultPopup(true);
  };

  useEffect(() => {
    let timerInterval;
    if (level && !quizOver && !errorMessage && !errorMessage2) {
      timerInterval = setInterval(() => {
        setTimer((prev) => {
          if (prev <= 1) {
            clearInterval(timerInterval);
            gameEnd(score);
            return 0;
          }
          return prev - 1;
        });
      }, 1000);
    }
    return () => clearInterval(timerInterval);
  }, [level, quizOver, score, errorMessage, errorMessage2]);


  return (
    <main-div>
      {!level ? (
        <Level handleLevel={handleLevel} />
      ) : (
        <div className="container-grid">
          <display-game-div>
            {questions.length > 0 && !quizOver && (
              <DisplayQuestion
                sno={currentQuestionIndex}
                question={questions[currentQuestionIndex]}
                timer={timer}
                handleOptionSelect={setSelectedOption}
                selectedOption={selectedOption}
                handleNextClick={handleNextClick}
                handleExitClick={handleExitClick}
                optionColor={optionColor}
              />
            )}

            {errorMessage && (
              <div>
                <div className="error-popup">
                  <p>{errorMessage}</p>
                  <button className="close-btn" onClick={() => { setErrorMessage(null); window.location.reload(); }}>Close</button>
                </div>
              </div>
            )}

            {errorMessage2 && (
              <div>
                {questions.length === 0 && <Level handleLevel={handleLevel} />}
                <div className="error-popup">
                  <p>{errorMessage2}</p>
                  <button className="close-btn" onClick={() => { setErrorMessage2(null); window.location.reload(); }}>Close</button>
                </div>
              </div>
            )}

            {showResultPopup && !errorMessage && !errorMessage2 && (
              <div className="result-div">
                {score === numOfQuestions && <h3>Congratulations 🎉, You became Crorepathi!</h3>}
                {((score < numOfQuestions) && score !== 0) && <h3>You won Rs.{Math.ceil(10000000 / Math.pow(2, numOfQuestions - score))}💵<br />Better Luck Next time 👍 </h3>}
                {score === 0 && <h3>Better Luck Next time 👍 </h3>}

                <h3>Quiz Over!</h3>
                <div className="score-div">
                  <p>You Scored: {score}/{numOfQuestions}</p>
                </div>
                <button className="button-m" onClick={() => (window.location.href = "../user/home")}>
                  Back to Home Page
                </button>
              </div>
            )}
          </display-game-div>
          {!showResultPopup && !errorMessage && !errorMessage2 && (
            <Amount
              numOfQuestions={numOfQuestions}
              currentQuestionIndex={currentQuestionIndex}

            />)}

        </div>
      )}
    </main-div>
  );
}

export default DisplayGame;