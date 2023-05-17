import { useNavigate } from "react-router-dom";
import { signupUser } from "../client/Auth";

export const SignupPage = () => {

    const navigate = useNavigate();

    const addUser = async (e: any) => {
        let response = await signupUser(e);

        if (response.status === 200) {
            alert("Please login to continue");
            navigate('/login');
        }
    }

    return (

        <div>
            <form onSubmit={addUser}>
                <input type="text" name="fullName" placeholder="Enter Fullname" />
                <input type="text" name="username" placeholder="Enter Username" />
                <input type="password" name="password" placeholder="Enter Password" />
                <input className="submit" type="submit" />
            </form>
        </div>

    );

}