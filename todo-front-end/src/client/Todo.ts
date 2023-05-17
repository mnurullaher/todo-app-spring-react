import { AuthToken } from "../context/AuthContext";
import { Todo } from "../model/Todo";

export const API_URL = "http://13.49.158.103:8080";

export const getTodos = async (authToken: AuthToken): Promise<Todo[]> => {    

    try {
        let response = await fetch(`${API_URL}/todos`, {
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

export const completeTodo = async (id: number, authToken: AuthToken) => {
    await fetch(`${API_URL}/todos?id=${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + String(authToken.token)
        }
    })
};

export const removeTodo = async (id: number, authToken: AuthToken) => {
    await fetch(`${API_URL}/todos?id=${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + String(authToken.token)
        }
    })
    
}

export const saveTodo = (todo: Todo, authToken: AuthToken) => {
    fetch(`${API_URL}/todos`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + String(authToken.token)
        },
        body: JSON.stringify({'description': todo.description, 'deadline': todo.deadline, 'completed': todo.completed})
    })
}




