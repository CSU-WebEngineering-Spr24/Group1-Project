import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Card, Form, Button } from 'react-bootstrap';
import '../App.css';

function FormPage() {
  const [facts, setFacts] = useState([]);
  const [count, setCount] = useState(5);

  // useEffect(() => {
  //   fetchFacts();
  // }, [count]);

  const fetchFacts = () => {
    fetch(`/facts?count=${count}`)
      .then(response => response.json())
      .then(data => setFacts(data))
      .catch(error => console.error('Error fetching facts:', error));
  };

  const handleCountChange = (e) => {
    setCount(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    fetchFacts();
  };

  return (
    <Container className="mt-4">
      <Row>
        <Col md={12}>
          <Form onSubmit={handleSubmit}>
            <Form.Group controlId="factCount">
              <Form.Label>Number of Facts</Form.Label>
              <Form.Control type="number" value={count} onChange={handleCountChange} min="1" max="10" />
            </Form.Group>
            <Button variant="primary" type="submit">
              Fetch Facts
            </Button>
          </Form>
        </Col>
      </Row>
      <Row className="mt-3">
        {facts.map((fact, index) => (
          <Col md={4} key={index} className="mb-4">
            <Card>
              <Card.Body>
                <Card.Text>
                  {fact.fact}
                </Card.Text>
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>
    </Container>
  );
}

export default FormPage;
