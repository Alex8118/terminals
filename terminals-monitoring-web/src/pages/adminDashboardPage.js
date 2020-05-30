import React from 'react';
import NavigationHeader from '../components/navigationHeader';
import DashboardUsersTable from '../components/dashboardUsersTable';
import userService from '../services/userService';
import { Alert } from 'reactstrap';

export default class AdminDashboardPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      users: [],
      page: 0,
      size: 100
    };
  }

  async componentDidMount() {
    userService
      .getUsers(this.state.page, this.state.size)
      .then(response => {
        this.setState({
          users: response.data.content
        });
      })
      .catch(error => {
        console.log(error);
      });
  }

  render() {
    const usersExist = this.state.users.length > 0;
    return (
      <div>
        <NavigationHeader name={sessionStorage.getItem('userName')} />
        <div className="m-t-56px">
          {usersExist ? (
            <DashboardUsersTable users={this.state.users} />
          ) : (
            <Alert color="info">Пользователей для отображения нет</Alert>
          )}
        </div>
      </div>
    );
  }
}
