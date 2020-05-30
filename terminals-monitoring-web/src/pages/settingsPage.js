import React from 'react';
import NavigationHeader from '../components/navigationHeader';
import SettingTab from '../components/tab';
import userService from '../services/userService';

export default class SettingsPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      apiToken: ''
    };
  }

  async componentDidMount() {
    userService
      .getToken(sessionStorage.getItem('userId'))
      .then(response => {
        this.setState({
          apiToken: response.data.encryptedApiToken
        });
      })
      .catch(error => {
        console.log(error);
      });
  }

  render() {
    return (
      <div>
        <NavigationHeader name={sessionStorage.getItem('userName')} />
        <div className="m-t-56px">
          <SettingTab token={this.state.apiToken} />
        </div>
      </div>
    );
  }
}
