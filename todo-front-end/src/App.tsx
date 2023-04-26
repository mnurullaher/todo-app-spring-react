import { ChangeEvent, useEffect, useState } from 'react';
import './App.css';
import { TodoComp } from './components/TodoComp';

export interface Todo {
  id: number,
  description: string,
  deadline: Date,
  completed: boolean
}

function App() {

  const [description, setDescription] = useState("");
  const [deadline, setDeadline] = useState("");
  const [isCompleted, setIsCompleted] = useState(false)
  const [todoList, setTodoList] = useState<Todo[]>([])

  const handleDescription = (event: ChangeEvent<HTMLInputElement>) => {
    setDescription(event.target.value)
  };

  const handleDeadline = (event: ChangeEvent<HTMLInputElement>) => {
    setDeadline(event.target.value)
  }

  const completeTodo = (id: number) => {
    fetch(`http://localhost:8080/todos/update?id=${id}`, {
      method: 'POST'
    })
  };

  const removeTodo = (id: number) => {
    fetch(`http://localhost:8080/todos/remove?id=${id}`, {
      method: 'POST'
    })
  }

  const saveTodo = () => {
    fetch(`http://localhost:8080/todos?description=${description}&isCompleted=${isCompleted}&deadline=${deadline}`, {
      method: 'POST'
    })
      .then(response => response.json())
      .then(data => console.log(data.message))
      .catch(error => console.error(error));
  }

  const getTodos = async () => {
    await fetch('http://localhost:8080/todos')
      .then(response => response.json())
      .then(data => setTodoList(data))
      .catch(error => console.log(error))
  }

  useEffect(() => {
    getTodos()
  }, [saveTodo, completeTodo, removeTodo])

  return (
    <div className="App">
      <div className="add-task">

        <div className='input-container'>
          <label>Description</label>
          <input type="textarea" placeholder='Description' onChange={handleDescription} />
        </div>

        <div className='input-container'>
          <label>Deadline</label>
          <input type="date" onChange={handleDeadline} />
        </div>

        <button className='save-todo-button' onClick={saveTodo}>Save</button>

      </div>

      <div className='todos'>
        {
          todoList.map((todo: Todo) => {
            return (
              < TodoComp
                id={todo.id}
                description={todo.description}
                deadline={todo.deadline}
                completed={todo.completed}
                completeTask={() => completeTodo(todo.id)}
                removeTask={() => removeTodo(todo.id)}
              />
            )
          })
        }
      </div>

    </div>
  );
}

export default App;
