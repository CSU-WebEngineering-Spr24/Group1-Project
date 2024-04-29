import logo from '../logo.svg';
import React from 'react'

import '../App.css';
import { Card, Row, Col } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

function Home({name="home"}) {
  const navigate = useNavigate();

  const cardInfo = [
    { path: "/form", title: "About Page", imageSrc: "https://media.proprofs.com/images/QM/user_images/2503852/New%20Project%20(63)(189).jpg" },
    { path: "/usage", title: "App Usage Page", imageSrc: "https://i.kinja-img.com/image/upload/c_fill,h_675,pg_1,q_80,w_1200/d2ofne7vqbinighpx3g7.jpg" },
    { path: "/quiz", title: "Quiz", imageSrc: "https://castingoutfear.org/wp-content/uploads/2024/01/Blog-Cover-Guidlines-707x409-1.png" },
    { path: "/facts", title: "facts", imageSrc: "https://t3.ftcdn.net/jpg/04/72/13/30/360_F_472133047_NoPjDxohSxhNZe62UkVtua9UK28jJecp.jpg" }
  ];
  return (
    <div >
      <header className="App-header">
      <Row xs={1} md={2} className="g-4">
      {cardInfo.map((card, idx) => (
        <Col key={idx} onClick={() => navigate(card.path)}>
          <Card>
            <Card.Img variant="top" src={card.imageSrc}  style={{maxHeight: "170px"}}/>
            <Card.Body>
              <Card.Title>{card.title}</Card.Title>
            </Card.Body>
          </Card>
        </Col>
      ))}
    </Row>
      </header>
    </div>
  );
}

export default Home;
