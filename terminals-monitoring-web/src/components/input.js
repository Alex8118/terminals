import React from 'react';
import { Label } from 'reactstrap';

export default class Input extends React.Component {
  render() {
    return (
      <div className="form-label-group">
        <input
          className="form-control"
          placeholder=" "
          type={this.props.type}
          id={this.props.id}
          value={this.props.value}
          onBlur={this.props.onBlur}
          onChange={this.props.onChange}
        />
        <Label htmlFor={this.props.id}>{this.props.label}</Label>
        {!this.props.valid ? (
          <div className="errorMessage">{this.props.errorMessage}</div>
        ) : null}
      </div>
    );
  }
}
