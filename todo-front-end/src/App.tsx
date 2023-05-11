import './App.css';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import { AddTodo } from './pages/AddTodo';
import { Todos } from './pages/Todos';
import { Navbar } from './components/Navbar';
import { Home } from './pages/Home';



function App() {


  return (
    <div className="App">

      <Router>
        <Navbar />
        <Routes>
          <Route path='/' element={<Home />} />
          <Route path='/todo-add' element={<AddTodo />} />
          <Route path='/todos' element={<Todos />} />
          <Route path='*' element={<h1> PAGE NOT FOUND </h1>} />
        </Routes>
      </Router>

    </div>
  );
}

export default App;
