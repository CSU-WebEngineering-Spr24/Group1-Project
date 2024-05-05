import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Card, Form, Button } from 'react-bootstrap';
import '../App.css';
import useFactsStore from './factsstore'; 
 

function FormPage() {
  const { facts, count, setFacts, setCount, fetchFacts } = useFactsStore();

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
        {!Array.isArray(facts) && <p>Try fetching facts... NO FACTS FOUND</p>}
        {Array.isArray(facts) && facts.map((fact, index) => (
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
