import React, { Component } from 'react'
import axios from 'axios'
import User from '../../components/User'

export default class ShowUsers extends Component {
    constructor(props) {
        super(props)

        this.state = {
            users: [],
            errorMsg: ''
        }
    }
	componentWillMount() {
		this.fetchUsers();
	}
	
	fetchUsers() {
        axios.get('http://localhost:8080/user', {params:{companyId: this.props.company.id}})
            .then(response => {
                console.log(response);
                this.setState({ users: response.data });
                if( response.data.length == 0)
                {
                    this.setState({ errorMsg: 'Keine User Daten erhalten' })
                }

            })
            .catch(error => {
                console.log(error);
                this.setState({ errorMsg: " "+error})
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
            this.setState({users: newList})
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
            this.fetchUsers();
        }

    }

    render() {
        const { users, errorMsg } = this.state
        return (
        <div> 
        {
            users.length ? users.map(user => <User user={user} parentCallback={this.handleCallback}/>) : null
        }
        {
            errorMsg ? <div>{errorMsg}</div> : null
        } 
        </div>
        )
    }
}