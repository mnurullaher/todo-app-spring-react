export const completeTodo = (id: number) => {
    fetch(`http://localhost:8080/todos?id=${id}`, {
        method: 'PUT'
    })
};

export const removeTodo = (id: number) => {
    fetch(`http://localhost:8080/todos?id=${id}`, {
        method: 'DELETE'
    })
}

export const saveTodo = (description: string, isCompleted: boolean, deadline: string) => {
    fetch(`http://localhost:8080/todos?description=${description}&isCompleted=${isCompleted}&deadline=${deadline}`, {
        method: 'POST'
    })
}

export const getTodos = async (setTodoList: Function) => {
    await fetch('http://localhost:8080/todos')
        .then(response => response.json())
        .then(data => setTodoList(data))    
        .catch(error => console.log(error))
}