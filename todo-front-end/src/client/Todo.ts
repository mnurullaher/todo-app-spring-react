import { Todo } from "../model/Todo";

export const getTodos = async (authToken: any): Promise<Todo[]> => {

    try {
        let response = await fetch('http://localhost:8080/todos', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + String(authToken.token)
            }
    
        })

        let data = await response.json();
        return data;
    } catch(error) {
        throw(error);        
    }   
}

export const completeTodo = async (id: number, authToken: any) => {
    await fetch(`http://localhost:8080/todos?id=${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + String(authToken.token)
        }
    })
};

export const removeTodo = async (id: number, authToken: any) => {
    await fetch(`http://localhost:8080/todos?id=${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + String(authToken.token)
        }
    })
    
}


