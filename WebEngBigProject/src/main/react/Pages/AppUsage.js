import React from 'react';
import { Container, Row, Col, Card } from 'react-bootstrap';

const AppUsage = () => {
  return (
    <Container className="mt-4">
      <Row>
        <Col>
          <Card>
            <Card.Body>
              <Card.Title>API Usage Examples</Card.Title>
              <Card.Text>
                <p>For questions please use '/questions?' followed by 'mode=' (arcade/challenge/quick).</p>
                <p>For fetching facts, please use '/facts?' followed by 'count='(number of facts).</p>
                <p>For fetching scores, please use '/readscores?'.</p>
                <p>For submitting scores, please use '/submit?' followed by 'user='(user name), '&amp;mode='(modename) and '&amp;score='(integer score).</p>
                <p>For resetting scores, please use '/resetscores?'.</p>
                <p>For going to ui, please use '/home'.</p>
              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default AppUsage;
