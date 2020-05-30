import StatusService from '../services/statusService';
import moment from 'moment';

it('the correct status is displayed', () => {
  //arrange
  let lastSignalDate =
    moment()
      .utc()
      .valueOf() - 500000;
  //act
  let newValue = StatusService.getOnlineStatus(lastSignalDate);
  //assert
  expect(newValue).toBe(true);
});

it('the correct status is displayed', () => {
  //arrange
  let lastSignalDate =
    moment()
      .utc()
      .valueOf() - 600060;
  //act
  let newValue = StatusService.getOnlineStatus(lastSignalDate);
  //assert
  expect(newValue).toBe(false);
});

it('the correct status is displayed', () => {
  //arrange
  let lastSignalDate =
    moment()
      .utc()
      .valueOf() - 600000;
  //act
  let newValue = StatusService.getOnlineStatus(lastSignalDate);
  //assert
  expect(newValue).toBe(false);
});
