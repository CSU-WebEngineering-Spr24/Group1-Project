import React from 'react';
import { Form } from 'react-bootstrap';

function shuffleArray(array) {
  let currentIndex = array.length, randomIndex;

  // While there remain elements to shuffle.
  while (currentIndex !== 0) {

    // Pick a remaining element.
    randomIndex = Math.floor(Math.random() * currentIndex);
    currentIndex--;

    // And swap it with the current element.
    [array[currentIndex], array[randomIndex]] = [
      array[randomIndex], array[currentIndex]];
  }

  return array;
}

function QuestionCard({ question, correctAnswer, incorrectAnswers, selected, onSelect }) {
  // Combine and shuffle the options
  console.log("question", question);
  console.log("correctAnswer", correctAnswer);
  console.log("incorrectAnswers", incorrectAnswers);
  
  const options = shuffleArray([correctAnswer, ...incorrectAnswers]);

  return (
    <Form.Group>
      <Form.Label>{question}</Form.Label>
      <Form.Control as="select" value={selected} onChange={e => onSelect(e.target.value)}>
        <option value="">Select an answer</option>
        {options.map((option, index) => (
          <option key={index} value={option}>{option}</option>
        ))}
      </Form.Control>
    </Form.Group>
  );
}

export default QuestionCard;
