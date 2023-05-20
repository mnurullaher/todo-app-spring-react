import { useContext } from "react"
import AuthContext from "../context/AuthContext"
import { useNavigate } from "react-router-dom";

export const Header = () => {
  const { user, logoutUser } :any = useContext(AuthContext);
  const navigate = useNavigate();

  const logout = () => {
    logoutUser();
    navigate("/login")
  }
  return (
    <header>
      {user != null && <button className='logoutBtn' onClick={logout}>Logout</button>}
    </header>
  )
}