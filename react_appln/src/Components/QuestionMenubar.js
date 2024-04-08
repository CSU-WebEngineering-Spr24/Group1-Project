import React from 'react';
import { ButtonGroup, Button } from 'react-bootstrap';

function QuestionMenubar({ questions, currentQuestion, onSelect, status }) {
  // Function to determine button variant based on status
  const getButtonVariant = (questionStatus) => {
    switch (questionStatus) {
      case 'answered':
        return 'success'; // Green for answered questions
      case 'visited':
        return 'warning'; // Yellow for visited but not answered questions
      case 'notvisited':
      default:
        return 'secondary'; // Grey for not visited questions
    }
  };

  return (
    <ButtonGroup>
      {questions.map((_, index) => {
        const buttonVariant = getButtonVariant(status[index]);

        return (
          <Button
            key={index}
            variant={buttonVariant}
            onClick={() => onSelect(index)}
            active={index === currentQuestion}
          >
            {index + 1}
          </Button>
        );
      })}
    </ButtonGroup>
  );
}

export default QuestionMenubar;
