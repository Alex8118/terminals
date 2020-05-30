import React from 'react';
import { Collapse, Navbar, NavbarToggler, NavbarBrand, Nav } from 'reactstrap';
import MenuDropdown from './menuDropdown';

export default class NavigationHeader extends React.Component {
  constructor(props) {
    super(props);

    this.toggle = this.toggle.bind(this);
    this.state = {
      isOpen: false
    };
  }

  toggle() {
    this.setState({
      isOpen: !this.state.isOpen
    });
  }

  render() {
    return (
      <Navbar
        color="prime"
        dark
        expand="md"
        fixed={`top`}
        className="navbar-expand-lg for-nav"
      >
        <NavbarBrand>Доброматы</NavbarBrand>
        <NavbarToggler onClick={this.toggle} />
        <Collapse isOpen={this.state.isOpen} navbar>
          <Nav className="ml-auto" navbar>
            <MenuDropdown name={this.props.name} />
          </Nav>
        </Collapse>
      </Navbar>
    );
  }
}
