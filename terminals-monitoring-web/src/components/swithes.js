import React from 'react';
import Switch from 'react-bootstrap-switch';

export default class Switches extends React.Component {
  render() {
    return (
      <Switch
        defaultValue={this.props.defaultValue}
        onChange={this.props.onChange}
        offText="Разблокирован"
        offColor="success"
        onText="Заблокирован"
        onColor="danger"
        className="bootstrap-switch"
      />
    );
  }
}
