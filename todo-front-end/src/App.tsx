import './App.scss';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { TodosPage } from './pages/todos/TodosPage';
import { LoginPage } from './pages/LoginPage';
import { PrivateRoutes, AnonymousRoutes } from './utils/PrivateRoutes';
import { AuthProvider } from './context/AuthContext';
import { SignupPage } from './pages/SignupPage';
import { Header } from './components/Header';

function App() {

  return (
    <>
      <AuthProvider>
        <Router>
          <Header/>
          <div className='app-container'>
            <Routes>
              <Route element={<PrivateRoutes />}>
                <Route path='/' element={<TodosPage />} />
              </Route>
              <Route element={<AnonymousRoutes />}>
                <Route path='/login' element={<LoginPage />} />
                <Route path='/signup' element={<SignupPage />}></Route>
              </Route>
              <Route path='*' element={<h1> PAGE NOT FOUND </h1>} />
            </Routes>
          </div>
        </Router>
      </AuthProvider>
    </>
  );
}

export default App;
