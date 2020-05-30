import React from 'react';
import './App.css';

import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';

import AuthHeader from './components/authHeader';
import TerminalsDashboardPage from './pages/terminalsDashboardPage';
import AdminDashboardPage from './pages/adminDashboardPage';
import SignInForm from './components/signInForm';
import SignUpForm from './components/signUpForm';
import SettingsPage from './pages/settingsPage';

export default class App extends React.Component {
  render() {
    return (
      <Router>
        <Switch>
          <Route exact path="/">
            <Login />
          </Route>
          <Route exact path="/signup">
            <SignUp />
          </Route>
          <Route path="/dashboard" component={TerminalsDashboardPage} />
          <Route path="/admin" component={AdminDashboardPage} />
          <Route path="/settings" component={SettingsPage} />
        </Switch>
      </Router>
    );
  }
}

function Login() {
  return (
    <main>
      <AuthHeader />
      <div className="login-bg-cover">
        <SignInForm />
      </div>
    </main>
  );
}

function SignUp() {
  return (
    <main>
      <AuthHeader />
      <div className="login-bg-cover">
        <SignUpForm />
      </div>
    </main>
  );
}
