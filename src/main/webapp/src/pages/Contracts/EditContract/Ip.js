import React, { Component } from 'react'
import axios from 'axios'

export default class Ip extends Component {

    constructor(props) {
        super(props);
    
        this.state = {
            id: this.props.Ip.id,
            number: this.props.Ip.ipNumber
        };
        this.deleteIp = this.deleteIp.bind(this);
        this.changehandler = this.changehandler.bind(this);
    }
    changehandler = (event) => {
        console.log("änderung")
        this.setState({ [event.target.name]: event.target.value })
    }
    deleteIp() {
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
   

    render() {
        return (
            <div key ={this.state.ip}>
                <input type="text" name="IpNumber" value={this.state.ipNumber} onChange={this.changehandler}></input>
                <button className="btn btn-danger" onClick={() => this.deleteIp()}>Ip Löschen</button>
            </div>
        )
    }
}
