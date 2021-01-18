const API = "http://localhost:8081/"

export const getUsers = (body) => {
    return fetch({
      method: 'GET',
      url: `${API}/users/`,
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(body),
    });
  };