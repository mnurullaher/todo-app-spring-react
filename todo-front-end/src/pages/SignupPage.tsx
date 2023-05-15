import { useNavigate } from "react-router-dom";

export const SignupPage = () => {

    const navigate = useNavigate();

    const signupUser = async (e: any) => {
        e.preventDefault();

        let response = await fetch(`http://localhost:8080/auth/signup`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ 'fullName': e.target.fullName.value, 'username': e.target.username.value, 'password': e.target.password.value })
        })

        if (response.status === 200) {
            alert("Please login to continue");
            navigate('/login');
        }
    }

    return (

        <div>
            <form onSubmit={signupUser}>
                <input type="text" name="fullName" placeholder="Enter Fullname" />
                <input type="text" name="username" placeholder="Enter Username" />
                <input type="password" name="password" placeholder="Enter Password" />
                <input className="submit" type="submit"/>
            </form>
        </div>

    );

}