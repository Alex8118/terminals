import is from 'is_js';

export default {
  validateControl(value, validation) {
    let isValid = true;
    if (validation.email) {
      isValid = is.email(value) && isValid;
    }
    if (validation.password) {
      const check = /(?=.*[a-z])(?=.*d)(?=.*[@#$%])(?=.*[A-Z])/;
      isValid = check.test(value) && isValid;
    }
    if (validation.minLength) {
      isValid = value.trim().length >= validation.minLength && isValid;
    }
    if (validation.maxLength) {
      isValid = value.trim().length <= validation.maxLength && isValid;
    }
    return isValid;
  }
};
