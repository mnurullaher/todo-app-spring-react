import { Todo } from "../pages/TodosPage";

interface TodoProps {
  todo: Todo
  completeTodo(id: number): void
  removeTodo(id: number): void
}

export const TodoComp = (props: TodoProps) => {
    return (
        <div className='task'>
          <p style={{backgroundColor: props.todo.completed ? "green" : "red"}}>{props.todo.description}</p>
          <p>{new Date(props.todo.deadline).toLocaleString('lookup')}</p>
          <button onClick={() => props.completeTodo(props.todo.id)} className="complete-btn">Complete</button>
          <button onClick={() => props.removeTodo(props.todo.id)} className="remove-btn">Remove</button>
        </div>
      );
}