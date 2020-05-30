import React from 'react';
import { Button, FormGroup } from 'reactstrap';
import Input from './input';
import formValidationUtility from './formValidationUtility';

export default class Auth extends React.Component {
  state = {
    isFormValid: false
  };

  onChangeHandler = (event, controlName) => {
    const control = this.props.data.formControls[controlName];
    let valid = control.valid;
    if (!valid) {
      let validHasChanged = formValidationUtility.validateControl(
        event.target.value,
        control.validation
      );
      if (validHasChanged) {
        valid = validHasChanged;
      }
    }
    this.props.onControlChanged(controlName, event.target.value, valid);
  };

  onBlurHandler = (event, controlName) => {
    const control = this.props.data.formControls[controlName];
    const valid = formValidationUtility.validateControl(
      control.value,
      control.validation
    );
    this.props.onControlChanged(controlName, control.value, valid);
  };

  componentDidUpdate(prevProps) {
    if (this.props.data.formControls !== prevProps.data.formControls) {
      let isFormValid = true;
      Object.values(this.props.data.formControls).forEach(control => {
        isFormValid =
          isFormValid &&
          formValidationUtility.validateControl(
            control.value,
            control.validation
          ) &&
          control.value.trim().length > 0;
      });
      this.setState({
        isFormValid
      });
    }
  }

  renderInputs() {
    return Object.entries(this.props.data.formControls).map(
      ([controlName, control], index) => {
        return (
          <Input
            key={controlName + index}
            id={control.type + index}
            type={control.type}
            value={control.value}
            valid={control.valid}
            label={control.label}
            errorMessage={control.errorMessage}
            onBlur={event => this.onBlurHandler(event, controlName)}
            onChange={event => this.onChangeHandler(event, controlName)}
          />
        );
      }
    );
  }

  render() {
    return (
      <section>
        <form
          onSubmit={event =>
            this.props.submitHandler(event, this.state.isFormValid)
          }
        >
          <div className="container">
            <div className="row">
              <div className="col-sm-9 col-md-7 col-lg-5 mx-auto">
                <div className="card-signin my-5">
                  <div className="card-body">
                    <h5 className="card-title text-center">
                      {this.props.title}
                    </h5>
                    <FormGroup className="form-signin">
                      {this.renderInputs()}
                      <Button
                        type="submit"
                        color="primary"
                        className="btn btn-lg text-uppercase"
                      >
                        {this.props.buttonText}
                      </Button>
                      <div
                        className="pd-t-5"
                        style={{ display: 'flex', justifyContent: 'center' }}
                      >
                        {this.props.transition}
                      </div>
                    </FormGroup>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </form>
      </section>
    );
  }
}
