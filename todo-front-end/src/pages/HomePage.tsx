import { useContext } from "react";
import AuthContext from "../context/AuthContext";

export const HomePage = () => {
    
    let {name}: any = useContext(AuthContext);

    return (
        <div>This is home page

            <p>{name}</p>

        </div>
    );
}