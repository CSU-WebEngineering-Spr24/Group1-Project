import React from 'react';
import { Form } from 'react-bootstrap';

function QuestionCard({ question, options, selected, onSelect }) {
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
