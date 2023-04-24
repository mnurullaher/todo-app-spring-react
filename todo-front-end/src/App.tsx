import { ChangeEvent, useState } from 'react';
import './App.css';

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

  return (
    <div className="App">
      <div className="add-task">

        <div>
          <label>Description</label>
          <input type="textarea" placeholder='Description' className='text-area' onChange={handleDescription} />
        </div>

        <div>
          <label>Deadline</label>
          <input type="date" onChange={handleDeadline} />
        </div>

      </div>
    </div>
  );
}

export default App;
