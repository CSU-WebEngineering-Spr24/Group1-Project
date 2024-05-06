import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import QuestionCard from './QuestionCard';

describe('QuestionCard Component', () => {
  const question = 'What is the capital of France?';
  const correctAnswer = 'Paris';
  const incorrectAnswers = ['London', 'Rome', 'Berlin'];

  it('renders the question', () => {
    render(<QuestionCard question={question} correctAnswer={correctAnswer} incorrectAnswers={incorrectAnswers} />);
    expect(screen.getByText(question)).toBeInTheDocument();
  });

  it('renders all options', () => {
    render(<QuestionCard question={question} correctAnswer={correctAnswer} incorrectAnswers={incorrectAnswers} />);
    incorrectAnswers.forEach(answer => {
      expect(screen.getByText(answer)).toBeInTheDocument();
    });
    expect(screen.getByText(correctAnswer)).toBeInTheDocument();
  });

  it('calls onSelect with the correct value when an option is selected', () => {
    const onSelectMock = jest.fn();
    render(<QuestionCard question={question} correctAnswer={correctAnswer} incorrectAnswers={incorrectAnswers} onSelect={onSelectMock} />);
    
    const select = screen.getByLabelText(question);
    fireEvent.change(select, { target: { value: correctAnswer } });
    
    expect(onSelectMock).toHaveBeenCalledWith(correctAnswer);
  });

  // Add more tests as needed for different scenarios and interactions
});

