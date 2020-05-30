import axios from 'axios';

const instance = axios.create({
  baseURL: 'http://localhost:8081/api/v1/'
});

export default {
  async lockedPatch(ownerId, locked) {
    let token = sessionStorage.getItem('token');
    return await instance
      .patch(
        `users/${ownerId}`,
        {
          locked: locked
        },
        {
          headers: {
            'Content-Type': 'application/json',
            Authorization: token
          }
        }
      )
      .then(function(response) {
        return response;
      })
      .catch(function(error) {
        console.log(error);
        return null;
      });
  },

  async impersonate(ownerId) {
    let token = sessionStorage.getItem('token');
    return await instance
      .get(`users/${ownerId}/impersonate`, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: token
        }
      })
      .then(function(response) {
        if (response.status === 200) {
          return response;
        } else {
          return null;
        }
      })
      .catch(function(error) {
        console.log(error);
        return null;
      });
  }
};
