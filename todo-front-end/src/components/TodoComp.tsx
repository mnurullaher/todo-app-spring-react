interface TodoProps {
  id: number,
  description: string,
  deadline: Date,
  completed: boolean,
  completeTask(id: number): void
  removeTask(id: number): void
}

export const TodoComp = (props: TodoProps) => {
    return (
        <div className='task'>
          <p style={{backgroundColor: props.completed ? "green" : "red"}}>{props.description}</p>
          <p>{new Date(props.deadline).toLocaleString('lookup')}</p>
          <p>{props.completed}</p>
          <button onClick={() => props.completeTask(props.id)} className="complete-remove-btn complete-btn">Complete</button>
          <button onClick={() => props.removeTask(props.id)} className="complete-remove-btn remove-btn">Remove</button>
        </div>
      );
}