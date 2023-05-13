import { useContext } from "react";
import { Link } from "react-router-dom";
import AuthContext from "../context/AuthContext";

export const Navbar = () => {

    let {user, logoutUser}: any = useContext(AuthContext);

    return (
        <div>
            <Link to={"/"}> Home </Link>
            <Link to={"/todos-add"}> Add Todo </Link>
            <Link to={"/todos"}> Show Todos </Link>
            {user ? (
                <p onClick={logoutUser}>Logout</p>
            ) : <Link to={"/login"}>Login</Link>}
        </div>
    );

}