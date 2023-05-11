import { useEffect, useState } from "react";
import { completeTodo, getTodos, removeTodo, saveTodo } from "../client/Todo";
import { TodoComp } from "../components/TodoComp";

export interface Todo {
    id: number,
    description: string,
    deadline: Date,
    completed: boolean
  }

export const Todos = () => {

    const [todoList, setTodoList] = useState<Todo[]>([])


    useEffect(() => {
        getTodos(setTodoList)
      }, [saveTodo, completeTodo, removeTodo])

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