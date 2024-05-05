import React, { useState, useEffect, useRef } from 'react';
import { Container, Row, Col, Card, Table, Dropdown } from 'react-bootstrap';
import { AccountCircle } from '@mui/icons-material';
import Chart from 'chart.js/auto';
import axios from 'axios';


  

const Dashboard = () => {
  const [selectedMode, setSelectedMode] = useState("arcade");
  const [scoresData, setScoresData] = useState([]);
  const chartRef = useRef(null);

  useEffect(() => {
    // Fetch scoresData from the /readscores endpoint
    if (scoresData.length <= 0) {
      axios.get('/readscores')
        .then(response => {
          setScoresData(response.data);
        })
        .catch(error => {
          console.error('Error fetching scoresData:', error);
        });
      }
  }, []);
  
  useEffect(() => {
      axios.get('/readscores')
        .then(response => {
          setScoresData(response.data);
        })
        .catch(error => {
          console.error('Error fetching scoresData:', error);
        });
  }, [selectedMode]);

  useEffect(() => {
    if (scoresData.length <= 0) {
    axios.get('/readscores')
      .then(response => {
        setScoresData(response.data);
      })
      .catch(error => {
        console.error('Error fetching scoresData:', error);
      });
    }
    if (chartRef.current !== null && chartRef.current !== undefined) {
      const chartInstance = chartRef.current.chartInstance;
      if (chartInstance !== null && chartInstance !== undefined) {
        chartInstance.destroy();
      }
    }

    if (chartRef.current !== null && chartRef.current !== undefined) {
      const ctx = chartRef.current.getContext('2d');
      const newChartInstance = new Chart(ctx, {
        type: 'bar',
        data: {
          labels: [],
          datasets: [
            {
              label: 'Scores',
              data: [],
              backgroundColor: 'rgba(54, 162, 235, 0.2)',
              borderColor: 'rgba(54, 162, 235, 1)',
              borderWidth: 1,
            },
          ],
        },
        options: {
          scales: {
            y: {
              beginAtZero: true,
            },
          },
        },
      });
      chartRef.current.chartInstance = newChartInstance;
    }
  }, [selectedMode]);

  useEffect(() => {
    if (scoresData.length <= 0) {
      axios.get('/readscores')
        .then(response => {
          setScoresData(response.data);
        })
        .catch(error => {
          console.error('Error fetching scoresData:', error);
        });
      }

    if (!selectedMode) return;

    const selectedModeData = scoresData.find(data => data._mode === selectedMode);
    if (!selectedModeData) return;

    const chartLabels = selectedModeData._results.map(result => result._user);
    const chartData = selectedModeData._results.map(result => result._score);

    if (chartRef.current !== null && chartRef.current !== undefined) {
      const chartInstance = chartRef.current.chartInstance;
      if (chartInstance !== null && chartInstance !== undefined) {
        chartInstance.data.labels = chartLabels;
        chartInstance.data.datasets[0].data = chartData;
        chartInstance.update();
      }
    }
  }, [selectedMode,scoresData]);

  return (
    <Container>
      <h1 className="text-center">Score Dashboard</h1>
      <Row className="justify-content-center mb-3">
        <Col xs={6}>
          <Dropdown onSelect={(mode) => setSelectedMode(mode)}>
            <Dropdown.Toggle variant="primary" id="dropdown-basic">
              Mode : {selectedMode}
            </Dropdown.Toggle>
            <Dropdown.Menu>
              {scoresData.map(mode => (
                <Dropdown.Item key={mode._mode} eventKey={mode._mode} active={selectedMode === mode._mode}>
                  {mode._mode}
                </Dropdown.Item>
              ))}
            </Dropdown.Menu>
          </Dropdown>
        </Col>
      </Row>
      <Row>
        <Col>
          <Card>
            <Card.Body>
              <canvas ref={chartRef} />
            </Card.Body>
          </Card>
        </Col>
        <Col>
          <Card>
            <Card.Header>Scores Table</Card.Header>
            <Card.Body>
              <Table striped bordered hover>
                <thead>
                  <tr>
                    <th>User</th>
                    <th>Score</th>
                  </tr>
                </thead>
                <tbody>
                  {scoresData.find(data => data._mode === selectedMode) && scoresData.find(data => data._mode === selectedMode)._results.map((result, idx) => (
                    <tr key={idx}>
                      <td><AccountCircle /> {result._user}</td>
                      <td>{result._score}</td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default Dashboard;
