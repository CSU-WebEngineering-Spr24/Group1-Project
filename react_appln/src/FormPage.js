import logo from './logo.svg';
import './App.css';

function FormPage({name="FormPage"}) {
  return (
    <div >
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Hey Learn React at {name} page
        </a>
      </header>
    </div>
  );
}

export default FormPage;
