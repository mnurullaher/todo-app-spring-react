import { createContext, useState } from "react";
import jwt_decode from "jwt-decode"
import { loginUserReq } from "../client/Auth";

export interface AuthToken {
    token: string
}

const AuthContext = createContext({})

export default AuthContext;

export const AuthProvider = ({ children }: any) => {
    let [authToken, setAuthToken] = useState<AuthToken | null>(() => localStorage.getItem('authToken') ? JSON.parse(localStorage.getItem('authToken') || "") : null);
    let [user, setUser] = useState(() => localStorage.getItem('authToken') ? jwt_decode(localStorage.getItem('authToken') || "") : null);

    let loginUser = async (username: string, password: string) => {
        let response = await loginUserReq(username, password);
        if (response.status === 200) {
            let data = await response.json();
            setAuthToken(data);
            setUser(jwt_decode(data.token))
            localStorage.setItem('authToken', JSON.stringify(data))
        } else {
            alert("Something Went Wrong")
        }

    }

    let logoutUser = () => {
        console.log("logout")
        setAuthToken(null);
        setUser(null);
        localStorage.removeItem('authToken')
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