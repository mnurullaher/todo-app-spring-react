export const getTodos = async (authToken: any, setTodoList: Function) => {

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

export const completeTodo = (id: number, authToken: any) => {
    fetch(`http://localhost:8080/todos?id=${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + String(authToken.token)
        }
    })
};

export const removeTodo = (id: number, authToken: any) => {
    fetch(`http://localhost:8080/todos?id=${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + String(authToken.token)
        }
    })
    
}