import { useContext, useState } from "react";
import AuthContext from "../context/AuthContext";
import { Link, useNavigate } from "react-router-dom";

export const LoginPage = () => {
    
    const navigate = useNavigate()
    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")
    let {loginUser}: any = useContext(AuthContext);

    const login = async (e: any) => {
        e.preventDefault()
        await loginUser(username, password)
        navigate("/")
    }

    return (
        <div className="formContainer">
            <form onSubmit={login} className="form">
                <input type="text" name="username" 
                    value={username} onChange={(e) => setUsername(e.target.value)}
                    placeholder="Enter your username..." className="formInput" />
                <input type="password" name="password"
                    value={password} onChange={(e) => setPassword(e.target.value)}
                    placeholder="Enter your password..." className="formInput"/>
                <button className="submitBtn" type="submit"> Login </button>
                <span>Don't have an account..?</span><Link to={"/signup"} id="createAccountBtn">Create an account</Link>
            </form>
            
        </div>
    );
    
}