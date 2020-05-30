import React from 'react';
import StatusService from '../services/statusService';
import { Table, Input } from 'reactstrap';
import Modals from './modal';

export default class DashboardTerminalsTable extends React.Component {
  render() {
    return (
      <section>
        <Table>
          <thead>
            <tr>
              <th></th>
              <th>Номер</th>
              <th>Название</th>
              <th>Город</th>
              <th>Улица</th>
              <th>Дом</th>
              <th>Статус</th>
            </tr>
          </thead>
          <tbody>
            {this.props.terminals.map(t => (
              <tr key={t.id}>
                <th scope="row">
                  <div className="pd-l-2vh">
                    <Input type="checkbox" />
                  </div>
                </th>
                <td>{t.id}</td>
                <td>{t.name}</td>
                <td>{t.city}</td>
                <td>{t.street}</td>
                <td>{t.house}</td>
                <td>
                  <Modals
                    status={StatusService.getOnlineStatus(
                      t.status.lastSignalDate
                    )}
                    terminalId={t.id}
                  />
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      </section>
    );
  }
}
