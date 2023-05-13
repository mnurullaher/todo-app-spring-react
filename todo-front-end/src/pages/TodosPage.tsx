import { useContext, useEffect, useState } from "react";
import { TodoComp } from "../components/TodoComp";
import AuthContext from "../context/AuthContext";

export interface Todo {
    id: number,
    description: string,
    deadline: Date,
    completed: boolean
}

export const TodosPage = () => {

    const [todoList, setTodoList] = useState<Todo[]>([])
    let { authToken }: any = useContext(AuthContext);

    const getTodos = async () => {

        await fetch('http://localhost:8080/todos', {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + String(authToken.token)
            }

        })
            .then(response => response.json())
            .then(data => setTodoList(data))
            .catch(error => console.log(error))
    }

    const completeTodo = (id: number) => {
        fetch(`http://localhost:8080/todos?id=${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + String(authToken.token)
            }
        })
    };

    const removeTodo = (id: number) => {
        fetch(`http://localhost:8080/todos?id=${id}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + String(authToken.token)
            }
        })
    }

    useEffect(() => {
        getTodos()
    }, [])

    return (

        <div className='todos'>
            {
                todoList.map((todo: Todo) => {
                    return (
                        < TodoComp
                            todo={todo}
                            completeTodo={completeTodo}
                            removeTodo={removeTodo}
                        />
                    )
                })
            }
        </div>

    );

}