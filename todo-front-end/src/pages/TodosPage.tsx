import { useContext, useEffect, useState } from "react";
import { TodoComp } from "../components/TodoComp";
import AuthContext from "../context/AuthContext";
import { completeTodo, getTodos, removeTodo } from "../client/Todo";

export interface Todo {
    id: number,
    description: string,
    deadline: Date,
    completed: boolean
}


export const TodosPage = () => {

    const [todoList, setTodoList] = useState<Todo[]>([])
    let { authToken }: any = useContext(AuthContext);



    useEffect(() => {
        getTodos(authToken, setTodoList)
    }, [])

    return (

        <div className='todos'>
            {
                todoList.map((todo: Todo) => {
                    return (
                        < TodoComp
                            todo={todo}
                            completeTodo={() => completeTodo(todo.id, authToken)}
                            removeTodo={() => removeTodo(todo.id, authToken)}
                        />
                    )
                })
            }
        </div>

    );

}