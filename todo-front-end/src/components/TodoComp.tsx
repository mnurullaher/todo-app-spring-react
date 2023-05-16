import { TodoProps } from "../model/Todo";

export const TodoComp = (props: TodoProps) => {
    return (
        <div className='task'>
          <p style={{backgroundColor: props.todo.completed ? "green" : "red"}}>{props.todo.description}</p>
          <p>{new Date(props.todo.deadline).toLocaleString('lookup')}</p>
          <button onClick={() => props.completeTodo()} className="complete-btn">Complete</button>
          <button onClick={() => props.removeTodo()} className="remove-btn">Remove</button>
        </div>
      );
}