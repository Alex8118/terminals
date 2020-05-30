import React from 'react';
import { Table } from 'reactstrap';
import Switches from './swithes';
import AdminService from '../services/adminService';
import Impersonate from './impersonate';

export default class DashboardUsersTable extends React.Component {
  handleSwitch = async (userId, locked) => {
    await AdminService.lockedPatch(userId, locked);
  };

  render() {
    return (
      <section>
        <Table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Имя</th>
              <th>Блокировка</th>
              <th>Имперсонация</th>
            </tr>
          </thead>
          <tbody>
            {this.props.users.map(u => (
              <tr key={u.id}>
                <td>{u.id}</td>
                <td>{u.name}</td>
                <td>
                  <Switches
                    defaultValue={u.locked}
                    onChange={(el, locked) => this.handleSwitch(u.id, locked)}
                  />
                </td>
                <td>
                  <Impersonate id={u.id} />
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      </section>
    );
  }
}
