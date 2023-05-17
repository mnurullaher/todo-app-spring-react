import { useContext } from "react";
import AuthContext from "../context/AuthContext";
import { Link } from "react-router-dom";

export const LoginPage = () => {
    
    let {loginUser}: any = useContext(AuthContext);

    return (
        <div className="formContainer">
            <form onSubmit={loginUser} className="form">
                <input type="text" name="username" placeholder="USERNAME" className="formInput" />
                <input type="password" name="password" placeholder="PASSWORD" className="formInput"/>
                <button className="submitBtn" type="submit"> LOGIN </button>
                <span>Don't have an account..?</span><Link to={"/signup"} id="createAccountBtn">Create an account</Link>
            </form>
            
        </div>
    );
    
}