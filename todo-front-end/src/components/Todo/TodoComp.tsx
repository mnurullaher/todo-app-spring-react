import { TodoProps } from "../../model/Todo";
import './todo.scss';

export const TodoComp = (props: TodoProps) => {
  return (
    <div className='todo-container'>
      <input type="checkbox" checked={props.todo.completed} className="is-completed" onClick={() => props.completeTodo()}/>
      <div>
        <p className={props.todo.completed ? 'text-lt' : ''} >{props.todo.description}</p>
        <span>{new Date(props.todo.deadline).toLocaleString('lookup')}</span>
      </div>
      <button onClick={() => props.removeTodo()} className="removeBtn">X</button>
    </div>
  );
}