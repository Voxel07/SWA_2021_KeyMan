import React from 'react';
import './App.css';
import {BrowserRouter as Router, Route} from 'react-router-dom';
import LoginPage from './pages/Login/login.page'
import HomePage from './pages/Home/home.page';
import { ProtectedRoute } from './pages/Login/protectedRoute';

function App() {
  return (
    <Router>
      <Route path='/' exact component={LoginPage}/>
      <ProtectedRoute path='/keyman/' component={HomePage}/>
    </Router>
  );
}

export default App;

