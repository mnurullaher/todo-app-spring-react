import { useNavigate } from "react-router-dom";
import { signupUser } from "../client/Auth";
import * as yup from "yup";
import { useForm } from "react-hook-form"
import { yupResolver } from "@hookform/resolvers/yup"

export const SignupPage = () => {

    const navigate = useNavigate();

    const addUser = async (e: any) => {
        e.preventDefault();

        let response = await signupUser(
            {
                fullName: e.target.fullname.value,
                username: e.target.username.value,
                password: e.target.password.value
            }
        );

        if (response.status === 200) {
            alert("Please login to continue");
            navigate('/');
        }
    }

    const signupFormSchema = yup.object().shape({
        fullname: yup.string().required("Name can't be empty"),
        username: yup.string().required("Username cant't be empty"),
        password: yup.string().required("Password can't be empty")
    })

    const { register, handleSubmit, formState: { errors } } = useForm({
        resolver: yupResolver(signupFormSchema)
    })

    const onSubmit = (data: any, e: any) => {
        addUser(e)
    }

    return (

        <div className="form-container">
            <form onSubmit={handleSubmit(onSubmit)} className="form">
                <input type="text" {...register("fullname")} name="fullname" placeholder="Enter your fullname.." className="form-input" />
                <p className="error-message">{errors.fullname?.message?.toString()}</p>
                <input type="text" {...register("username")} name="username" placeholder="Enter your username..." className="form-input" />
                <p className="error-message">{errors.username?.message?.toString()}</p>
                <input type="password" {...register("password")} name="password" placeholder="Enter your password..." className="form-input" />
                <p className="error-message">{errors.password?.message?.toString()}</p>
                <button className="submit-btn" type="submit"> Sign Up </button>
            </form>
        </div>

    );

}