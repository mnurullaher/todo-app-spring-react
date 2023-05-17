export interface Todo {
    id?: number,
    description: string,
    deadline: string,
    completed: boolean
}

export interface TodoProps {
    todo: Todo
    completeTodo(): void
    removeTodo(): void
  }