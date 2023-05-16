import { useContext } from "react";
import { Link } from "react-router-dom";
import AuthContext from "../context/AuthContext";

export const Navbar = () => {

    let { user, logoutUser }: any = useContext(AuthContext);

    return (
        <div className="navbar">
            {user ? (
                <Link className="link" to={"/"} onClick={logoutUser}>Logout</Link>
            ) : <>
                <Link className="link" to={"/"}>Home</Link>
                <Link className="link" to={"/login"}>Login</Link>
                <Link className="link" to={"/signup"}>Signup</Link>
            </>
            }
            <Link className="link" to={"/todos-add"}> Add Todo </Link>
            <Link className="link" to={"/todos"}> Show Todos </Link>
        </div>
    );

}