import dateUtility from './dateUtility';

it('date will be in YYYY-MM-DD format', () => {
  //arrange
  let lastSignalDate = '2020-01-26T02:17:30.000+0000';
  let timeZone = 'America/New_York';
  //act
  let actualDate = dateUtility.getDate(lastSignalDate, timeZone);
  //assert
  expect(actualDate).toBe('2020-01-25');
});

it('date will be in YYYY-MM-DD format', () => {
  //arrange
  let lastSignalDate = '2020-01-26T10:17:30.000+0000';
  let timeZone = 'Europe/Berlin';
  //act
  let actualDate = dateUtility.getDate(lastSignalDate, timeZone);
  //assert
  expect(actualDate).toBe('2020-01-26');
});

it('time will be in HH:mm:ss format', () => {
  //arrange
  let lastSignalDate = '2020-01-26T10:17:30.000+0000';
  let timeZone = 'Europe/Berlin';
  //act
  let newTime = dateUtility.getTime(lastSignalDate, timeZone);
  //assert
  expect(newTime).toBe('11:17:30');
});
