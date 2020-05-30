import axios from 'axios';

const instance = axios.create({
  baseURL: 'http://localhost:8081/api/v1/'
});

export default {
  getStatusHistory(terminalId, page, size) {
    let token = sessionStorage.getItem('token');
    return instance.get('statuses/history', {
      params: {
        terminalId: terminalId,
        page: page,
        size: size
      },
      headers: {
        'Content-Type': 'application/json',
        Authorization: token
      }
    });
  }
};
