import React from 'react';
import { Modal, ListGroup, Button } from 'react-bootstrap';

function QuizScoreModal({ show, onClose, questions, answers, reviewAnswers, score, evaluateAnswers }) {
    
    return (
    <Modal show={show} onHide={onClose} size="lg">
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
        <Button variant="secondary" onClick={reviewAnswers ? evaluateAnswers : onClose}>
          {reviewAnswers ? "Submit" : "Close"}
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default QuizScoreModal;
