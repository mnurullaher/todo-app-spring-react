import { useContext, useEffect, useState } from "react";
import { TodoComp } from "../components/TodoComp";
import AuthContext from "../context/AuthContext";
import { completeTodo, getTodos, removeTodo } from "../client/Todo";
import { Todo } from "../model/Todo";
import { useLocation } from "react-router-dom";

export const TodosPage = () => {

    const [todoList, setTodoList] = useState<Todo[]>([]);
    let { authToken }: any = useContext(AuthContext);
    const location = useLocation();

    let setTodos = async (token: any) =>  {
        setTodoList(await getTodos(token));
    }

    let deleteTodo = async (id: number, authToken: any) => {
        await removeTodo(id, authToken);
        setTodos(authToken);
    }

    let fullfillTodo = async (id: number, authToken: any) => {
        await completeTodo(id, authToken);
        setTodos(authToken)
    }

    useEffect(() => {
        setTodos(authToken);
    }, [])

    useEffect(() => {
        setTodos(authToken);
    }, [location.pathname])

    return (

        <div className='todos'>
            {
                todoList.map((todo: Todo) => {
                    return (
                        < TodoComp
                            key={todo.id}
                            todo={todo}
                            completeTodo={() => fullfillTodo(todo.id, authToken)}
                            removeTodo={() => deleteTodo(todo.id, authToken)}
                        />
                    )
                })
            }
        </div>

    );

}
