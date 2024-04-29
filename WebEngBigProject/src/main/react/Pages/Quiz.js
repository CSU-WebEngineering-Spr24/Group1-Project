import React, { useState } from 'react';
import { Container, Button, Form, Modal, Card, ListGroup } from 'react-bootstrap';
import QuestionCard from '../Components/QuestionCard';
import QuestionMenubar from '../Components/QuestionMenubar';

function Quiz() {
  const [numQuestions, setNumQuestions] = useState(5);
  const [questions, setQuestions] = useState([]);
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  const [answers, setAnswers] = useState({});
  const [visited, setVisited] = useState({});
  const [showScoreModal, setShowScoreModal] = useState(false);
  const [reviewAnswers, setReviewAnswers] = useState(true);
  const [quizStarted, setQuizStarted] = useState(false);
  const [score, setScore] = useState(0);

  const fetchQuestions = async () => {
    try {
      const response = await fetch(`/questions`);
      const data = await response.json();
      setQuestions(data);
      console.log(questions)
    } catch (error) {
      console.error('Error fetching questions:', error);
    }
    setQuizStarted(true);
  };

  const startQuiz = () => {
    fetchQuestions();
    setVisited({});
  };

  const handleSelectQuestion = index => {
    setCurrentQuestionIndex(index);
    setVisited({ ...visited, [index]: true });
  };

  const handleAnswerSelect = answer => {
    setAnswers({ ...answers, [currentQuestionIndex]: answer });
  };

  const evaluateAnswers = () => {
    if (reviewAnswers) {
      // Calculate score
      const calculatedScore = questions.reduce((acc, question, index) => {
        return acc + (answers[index] === question.correct_answer ? 1 : 0);
      }, 0);
      setScore(calculatedScore);
    }
    setReviewAnswers(!reviewAnswers);
    setShowScoreModal(true);
  };

  const resetQuiz = () => {
    setShowScoreModal(false);
    setReviewAnswers(true);
    setQuizStarted(false);
    setQuestions([]);
    setAnswers({});
    setVisited({});
    setCurrentQuestionIndex(0);
    setScore(0);
  };

  const getStatus = (index) => {
    if (answers[index] != null) return 'answered';
    if (visited[index]) return 'visited';
    return 'notvisited';
  };

  return (
    <Container>
      {!quizStarted ? (
        <Card className="text-center mt-5">
          <Card.Header>Start the Quiz</Card.Header>
          <Card.Body>
            <Form>
              <Form.Group>
                <Form.Label>Number of questions:</Form.Label>
                <Form.Control type="number" value={numQuestions} onChange={e => setNumQuestions(parseInt(e.target.value))} />
              </Form.Group>
              <Button variant="primary" onClick={startQuiz}>Start Quiz</Button>
            </Form>
          </Card.Body>
        </Card>
      ) : (
        <>
          <QuestionMenubar
            questions={questions}
            currentQuestion={currentQuestionIndex}
            onSelect={handleSelectQuestion}
            status={questions.map((_, index) => getStatus(index))}
          />
          <Card className="mt-3">
            <Card.Header>Question {currentQuestionIndex + 1}</Card.Header>
            <Card.Body>
              {questions.length > 0 && (
                <QuestionCard
                  question={questions[currentQuestionIndex].question}
                  correctAnswer={questions[currentQuestionIndex].correct_answer}
                  incorrectAnswers={questions[currentQuestionIndex].incorrect_answers}
                  selected={answers[currentQuestionIndex]}
                  onSelect={handleAnswerSelect}
                />
              )}
              <Button variant="primary" onClick={evaluateAnswers} className="mt-3">Evaluate</Button>
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
