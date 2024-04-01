import logo from './logo.svg';
import './App.css';

function FormPage({name="FormPage"}) {
  return (
    <div >
      <header className="App-header">
        <a>
          Hey Learn React at {name} page
        </a>
      </header>
    </div>
  );
}

export default FormPage;
