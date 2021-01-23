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
                 <div className=" form-row ">
                        <div className=" col-12 col-sm-2 my-2 p-2">
                        <input type="text" name="ipNumber" className="form-control" value={this.state.ipNumber} onChange={this.changehandler}></input>
                        </div>
                        </div>
                        <div class="btn-group">
                        <button className="btn btn-danger" onClick={this.deleteIp}>Ip Löschen</button>
                        </div>
                        <div class="btn-group">
                        <button className="btn btn-dark" onClick={this.handleIp}>Ip ändern</button>
                         </div>
                       
            </div>
        )
    }
}
