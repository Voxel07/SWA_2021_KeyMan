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
        this.setState({ [event.target.name]: event.target.value })
    }

    deleteIp = (event) => {
        event.preventDefault();
        axios.delete("http://localhost:8080/IpNumber", { data: this.state })
            .then(response => {
                this.props.cbToEditCon('Ip');
            })
            .catch(error => {
            })
    }

    handleIp = event => {
        event.preventDefault();
        axios.post('http://localhost:8080/IpNumber', this.state)
            .then(response => {
                this.props.cbToEditCon('Ip');
            })
            .catch(error => {
            })
    }


    render() {
        return (
            <div key={this.state.id}>
                <div className=" form-row ">
                    <div className=" col-12 col-sm-2 my-2 p-2">
                        <input type="text" name="ipNumber" className="form-control1" value={this.state.ipNumber} onChange={this.changehandler}></input>
                    </div>
                    <div class="btn-group col-12 col-sm-4 my-2 p-2">
                        <button className=" btn-danger1" onClick={this.deleteIp}>Ip Löschen</button>
                        <button className=" btn-dark1" onClick={this.handleIp}>Ip ändern</button>
                    </div>
                </div>
            </div>
        )
    }
}
