import React from 'react';
import AdminService from '../services/adminService';
import { Button } from 'reactstrap';
import { withRouter } from 'react-router-dom';

class Impersonate extends React.Component {
  submitHandler = async event => {
    event.preventDefault();

    let token = await AdminService.impersonate(this.props.id);
    if (token) {
      sessionStorage.setItem(
        'token',
        `Bearer ${token.headers['authorization']}`
      );
      sessionStorage.setItem('userId', token.data.id);
      sessionStorage.setItem('userName', token.data.name);
      this.props.history.push('/dashboard');
    } else {
      alert('Вы не можете войти под этим пользователем');
    }
  };

  render() {
    return (
      <form onSubmit={this.submitHandler}>
        <Button color="primary">Войти</Button>
      </form>
    );
  }
}

export default withRouter(Impersonate);
