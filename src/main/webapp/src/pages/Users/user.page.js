import React from 'react';
import axios from 'axios';
import User from '../../components/User'


class UserPage extends React.Component {
    constructor() {
        super();
        this.state = { users: [], errorMsg:"" };
    }

    componentWillMount() {
        axios.get('http://localhost:8080/user')
            .then(response => {
                console.log("getuser: "+ response.data);
                if( response.data.length === 0)
                {
                    this.setState({ errorMsg: 'Keine user Daten erhalten' })
                } else {
                    this.setState({ users: response.data });
                }

            })
            .catch(error => {
                // console.log(error);
                this.setState({ errorMsg: " "+error})
            })
    }
    
    render() {
        const { users, errorMsg } = this.state
        return (
        <div> 
        {
            users.length ? users.map(user => <User user={user} />) : null
        }
        {
            errorMsg ? <div>{errorMsg}</div> : null
        } 
        </div>
        )
    }
}

export default UserPage;