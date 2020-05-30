import moment from 'moment';

export default {
  getOnlineStatus(signalDate) {
    let nowTime = moment()
      .utc()
      .valueOf();
    let lastTime = moment(signalDate)
      .utc()
      .valueOf();
    let difference = nowTime - lastTime;
    if (difference < 600000) {
      return true;
    } else {
      return false;
    }
  }
};
