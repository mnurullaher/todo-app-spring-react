import { ChangeEvent, useState } from "react";
import './create-todo.scss'
import { Todo } from "../../model/Todo";

interface CreateTodoProps {
  onCreate(t: Todo): void
}

export const CreateTodo = (props: CreateTodoProps) => {

  const [description, setDescription] = useState("");
  const [deadline, setDeadline] = useState("");
  const [isCompleted, setIsCompleted] = useState(false)

  const handleDescription = (event: ChangeEvent<HTMLInputElement>) => {
    setDescription(event.target.value)
  };

  const handleDeadline = (event: ChangeEvent<HTMLInputElement>) => {
    setDeadline(event.target.value)
  }

  const createTodo = async () => {
    await props.onCreate({
      completed: isCompleted,
      description: description,
      deadline: deadline
    })
    setDeadline("")
    setDescription("")
    setIsCompleted(false)
  }

  return (
    <div className="create-task-container">
      <input type="textarea" className="description" 
        placeholder='Description'
        onChange={handleDescription}
        value={description}
      />
      <input type="date" className="date" value={deadline} onChange={handleDeadline} />
      <button className="create-todo-btn" onClick={() => { createTodo() }}>Create</button>
    </div>
  );

}