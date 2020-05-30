import moment from 'moment-timezone';

export default {
  getDate(lastSignalDate, timezone) {
    return moment(lastSignalDate)
      .tz(timezone)
      .format('YYYY-MM-DD');
  },

  getTime(lastSignalDate, timezone) {
    return moment(lastSignalDate)
      .tz(timezone)
      .format('HH:mm:ss');
  }
};
