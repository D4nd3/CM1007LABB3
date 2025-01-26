import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import RegisterStaffPage from './pages/RegisterStaffPage';
import MessagePage from './pages/MessagePage';
import NotePage from './pages/NotePage';
import JournalPage from './pages/JournalPage';
import ImagePage from './pages/ImagePage';
import SearchPage from './pages/SearchPage';
import { Navbar, UserRoleDisplay } from './components';
import { AuthProvider, useAuth } from './contexts/AuthContext';


const App: React.FC = () => {
  const isAuthenticated: boolean = false;

  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/" element={isAuthenticated ? <HomePage /> : <Navigate to="/welcome" />} />
          <Route path="/welcome" element={<WelcomePage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/registerStaff" element={<ProtectedRoute><RegisterStaffPage /></ProtectedRoute>} />
          <Route path="/message" element={<ProtectedRoute><MessagePage /></ProtectedRoute>} />
          <Route path="/note" element={<ProtectedRoute><NotePage /></ProtectedRoute>} />
          <Route path="/journal" element={<ProtectedRoute><JournalPage /></ProtectedRoute>} />
          <Route path="/image" element={<ProtectedRoute><ImagePage/></ProtectedRoute>} />
          <Route path="/search" element={<ProtectedRoute><SearchPage /></ProtectedRoute>} />
        </Routes>
      </Router>
    </AuthProvider>
  );
};

const WelcomePage: React.FC = () => {
  return (
    <div className="App">
      <Navbar />
      <UserRoleDisplay />
      <header className="App-header">
        <h1>Välkommen till applikationen!</h1>
      </header>
    </div>
  );
};

function ProtectedRoute({ children }: { children: React.ReactNode }) {
  const { isAuthenticated } = useAuth();
  return isAuthenticated ? <>{children}</> : <Navigate to="/login" />;
}

export default App;