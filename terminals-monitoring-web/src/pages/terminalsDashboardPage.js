import React from 'react';
import NavigationHeader from '../components/navigationHeader';
import DashboardTerminalsTable from '../components/dashboardTerminalsTable';
import TerminalsService from '../services/terminalsService';
import { Alert } from 'reactstrap';

export default class TerminalsDashboardPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      terminals: [],
      page: 0,
      size: 100
    };
  }

  async componentDidMount() {
    TerminalsService.getTerminalData(
      sessionStorage.getItem('userId'),
      this.state.page,
      this.state.size
    )
      .then(response => {
        this.setState({
          terminals: response.data.content
        });
      })
      .catch(error => {
        console.log(error);
      });
  }

  render() {
    const terminalsExist = this.state.terminals.length > 0;
    return (
      <div>
        <NavigationHeader name={sessionStorage.getItem('userName')} />
        <div className="m-t-56px">
          {terminalsExist ? (
            <DashboardTerminalsTable terminals={this.state.terminals} />
          ) : (
            <Alert color="info">Терминалов для отображения нет</Alert>
          )}
        </div>
      </div>
    );
  }
}
