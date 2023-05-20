import { useContext, useState } from "react";
import AuthContext from "../context/AuthContext";
import { Link, useNavigate } from "react-router-dom";
import * as yup from "yup";
import { useForm } from "react-hook-form"
import { yupResolver } from "@hookform/resolvers/yup"

export const LoginPage = () => {

    const navigate = useNavigate()
    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")
    let { loginUser }: any = useContext(AuthContext);

    const login = async (e: any) => {
        e.preventDefault()
        await loginUser(username, password)
        navigate("/")
    }

    const loginFormSchema = yup.object().shape({
        username: yup.string().required("Username can't be empty"),
        password: yup.string().required("Password can' be empty")
    });

    const { register, handleSubmit, formState: { errors } } = useForm({
        resolver: yupResolver(loginFormSchema)
    })

    const onSubmit = (data: any, e: any) => {
        login(e);
    }

    return (
        <div className="form-container">
            <form onSubmit={handleSubmit(onSubmit)} className="form">
                <input type="text" {...register("username")} name="username"
                    value={username} onChange={(e) => setUsername(e.target.value)}
                    placeholder="Enter your username..." className="form-input" />
                <p className="error-message">{errors.username?.message?.toString()}</p>
                <input type="password" {...register("password")} name="password"
                    value={password} onChange={(e) => setPassword(e.target.value)}
                    placeholder="Enter your password..." className="form-input" />
                <p className="error-message">{errors.password?.message?.toString()}</p>
                <button className="submit-btn" type="submit"> Login </button>
                <span>Don't have an account..?</span><Link to={"/signup"} id="createAccountBtn">Create an account</Link>
            </form>

        </div>
    );

}