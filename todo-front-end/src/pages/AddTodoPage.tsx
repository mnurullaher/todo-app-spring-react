import { ChangeEvent, useContext, useState } from "react";
import AuthContext from "../context/AuthContext";
import { useNavigate } from "react-router-dom";
import { saveTodo } from "../client/Todo";

export const AddTodoPage = () => {

  const [description, setDescription] = useState("");
  const [deadline, setDeadline] = useState("");
  const [isCompleted, setIsCompleted] = useState(false)
  let {authToken}: any = useContext(AuthContext);

  const handleDescription = (event: ChangeEvent<HTMLInputElement>) => {
    setDescription(event.target.value)
  };

  const handleDeadline = (event: ChangeEvent<HTMLInputElement>) => {
    setDeadline(event.target.value)
  }

  const navigate = useNavigate();

  const addTodo = async() => {

    await saveTodo({description: description, deadline: deadline, completed: isCompleted}, authToken)

    navigate('/todos')

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

      <button className="submit" onClick={() => addTodo()}>Save</button>

    </div>

  );

}