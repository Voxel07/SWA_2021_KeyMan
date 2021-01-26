import React from 'react';
import axios from 'axios';
import User from '../../components/User'


class UserPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
            errorMsg: ""
        };
        this.handleRemove = this.handleRemove.bind(this);
        this.handleCallback = this.handleCallback.bind(this);
    }

    componentDidMount() {
        this.fetchUser();
    }

    fetchUser() {
        axios.get('http://localhost:8080/user')
            .then(response => {
                if (response.data.length === 0) {
                    this.setState({ errorMsg: 'Keine user Daten erhalten' })
                } else {
                    this.setState({ users: response.data, errorMsg: '' });
                }
            })
            .catch(error => {
                this.setState({ errorMsg: " " + error })
            })
    }

    handleCallback = (func, user) => {
        switch (func) {
            case 'DELETE':
                this.handleRemove(user);
                break;
            case 'UPDATE':
                this.handleUpdate(user);
                break;
            default:
                break;
        }
    }
    handleRemove = (user) => {
        const newList = this.state.users.filter((item) => item.id !== user.id);
        this.setState({ users: newList })
    }

    handleUpdate = (user) => {
        const newList = this.state.users.map((item) => {
            if (item.id === user.id) {
                const updatedItem = {
                    email: user.email,
                    username: user.username,
                    companyName: user.companyName,
                    firstName: user.firstName,
                    lastName: user.lastName,
                    password: user.password,
                    id: user.id
                };
                return updatedItem;
            }
            else {
                return item;
            }
        });
        this.setState({ users: newList })
    }

    componentDidUpdate() {
        if (this.props.newUser === true) {
            this.fetchUser();
        }
    }

    render() {
        const { users, errorMsg } = this.state
        return (
            <div>
                {
                    users.length ? users.map(user => <User user={user} parentCallback={this.handleCallback} />) : null
                }
                {
                    errorMsg ? <div>{errorMsg}</div> : null
                }
            </div>
        )
    }
}

export default UserPage;