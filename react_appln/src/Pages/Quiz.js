import React, { useState } from 'react';
import { Container, Button, Form, Modal } from 'react-bootstrap';
import QuestionCard from '../Components/QuestionCard';
import QuestionMenubar from '../Components/QuestionMenubar';

function Quiz() {
  const [numQuestions, setNumQuestions] = useState(5);
  const [questions, setQuestions] = useState([]);
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  const [answers, setAnswers] = useState({});
  const [showScoreModal, setShowScoreModal] = useState(false);
  const [quizStarted, setQuizStarted] = useState(false);
  const [score, setScore] = useState(0);

  const fetchQuestions = async () => {
    // Fetch questions logic here
    // For example:
    // const response = await fetch(`https://yourapi.com/questions?amount=${numQuestions}`);
    // const data = await response.json();
    // setQuestions(data);
    try {
      const response = await fetch(`https://8080-csuwebengin-group1proje-1c60tug796n.ws-us110.gitpod.io/get?amount=${numQuestions}`);
      const data = await response.json();
      setQuestions(data.results );
      // Initialize answers with empty strings
      // setAnswers(Array(numQuestions).fill(''));
    } catch (error) {
      console.error('Error fetching questions:', error);
    }
    setQuizStarted(true);
  };

  const startQuiz = () => {
    fetchQuestions();
    // reset states if needed
  };

  const handleSelectQuestion = index => {
    setCurrentQuestionIndex(index);
  };

  const handleAnswerSelect = answer => {
    setAnswers({ ...answers, [currentQuestionIndex]: answer });
  };

  const evaluateAnswers = () => {
    // Evaluate answers and set score
    setShowScoreModal(true);
  };

  const resetQuiz = () => {
    setShowScoreModal(false);
    setQuizStarted(false);
    setQuestions([]);
    setAnswers({});
    setCurrentQuestionIndex(0);
    setScore(0);
  };

  return (
    <Container>
      {!quizStarted ? (
        <Form>
          <Form.Group>
            <Form.Label>Number of questions:</Form.Label>
            <Form.Control type="number" value={numQuestions} onChange={e => setNumQuestions(parseInt(e.target.value))} />
          </Form.Group>
          <Button variant="primary" onClick={startQuiz}>Start Quiz</Button>
        </Form>
      ) : (
        <>
          <QuestionMenubar
            questions={questions}
            currentQuestion={currentQuestionIndex}
            onSelect={handleSelectQuestion}
            status={questions.map((_, index) => answers[index] ? 'answered' : 'notvisited')}
          />
          {questions.length > 0 && (
            <QuestionCard
              question={questions[currentQuestionIndex].question}
              options={questions[currentQuestionIndex].options}
              selected={answers[currentQuestionIndex]}
              onSelect={handleAnswerSelect}
            />
          )}
          <Button onClick={evaluateAnswers}>Evaluate</Button>
        </>
      )}

      <Modal show={showScoreModal} onHide={resetQuiz}>
        <Modal.Header closeButton>
          <Modal.Title>Quiz Score</Modal.Title>
        </Modal.Header>
        <Modal.Body>Your score is {score}</Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={resetQuiz}>Close</Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
}

export default Quiz;
