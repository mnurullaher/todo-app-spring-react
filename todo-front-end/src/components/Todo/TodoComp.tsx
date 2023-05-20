import { useState } from "react";
import { TodoProps } from "../../model/Todo";
import './todo.scss';

export const TodoComp = (props: TodoProps) => {

  const [isChecked, setIsChecked] = useState(false);

  const handleCheck = (e: any) => {
    setIsChecked(e.target.checked)
  }

  return (
    <div className='todo-container'>
      <input type="checkbox" checked={props.todo.completed} className="is-completed" onClick={() => props.completeTodo()} onChange={handleCheck}/>
      <div>
        <p className={props.todo.completed ? 'text-lt' : ''} >{props.todo.description}</p>
        <span>{new Date(props.todo.deadline).toLocaleString('lookup')}</span>
      </div>
      <button onClick={() => props.removeTodo()} className="remove-btn">X</button>
    </div>
  );
}