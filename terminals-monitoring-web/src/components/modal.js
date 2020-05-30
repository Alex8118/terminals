import React from 'react';
import {
  Button,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
  Table
} from 'reactstrap';
import statusHistoryService from '../services/statusHistoryService';
import dateUtility from './dateUtility';
import moment from 'moment-timezone';

export default class Auth extends React.Component {
  constructor(props) {
    super(props);
    this.toggle = this.toggle.bind(this);
    this.state = {
      statuses: [],
      timeZone: moment.tz.guess(),
      isOpen: false
    };
  }

  toggle() {
    this.setState({
      isOpen: !this.state.isOpen
    });
  }

  componentDidMount() {
    statusHistoryService
      .getStatusHistory(this.props.terminalId, 0, 10)
      .then(response => {
        this.setState({
          statuses: response.data.content
        });
      })
      .catch(error => {
        console.log(error);
      });
  }

  render() {
    return (
      <div>
        {this.props.status ? (
          <Button color="success" onClick={this.toggle} size="sm">
            онлайн
          </Button>
        ) : (
          <Button color="danger" onClick={this.toggle} size="sm">
            офлайн
          </Button>
        )}
        <Modal isOpen={this.state.isOpen} toggle={this.toggle}>
          <ModalHeader toggle={this.toggle}>
            История статусов терминала {this.props.terminalId}
          </ModalHeader>
          <ModalBody>
            <Table>
              <thead>
                <tr>
                  <th>Дата</th>
                  <th>
                    Время (
                    {moment()
                      .tz(this.state.timeZone)
                      .format('Z')}
                    )
                  </th>
                </tr>
              </thead>
              <tbody>
                {this.state.statuses.map(s => (
                  <tr key={s.id}>
                    <td>
                      {dateUtility.getDate(
                        s.lastSignalDate,
                        this.state.timeZone
                      )}
                    </td>
                    <td>
                      {dateUtility.getTime(
                        s.lastSignalDate,
                        this.state.timeZone
                      )}
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          </ModalBody>
          <ModalFooter>
            <Button color="info" onClick={this.toggle}>
              Назад
            </Button>
          </ModalFooter>
        </Modal>
      </div>
    );
  }
}
