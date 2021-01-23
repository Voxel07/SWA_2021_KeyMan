import React, { Component } from 'react'
import axios from 'axios'
import User from '../../components/User'

export default class ShowUsers extends Component {
    constructor(props) {
        super(props)

        this.state = {
            Users: [],
            errorMsg: ''
        }
    }
    componentDidMount() {
        axios.get('http://localhost:8080/user', {params:{company_id: this.props.company.id}})
            .then(response => {
                console.log(response);
                this.setState({ Users: response.data });
                if( response.data.length === 0)
                {
                    this.setState({ errorMsg: 'Keine User Daten erhalten' })
                }

            })
            .catch(error => {
                // console.log(error);
                this.setState({ errorMsg: " "+error})
            })
    }

    render() {
        const { Users, errorMsg } = this.state
        return (
        <div> 
        {
            Users.length ? Users.map(user => <User user={user} />) : null
        }
        {
            errorMsg ? <div>{errorMsg}</div> : null
        } 
        </div>
        )
    }
}