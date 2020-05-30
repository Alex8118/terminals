import React from 'react';
import { Link, withRouter } from 'react-router-dom';
import UserService from '../services/userService';
import Auth from './auth';
import update from 'immutability-helper';

class SignInForm extends React.Component {
  state = {
    formControls: {
      email: {
        value: '',
        type: 'text',
        label: 'Электронная почта',
        errorMessage: 'Введите корректный эл. адрес',
        valid: true,
        validation: {
          email: true
        }
      },
      password: {
        value: '',
        type: 'password',
        label: 'Пароль',
        errorMessage: 'Введите корректный пароль',
        valid: true,
        validation: {
          minLength: 1
        }
      }
    }
  };

  submitHandler = async (event, isFormValid) => {
    event.preventDefault();
    if (isFormValid) {
      let credentials = JSON.stringify({
        password: this.state.formControls.password.value,
        email: this.state.formControls.email.value
      });
      let token = await UserService.getUserToken(credentials);
      if (token) {
        sessionStorage.setItem('token', token);
        let user = await UserService.getCurrentUser();
        sessionStorage.setItem('userId', user.id);
        sessionStorage.setItem('email', user.email);
        sessionStorage.setItem('userName', user.name);
        if (user.role === 'ROLE_USER') {
          this.props.history.push('/dashboard');
        } else {
          this.props.history.push('/admin');
        }
      } else {
        alert('Введен неправильный логин или пароль');
      }
    } else {
      alert('Форма заполнена неправильно');
    }
  };

  onControlChanged = (controlName, value, valid) => {
    let formControls = update(this.state.formControls, {
      [controlName]: {
        value: { $set: value },
        valid: { $set: valid }
      }
    });
    this.setState({
      formControls
    });
  };

  render() {
    return (
      <Auth
        data={this.state}
        submitHandler={this.submitHandler}
        onControlChanged={this.onControlChanged}
        title="Войти в кабинет администратора"
        buttonText="Войти"
        transition={<Link to="/signup">Создать аккаунт</Link>}
      />
    );
  }
}

export default withRouter(SignInForm);
