import './App.scss';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { TodosPage } from './pages/TodosPage';
import { LoginPage } from './pages/LoginPage';
import { PrivateRoutes } from './utils/PrivateRoutes';
import { AuthProvider } from './context/AuthContext';
import { SignupPage } from './pages/SignupPage';

function App() {

  return (
      <Router>
        <AuthProvider>
          <Routes>
            <Route element={<PrivateRoutes />}>
              <Route path='/todos' element={<TodosPage />} />
            </Route>
            <Route path='/' element={<LoginPage />} />
            <Route path='/signup' element={<SignupPage />}></Route>
            <Route path='*' element={<h1> PAGE NOT FOUND </h1>} />
          </Routes>
        </AuthProvider>
      </Router>
  );
}

export default App;
