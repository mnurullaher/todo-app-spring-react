import { Link } from "react-router-dom";

export const Navbar = () => {

    return (
        <div>
            <Link to={"/"}> Home </Link>
            <Link to={"/todo-add"}> Add Todo </Link>
            <Link to={"/todos"}> Show Todos </Link>
        </div>
    );

}