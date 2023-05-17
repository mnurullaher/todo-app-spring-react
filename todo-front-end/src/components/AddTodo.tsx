import { ChangeEvent, useContext, useState } from "react";
import AuthContext from "../context/AuthContext";
import { saveTodo } from "../client/Todo";

export const AddTodoPage = () => {

  const [description, setDescription] = useState("");
  const [deadline, setDeadline] = useState("");
  const [isCompleted, setIsCompleted] = useState(false)
  let { authToken }: any = useContext(AuthContext);

  const handleDescription = (event: ChangeEvent<HTMLInputElement>) => {
    setDescription(event.target.value)
  };

  const handleDeadline = (event: ChangeEvent<HTMLInputElement>) => {
    setDeadline(event.target.value)
  }

  const addTodo = async () => {

    await saveTodo({ description: description, deadline: deadline, completed: isCompleted }, authToken)

    window.location.reload()

  }

  return (

    <div className="addTask">

      <input type="textarea" placeholder='Description' onChange={handleDescription} className="todoDescription"/>
      <input type="date" onChange={handleDeadline} className="todoDate"/>
      <button className="addItemBtn" onClick={() => { addTodo() }}>Add Item</button>

    </div>

  );

}