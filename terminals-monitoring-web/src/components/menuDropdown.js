import React from 'react';
import {
  UncontrolledDropdown,
  DropdownToggle,
  DropdownMenu,
  DropdownItem,
  Button
} from 'reactstrap';

export default class MenuDropdown extends React.Component {
  render() {
    return (
      <UncontrolledDropdown setActiveFromChild>
        <DropdownToggle caret>{this.props.name}</DropdownToggle>
        <DropdownMenu>
          <DropdownItem href="/dashboard">Панель инструментов</DropdownItem>
          <DropdownItem href="/settings">Настройки</DropdownItem>
          <hr />
          <DropdownItem href="/">
            <Button outline color="danger" block>
              Выйти
            </Button>
          </DropdownItem>
        </DropdownMenu>
      </UncontrolledDropdown>
    );
  }
}
