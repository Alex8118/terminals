import React from 'react';
import Auth from './auth';
import UserService from '../services/userService';
import { Link, withRouter } from 'react-router-dom';
import update from 'immutability-helper';

class SignUpForm extends React.Component {
  state = {
    formControls: {
      name: {
        value: '',
        type: 'name',
        label: 'Имя',
        errorMessage: 'Имя должно иметь минимум 2 и максимум 250 символов',
        valid: true,
        validation: {
          minLength: 2,
          maxLength: 250
        }
      },
      email: {
        value: '',
        type: 'text',
        label: 'Электронная почта',
        errorMessage: 'Введите эл. адрес (формата "mail@email.ru")',
        valid: true,
        validation: {
          email: true
        }
      },
      password: {
        value: '',
        type: 'password',
        label: 'Пароль',
        errorMessage:
          'Пароль должен иметь в себе буквы (большие и маленькие), цифры, символы (@#!$_) и быть не короче 6 и не длиннее 16 символов',
        valid: true,
        validation: {
          minLength: 6,
          maxLength: 16,
          password: true
        }
      }
    }
  };

  submitHandler = async (event, isFormValid) => {
    event.preventDefault();
    if (isFormValid) {
      let credentials = JSON.stringify({
        name: this.state.formControls.name.value,
        email: this.state.formControls.email.value,
        password: this.state.formControls.password.value
      });
      let status = await UserService.submit(credentials);
      switch (status) {
        case 200:
          this.props.history.push('/');
          alert(
            'Регистрация прошла успешно, используйте указанные при регистрации данные чтобы войти в систему'
          );
          break;
        case 409:
          alert('Пользователь с таким электронным адресом уже зарегистрирован');
          break;
        case 422:
          alert('Введены некорректные данные');
          break;
        default:
          alert('Произошла ошибка');
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
        title="Регистрация"
        buttonText="Зарегистрироваться"
        transition={<Link to="/">У меня уже есть аккаунт</Link>}
      />
    );
  }
}

export default withRouter(SignUpForm);
