import { API_URL } from "./Todo";

interface User {
    fullName: string,
    username: string,
    password: string
}

export const signupUser = async (user: User) => {

    return await fetch(`${API_URL}/auth/signup`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ 'fullName': user.fullName, 'username': user.username, 'password': user.password })
    })
}

export const loginUserReq = async (username: string, password: string) => {
    return await fetch(`${API_URL}/auth/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ 'username': username, 'password': password })
    })

}