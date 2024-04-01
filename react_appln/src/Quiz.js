import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Form, Button } from 'react-bootstrap';
import './App.css';

function Quiz() {
  const [numQuestions, setNumQuestions] = useState(5);
  const [questions, setQuestions] = useState([]);
  const [answers, setAnswers] = useState([]);

  // Fetch questions from the API
  useEffect(() => {
    async function fetchQuestions() {
      try {
        const response = await fetch(`https://8080-csuwebengin-group1proje-1c60tug796n.ws-us110.gitpod.io/get?amount=${numQuestions}`);
        const data = await response.json();
        setQuestions(data.results );
        // Initialize answers with empty strings
        // setAnswers(Array(numQuestions).fill(''));
      } catch (error) {
        console.error('Error fetching questions:', error);
      }
    }

    fetchQuestions();
  }, [numQuestions]);

  // Handle user selection of answers
  const handleAnswerChange = (index, e) => {
    const newAnswers = [...answers];
    newAnswers[index] = e.target.value;
    setAnswers(newAnswers);
  };

  // Render questions and options
  const renderQuestions = () => {
    // Check if questions is not an array or is empty
    if (!Array.isArray(questions) || questions.length === 0) {
      return <p>No questions available.</p>;
    }
  
    return questions.map((question, index) => (
      <Form.Group key={index}>
        <Form.Label>{question.question}</Form.Label>
        <Form.Control as="select" value={answers[index]} onChange={(e) => handleAnswerChange(index, e)}>
          <option value="">Select an answer</option>
          {[...question.incorrect_answers, question.correct_answer].map((option, optionIndex) => (
            <option key={optionIndex} value={option}>{option}</option>
          ))}
        </Form.Control>
      </Form.Group>
    ));
  };
  

  // Handle evaluation of user's answers
  const evaluateAnswers = () => {
    // Implement your evaluation logic here
    alert('User answers:', answers);
    // You can compare user answers with correct answers for evaluation
  };

  return (
    <Container className="App">
      <h1>Quiz</h1>
      <Form>
        <Form.Group>
          <Form.Label>Number of questions:</Form.Label>
          <Form.Control type="number" value={numQuestions} onChange={(e) => setNumQuestions(parseInt(e.target.value))} />
        </Form.Group>
        {renderQuestions()}
        <Button variant="primary" onClick={evaluateAnswers}>Evaluate</Button>
      </Form>
    </Container>
  );
}

export default Quiz;
