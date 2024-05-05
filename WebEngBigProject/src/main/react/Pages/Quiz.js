import React, { useState, useEffect } from 'react';
import { Container, Button, Form, Modal, Card, ListGroup } from 'react-bootstrap';
import QuestionCard from '../Components/QuestionCard';
import QuestionMenubar from '../Components/QuestionMenubar';

function Quiz() {
  const [numQuestions, setNumQuestions] = useState(5);
  const [mode, setMode] = useState('arcade');
  const [questions, setQuestions] = useState([]);
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  const [answers, setAnswers] = useState({});
  const [visited, setVisited] = useState({});
  const [showScoreModal, setShowScoreModal] = useState(false);
  const [reviewAnswers, setReviewAnswers] = useState(true);
  const [quizStarted, setQuizStarted] = useState(false);
  const [score, setScore] = useState(0);
  const [timer, setTimer] = useState(null);
  const [timeLeft, setTimeLeft] = useState(600); // 10 seconds
  const [showAnswer, setshowAnswer] = useState(false);
  // health
  const [health, setHealth] = useState(100);


  const fetchQuestions = async () => {
    try {
      setshowAnswer(false);
      let response = null;
      let data = { 'status': 200 };
      while ('status' in data) {
        if (mode === 'freeplay' || mode === 'survival') {
          response = await fetch('/questions?amount=1');
        }
        else {
          response = await fetch(`/questions?mode=${mode}`);
        }
        data = await response.json();
      }
      setQuestions(data);
      console.log(questions)
    } catch (error) {
      console.error('Error fetching questions:', error);
    }
    setQuizStarted(true);
  };

  useEffect(() => {
    if (mode === 'timed') {
      setTimer(setInterval(() => {
        setTimeLeft(prevTime => prevTime - 1);
      }, 1000));
    }

    return () => {
      clearInterval(timer);
    };
  }, [mode]);

  useEffect(() => {
    if (timeLeft === 0) {
      evaluateAnswers();
    }
  }, [timeLeft]);

  const startQuiz = () => {
    if (mode === "arcade") {
      setNumQuestions(5);
    } else if (mode === "timed") {
      setNumQuestions(10);
    } else if (mode === "quick") {
      setNumQuestions(5);
    } else if (mode === "freeplay") {
      setNumQuestions(1);
    } else if (mode === "marathon") {
      setNumQuestions(50);
    }
    fetchQuestions();
    setVisited({});
  };

  const handleSelectQuestion = index => {
    setCurrentQuestionIndex(index);
    setVisited({ ...visited, [index]: true });
  };

  const handleAnswerSelect = answer => {
    setAnswers({ ...answers, [currentQuestionIndex]: answer });
    if (mode === 'freeplay' || mode === 'survival') {
      setshowAnswer(true);
      if (answer === questions[currentQuestionIndex].correct_answer) {
        setScore(prevScore => prevScore + 1);
      }
      else{
        if (mode === 'survival') {
          if (health- 10 <= 0) {
            alert("You have died. Your score is " + score + ". your answer is " + answer + ". The correct answer is " + questions[currentQuestionIndex].correct_answer);
            evaluateAnswers();
          }
          setHealth(prevHealth => prevHealth - 10);
        }
      }
    }
  };

  const evaluateAnswers = () => {
    if (mode === 'freeplay' || mode === 'survival') {
      resetQuiz();
      return;
    }
    if (reviewAnswers) {
      const calculatedScore = questions.reduce((acc, question, index) => {
        return acc + (answers[index] === question.correct_answer ? 1 : 0);
      }, 0);
      setScore(calculatedScore);
    }
    setReviewAnswers(!reviewAnswers);
    setShowScoreModal(true);
  };

  const resetQuiz = () => {
    setshowAnswer(false);
    setShowScoreModal(false);
    setReviewAnswers(true);
    setQuizStarted(false);
    setQuestions([]);
    setAnswers({});
    setVisited({});
    setCurrentQuestionIndex(0);
    setScore(0);
    setTimeLeft(600); // Reset timer to 10 minutes
    setMode('arcade');
    setHealth(100);
  };

  const getStatus = index => {
    if (answers[index] != null) return 'answered';
    if (visited[index]) return 'visited';
    return 'notvisited';
  };

  const formatTime = () => {
    const minutes = Math.floor(timeLeft / 60);
    const seconds = timeLeft % 60;
    return `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;
  };

  const nextQuestion = () => {
    if (mode === 'freeplay' || mode === 'survival') {
      console.log('freeplay');
      fetchQuestions();
      console.log(questions);
    } else {
      console.log('not freeplay');
      console.log(questions);
      if (currentQuestionIndex < numQuestions - 1) {
        setCurrentQuestionIndex(prevIndex => prevIndex + 1);
      }
    }
  };

  return (
    <Container className="mt-4" style={{ maxWidth: '80vw' }}>
      {!quizStarted ? (
        <Card className="text-center mt-5">
          <Card.Header>Start the Quiz</Card.Header>
          <Card.Body>
            <Form>
              <Form.Group>
                <Form.Label>Select Mode</Form.Label>
                <Form.Control as="select" value={mode} onChange={e => setMode(e.target.value)}>
                  <option value="arcade">Arcade</option>
                  <option value="timed">Timed</option>
                  <option value="quick">Quick</option>
                  <option value="marathon">Marathon</option>
                  <option value="freeplay">Freeplay</option>
                  <option value="survival">Survival</option>
                </Form.Control>
              </Form.Group>
              <Button variant="primary" onClick={startQuiz}>Start Quiz</Button>
            </Form>
          </Card.Body>
        </Card>
      ) : (
        <>
          <h1>Quiz Mode : {mode}</h1>

          {mode !== 'freeplay' && (
            <QuestionMenubar
              questions={questions}
              currentQuestion={currentQuestionIndex}
              onSelect={handleSelectQuestion}
              status={questions.map((_, index) => getStatus(index))}
            />
          )}

          <Card className="mt-3">
            <Card.Header>
              Question {currentQuestionIndex + 1}
              {mode === 'timed' && (
                <span className="float-right">Time Left: {formatTime()}</span>
              )}
              {mode === 'survival' && (
                <span className="float-right">Health: {health}</span>
              )}
              {(mode === 'freeplay' || mode === 'survival') && (
                <span className="float-right">Score: {score}</span>
              )}
            </Card.Header>
            <Card.Body>
              {console.log(questions)}
              {questions.length > 0 && (
                <QuestionCard
                  question={questions[currentQuestionIndex].question}
                  correctAnswer={questions[currentQuestionIndex].correct_answer}
                  incorrectAnswers={questions[currentQuestionIndex].incorrect_answers}
                  selected={answers[currentQuestionIndex]}
                  onSelect={handleAnswerSelect}
                  showAnswer={showAnswer}
                />
              )}
              {showAnswer && (
                <div className={(answers[currentQuestionIndex] === questions[currentQuestionIndex].correct_answer) ? "text-success" : "text-danger"}>
                  <div className="text-center">Your answer: {answers[currentQuestionIndex]}</div>
                  <div className="text-center">Correct answer: {questions[currentQuestionIndex].correct_answer}</div>
                </div>
              )}
              {mode === 'freeplay' && (
                <>
                  <Button variant="primary" onClick={nextQuestion} className="mt-3">Next Question</Button>
                  <Button variant="primary" onClick={evaluateAnswers} className="mt-3">Quit</Button>
                </>
              )}
              {mode !== 'freeplay' && (
                <>
                  <Button variant="primary" onClick={nextQuestion} className="mt-3">Next Question</Button>
                  <Button variant="primary" onClick={evaluateAnswers} className="mt-3">Evaluate</Button>
                </>
              )}

            </Card.Body>
          </Card>
        </>
      )}

      <Modal show={showScoreModal} onHide={resetQuiz} size="lg">
        <Modal.Header closeButton>
          <Modal.Title>{reviewAnswers ? "Review Your Answers" : "Quiz Score"}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {reviewAnswers ? (
            <ListGroup>
              {questions.map((question, index) => (
                <ListGroup.Item key={index}>
                  Q{index + 1}: {question.question}
                  <br />
                  Your answer: {answers[index] || "No answer selected"}
                </ListGroup.Item>
              ))}
            </ListGroup>
          ) : (
            <>
              {questions.map((question, index) => (
                <ListGroup.Item key={index} variant={answers[index] === question.correct_answer ? "success" : "danger"}>
                  Q{index + 1}: {question.question}
                  <br />
                  Your answer: {answers[index] || "No answer selected"}
                  <br />
                  Correct answer: {question.correct_answer}
                </ListGroup.Item>
              ))}
              <p>Your score is {score} out of {questions.length}</p>
            </>
          )}
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={reviewAnswers ? evaluateAnswers : resetQuiz}>
            {reviewAnswers ? "Submit" : "Close"}
          </Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
}

export default Quiz;
