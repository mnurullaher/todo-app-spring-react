import { useContext } from "react";
import AuthContext from "../context/AuthContext";

export const LoginPage = () => {
    
    let {loginUser}: any = useContext(AuthContext);

    return (
        <div>
            <form onSubmit={loginUser}>
                <input type="text" name="username" placeholder="Enter Username" />
                <input type="password" name="password" placeholder="Enter Password" />
                <input type="submit" />
            </form>
        </div>
    );
    
}