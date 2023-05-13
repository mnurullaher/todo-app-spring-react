import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AddTodoPage } from './pages/AddTodoPage';
import { TodosPage } from './pages/TodosPage';
import { Navbar } from './components/Navbar';
import { HomePage } from './pages/HomePage';
import { LoginPage } from './pages/LoginPage';
import { PrivateRoutes } from './utils/PrivateRoutes';
import { AuthProvider } from './context/AuthContext';



function App() {

  return (
    <div className="App">

      <Router>
        <AuthProvider>
          <Navbar />
          <Routes>
            <Route element={<PrivateRoutes />}>
              <Route path='/' element={<HomePage />} />
              <Route path='/todos-add' element={<AddTodoPage />} />
              <Route path='/todos' element={<TodosPage />} />
            </Route>
            <Route path='/login' element={<LoginPage />} />
            <Route path='*' element={<h1> PAGE NOT FOUND </h1>} />
          </Routes>
        </AuthProvider>
      </Router>

    </div>
  );
}

export default App;
