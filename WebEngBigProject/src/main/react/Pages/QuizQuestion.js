import React from 'react';
import { Card, Button } from 'react-bootstrap';
import QuestionCard from '../Components/QuestionCard';
import QuestionMenubar from '../Components/QuestionMenubar';

function QuizQuestion({ questions, currentQuestionIndex, answers, onSelect , evaluateAnswers}) {
    
    
  
    return (
    <>
      <QuestionMenubar questions={questions} currentQuestion={currentQuestionIndex} status={[]} />
      <Card className="mt-3">
        <Card.Header>Question {currentQuestionIndex + 1}</Card.Header>
        <Card.Body>
          {questions.length > 0 && (
            <QuestionCard
              question={questions[currentQuestionIndex].question}
              correctAnswer={questions[currentQuestionIndex].correct_answer}
              incorrectAnswers={questions[currentQuestionIndex].incorrect_answers}
              selected={answers[currentQuestionIndex]}
              onSelect={onSelect}
            />
          )}
          <Button variant="primary" onClick={evaluateAnswers} className="mt-3">Evaluate</Button>
        </Card.Body>
      </Card>
    </>
  );
}

export default QuizQuestion;
