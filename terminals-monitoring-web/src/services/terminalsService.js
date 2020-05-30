import axios from 'axios';

const instance = axios.create({
  baseURL: 'http://localhost:8081/api/v1/'
});

export default {
  getTerminalData(ownerId, page, size) {
    let token = sessionStorage.getItem('token');
    return instance.get('terminals', {
      params: {
        ownerId: ownerId,
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
