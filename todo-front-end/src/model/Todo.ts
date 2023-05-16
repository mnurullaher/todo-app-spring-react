export interface Todo {
    id: number,
    description: string,
    deadline: Date,
    completed: boolean
}

export interface TodoProps {
    todo: Todo
    completeTodo(): void
    removeTodo(): void
  }