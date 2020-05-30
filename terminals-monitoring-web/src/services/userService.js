import axios from 'axios';

const client = axios.create({
  baseURL: 'http://localhost:8081/api/v1/users'
});

export default {
  async getUserToken(credentials) {
    return await client
      .post('/login', credentials, {
        headers: {
          'Content-Type': 'application/json'
        }
      })
      .then(function(response) {
        if (response.status === 200) {
          return response.headers['authorization'];
        } else {
          return null;
        }
      })
      .catch(function(error) {
        console.log(error);
        return null;
      });
  },

  async submit(credentials) {
    return await client
      .post('', credentials, {
        headers: {
          'Content-Type': 'application/json'
        }
      })
      .then(function(response) {
        return response.status;
      })
      .catch(function(error) {
        return error.response.status;
      });
  },

  async getCurrentUser() {
    let token = sessionStorage.getItem('token');
    return client
      .get('/current', {
        headers: {
          'Content-Type': 'application/json',
          Authorization: token
        }
      })
      .then(function(response) {
        return response.data;
      })
      .catch(function(error) {
        console.log(error);
        return null;
      });
  },

  getUsers(page, size) {
    let token = sessionStorage.getItem('token');
    return client.get('', {
      params: {
        page: page,
        size: size
      },
      headers: {
        'Content-Type': 'application/json',
        Authorization: token
      }
    });
  },

  async getToken(ownerId) {
    let token = sessionStorage.getItem('token');
    return await client.get(`/${ownerId}/token`, {
      headers: {
        'Content-Type': 'application/json',
        Authorization: token
      }
    });
  }
};
