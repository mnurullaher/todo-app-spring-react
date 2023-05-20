import { useNavigate } from "react-router-dom";
import { signupUser } from "../client/Auth";

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

    return (

        <div className="form-container">
            <form onSubmit={addUser} className="form">
                <input type="text" name="fullname" placeholder="Enter your fullname.." className="form-input" />
                <input type="text" name="username" placeholder="Enter your username..." className="form-input" />
                <input type="password" name="password" placeholder="Enter your password..." className="form-input" />
                <button className="submit-btn" type="submit"> Sign Up </button>
            </form>
        </div>

    );

}