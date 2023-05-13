import { useContext } from "react";
import { Navigate, Outlet } from "react-router-dom";
import AuthContext from "../context/AuthContext";

export const PrivateRoutes = () => {
    let {user}: any = useContext(AuthContext)

    return (

        user ? <Outlet/> : <Navigate to="/login" />

    );

}