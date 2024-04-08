import React from 'react';
import { ButtonGroup, Button } from 'react-bootstrap';

function QuestionMenubar({ questions, currentQuestion, onSelect, status }) {
  return (
    <ButtonGroup>
      {questions.map((_, index) => (
        <Button 
          key={index} 
          variant={status[index]} 
          onClick={() => onSelect(index)}
        >
          {index + 1}
        </Button>
      ))}
    </ButtonGroup>
  );
}

export default QuestionMenubar;
