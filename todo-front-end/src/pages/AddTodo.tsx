import { ChangeEvent, useState } from "react";
import { saveTodo } from "../client/Todo";

export const AddTodo = () => {

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

        <div className="add-task">

        <div className='input-container'>
          <label>Description</label>
          <input type="textarea" placeholder='Description' onChange={handleDescription} />
        </div>

        <div className='input-container'>
          <label>Deadline</label>
          <input type="date" onChange={handleDeadline} />
        </div>

        <button className='save-todo-button' onClick={() => saveTodo(description, isCompleted, deadline)}>Save</button>

      </div>

    );

}