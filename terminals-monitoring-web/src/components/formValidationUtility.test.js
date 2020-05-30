import formValidationUtility from './formValidationUtility';

it('password is correct (between minLength and maxLength)', () => {
  //arrange
  let validation = {
    minLength: 6,
    maxLength: 16
  };
  let value = 'danDAN_@123dan';
  //act
  let isValidation = formValidationUtility.validateControl(value, validation);
  //assert
  expect(isValidation).toBe(true);
});

it('short password', () => {
  //arrange
  let validation = {
    minLength: 6
  };
  let value = 'danD';
  //act
  let isValidation = formValidationUtility.validateControl(value, validation);
  //assert
  expect(isValidation).toBe(false);
});

it('long password', () => {
  //arrange
  let value =
    'danD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD13123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD1223123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsfdanD123123@!_aasdDA!@#DSsf';
  let validation = {
    maxLength: 16
  };
  //act
  let isValidation = formValidationUtility.validateControl(value, validation);
  //assert
  expect(isValidation).toBe(false);
});

it('password is incorrect (between minLength and maxLength)', () => {
  //arrange
  let validation = {
    password: true,
    minLength: 6,
    maxLength: 16
  };
  let value = 'danDANdan';
  //act
  let isValidation = formValidationUtility.validateControl(value, validation);
  //assert
  expect(isValidation).toBe(false);
});

it('correct mail', () => {
  //arrange
  let value = 'qa@qa.qa';
  let validation = {
    email: true
  };
  //act
  let isValidation = formValidationUtility.validateControl(value, validation);
  //assert
  expect(isValidation).toBe(true);
});

it('incorrect mail', () => {
  //arrange
  let value = 'qaqa.qa';
  let validation = {
    email: true
  };
  //act
  let isValidation = formValidationUtility.validateControl(value, validation);
  //assert
  expect(isValidation).toBe(false);
});
