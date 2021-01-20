import React, { Component } from 'react'
import axios from 'axios'

export default class Ip extends Component {

    constructor(props) {
        super(props);

        this.state = {
            id: this.props.ip.id,
            ipNumber: this.props.ip.ipNumber
        };
        this.deleteIp = this.deleteIp.bind(this);
        this.changehandler = this.changehandler.bind(this);
        this.handleIp = this.handleIp.bind(this);
    }
    changehandler = (event) => {
        console.log("änderung")
        this.setState({ [event.target.name]: event.target.value })
    }

    deleteIp = (event) => {
        event.preventDefault();
        console.log(this.state);
        axios.delete("http://localhost:8080/IpNumber", { data: this.state })
            .then(response => {
                console.log(response);
                // console.log(contract.id);
            })
            .catch(error => {
                console.log(error);
            })
    }

    handleIp = event => {
        event.preventDefault();
        console.log(this.state);
        axios.post('http://localhost:8080/IpNumber', this.state)
            .then(response => {
                console.log(response)
            })
            .catch(error => {
                console.log(error)
            })
    }


    render() {
        return (
            <div key={this.state.id}>
                <input type="text" name="ipNumber" value={this.state.ipNumber} onChange={this.changehandler}></input>
                <button className="btn btn-danger" onClick={this.deleteIp}>Ip Löschen</button>
                <button className="btn btn-dark" onClick={this.handleIp}>Ip ändern</button>
            </div>
        )
    }
}
