import { useNavigate } from "react-router-dom";
import { signupUser } from "../client/Auth";

export const SignupPage = () => {

    const navigate = useNavigate();

    const addUser = async (e: any) => {
        let response = await signupUser(e);

        if (response.status === 200) {
            alert("Please login to continue");
            navigate('/');
        }
    }

    return (

        <div className="formContainer">
            <form onSubmit={addUser} className="form">
                <input type="text" name="fullName" placeholder="FULLNAME" className="formInput"/>
                <input type="text" name="username" placeholder="USERNAME" className="formInput"/>
                <input type="password" name="password" placeholder="PASSWORD" className="formInput"/>
                <button className="submitBtn" type="submit"> SIGN UP </button>
            </form>
        </div>

    );

}