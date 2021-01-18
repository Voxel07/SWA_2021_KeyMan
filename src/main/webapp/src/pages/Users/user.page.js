import React from 'react';
import User from './user.component';
import axios from 'axios';

class Users extends React.Component {
    constructor() {
        super();
        this.state = { users: [], msg:"" };
    }

    /* componentWillMount(){
        const user1 = {customer: "CustomerA", userName: "User1", email: "user1@custa.com"};
        const user2 = {customer: "CustomerB", userName: "User2", email: "user2@custb.com"};

        this.setState(state => {
            const list = state.users.push(user1);
        });
        this.setState(state => {
            const list = state.users.push(user2);
        });

        console.log(this.state.users);
    }
    */

    componentDidMount() {
        axios.get('http://localhost:8080/User')
            .then(response => {
                console.log(response);
                if( response.data.length === 0)
                {
                    this.setState({ msg: 'Keine Daten erhalten' })
                } else {
                    this.setState({ users: response.data });
                }

            })
            .catch(error => {
                // console.log(error);
                this.setState({ msg: " "+error})
            })
    }
    
    
    deleteUser() {
        
    }

    editUser() {
        
    }
    
    render() {
        return (
            <div>
                {this.state.users.length ? this.state.users.map(user => (
                    <User customer={user.customer} userName={user.userName} email={user.email}/>
                )) : this.state.msg ? this.state.msg : null }
            </div>
        );
    }
}

export default Users;