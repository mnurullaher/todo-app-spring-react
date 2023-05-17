import { TodoProps } from "../model/Todo";

export const TodoComp = (props: TodoProps) => {
  return (
    <div className='taskContainer'>
      <input type="checkbox" className="checkBox" onClick={() => props.completeTodo()}/>
      <div className="taskUnitContainer">
        <p className="todoUnit">{props.todo.description}</p>
        <p className="todoUnit">{new Date(props.todo.deadline).toLocaleString('lookup')}</p>
      </div>
      <button onClick={() => props.removeTodo()} className="removeBtn">X</button>
    </div>
  );
}