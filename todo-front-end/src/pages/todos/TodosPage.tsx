import "./todos.scss"
import { useContext, useEffect, useState } from "react";
import { TodoComp } from "../../components/Todo/TodoComp";
import AuthContext from "../../context/AuthContext";
import { completeTodo as toggleTodoCompletion, getTodos, removeTodo, saveTodo } from "../../client/Todo";
import { Todo } from "../../model/Todo";
import { CreateTodo } from "../../components/CreateTodo/CreateTodo";

export const TodosPage = () => {
    const [todoList, setTodoList] = useState<Todo[]>([]);
    let { authToken }: any = useContext(AuthContext);

    let deleteTodo = async (id: number, authToken: any) => {
        await removeTodo(id, authToken);
        setTodoList(await getTodos(authToken));
    }

    let toggleCompletionTodo = async (id: number, authToken: any) => {
        await toggleTodoCompletion(id, authToken);
        setTodoList(await getTodos(authToken));
    }

    const onCreateTodo = async (todo: Todo) => {
        await saveTodo(todo, authToken)
        setTodoList(await getTodos(authToken));
    }

    useEffect(() => {
        getTodos(authToken).then(todos => setTodoList(todos));
    }, [])

    return (
        <>
            <div className='todos-container'>
                <h1>Todo List</h1>
                {todoList.map((todo: Todo) => {
                    return (
                        <TodoComp
                            key={todo.id}
                            todo={todo}
                            completeTodo={() => toggleCompletionTodo(todo.id!, authToken)}
                            removeTodo={() => deleteTodo(todo.id!, authToken)} />
                    );
                })}
                <CreateTodo onCreate={onCreateTodo} />
            </div>
        </>
    );

}
