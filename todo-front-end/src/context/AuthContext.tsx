import { createContext, useState } from "react";
import jwt_decode from "jwt-decode"
import { useNavigate } from "react-router-dom"

const AuthContext = createContext({})

export default AuthContext;

export const AuthProvider = ({ children }: any) => {


    let [authToken, setAuthToken] = useState(() => localStorage.getItem('authToken') ? JSON.parse(localStorage.getItem('authToken') || "") : null);
    let [user, setUser] = useState(() => localStorage.getItem('authToken') ? jwt_decode(localStorage.getItem('authToken') || "") : null);

    const navigate = useNavigate();

    let loginUser = async (e: any) => {
        e.preventDefault();

        let response = await fetch('http://localhost:8080/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ 'username': e.target.username.value, 'password': e.target.password.value })
        })


        if (response.status === 200) {
            let data = await response.json();
            setAuthToken(data);
            setUser(jwt_decode(data.token))
            localStorage.setItem('authToken', JSON.stringify(data))
            navigate('/todos');
        } else {
            alert("Something Went Wrong")
        }

    }

    let logoutUser = () => {
        setAuthToken(null);
        setUser(null);
        localStorage.removeItem('authToken')
        navigate('/login');
    }

    let contextData = {
        user: user,
        loginUser: loginUser,
        logoutUser: logoutUser,
        authToken: authToken
    }

    return (
        <AuthContext.Provider value={contextData} >
            {children}
        </AuthContext.Provider>
    );
}