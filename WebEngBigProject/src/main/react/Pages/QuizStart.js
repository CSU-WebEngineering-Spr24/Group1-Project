import React from 'react';
import { Card, Button } from 'react-bootstrap';

function QuizStart({ startQuiz }) {
  return (
    <div className="d-flex justify-content-center mt-5">
      <Card className="m-2" style={{ width: '18rem' }} onClick={() => { startQuiz('marathon'); }}>
        <Card.Body>
          <Card.Title>Marathon</Card.Title>
          <Card.Text>
            Click here to start the marathon mode.
          </Card.Text>
          <Button variant="primary">Start</Button>
        </Card.Body>
      </Card>
      <Card className="m-2" style={{ width: '18rem' }} onClick={() => { startQuiz('arcade'); }}>
        <Card.Body>
          <Card.Title>Arcade</Card.Title>
          <Card.Text>
            Click here to start the arcade mode.
          </Card.Text>
          <Button variant="primary">Start</Button>
        </Card.Body>
      </Card>
      <Card className="m-2" style={{ width: '18rem' }} onClick={() => { startQuiz('challenge'); }}>
        <Card.Body>
          <Card.Title>Challenge</Card.Title>
          <Card.Text>
            Click here to start the challenge mode.
          </Card.Text>
          <Button variant="primary">Start</Button>
        </Card.Body>
      </Card>
      <Card className="m-2" style={{ width: '18rem' }} onClick={() => {  startQuiz('quick');}}>
        <Card.Body>
          <Card.Title>Quick</Card.Title>
          <Card.Text>
            Click here to start the quick mode.
          </Card.Text>
          <Button variant="primary">Start</Button>
        </Card.Body>
      </Card>
    </div>
  );
}

export default QuizStart;
