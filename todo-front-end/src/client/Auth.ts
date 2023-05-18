import { API_URL } from "./Todo";

export const signupUser = async (e: any) => {
    e.preventDefault();

    return await fetch(`${API_URL}/auth/signup`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ 'fullName': e.target.fullName.value, 'username': e.target.username.value, 'password': e.target.password.value })
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