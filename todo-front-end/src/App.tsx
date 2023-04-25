import { ChangeEvent, useState } from 'react';
import './App.css';
import axios, { Axios } from 'axios';

function App() {

  const [description, setDescription] = useState("");
  const [deadline, setDeadline] = useState("");
  const [isCompleted, setIsCompleted] = useState(false)

  const handleDescription = (event: ChangeEvent<HTMLInputElement>) => {
    setDescription(event.target.value)
  };

  const handleDeadline = (event: ChangeEvent<HTMLInputElement>) => {
    setDeadline(event.target.value)
  }

  const saveTodo = () => {
    fetch(`http://localhost:8080/add-todo?description=${description}&isCompleted=${isCompleted}&deadline=${deadline}`, {
      method: 'POST'
    })
      .then(response => response.json())
      .then(data => console.log(data.message))
      .catch(error => console.error(error));
  }


  return (
    <div className="App">
      <div className="add-task">

        <div className='input-container'>
          <label>Description</label>
          <input type="textarea" placeholder='Description' className='text-area' onChange={handleDescription} />
        </div>

        <div className='input-container'>
          <label>Deadline</label>
          <input type="date" onChange={handleDeadline} />
        </div>

        <button className='save-todo-button' onClick={saveTodo}>Save</button>

      </div>
    </div>
  );
}

export default App;
