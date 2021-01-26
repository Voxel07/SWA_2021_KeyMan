import React, { Component } from 'react'
import axios from 'axios'

export default class Phone extends Component {

    constructor(props) {
        super(props);

        this.state = {
            id: this.props.phone.id,
            number: this.props.phone.number,
            type: this.props.phone.type,
        };
        this.changehandler = this.changehandler.bind(this);
        this.deletePhone = this.deletePhone.bind(this);
        this.handlePhone = this.handlePhone.bind(this);
    }

    changehandler = (event) => {
        this.setState({ [event.target.name]: event.target.value })
    }

    deletePhone = event => {
        event.preventDefault();
        axios.delete("http://localhost:8080/phone", { data: this.state })
            .then(response => {
                this.props.cbToEditUser();
            })
            .catch(error => {
            })
    }

    handlePhone = event => {
        event.preventDefault();
        axios.post('http://localhost:8080/phone', this.state)
            .then(response => {
                this.props.cbToEditUser();
            })
            .catch(error => {
            })
    }

    render() {
        return (
            <div key={this.state.id}>
                <div className=" form-row ">
                    <div className=" form-group col-12 col-sm-2 ">
                        <label>Number</label>
                        <input type="number" name="number" className="form-control1" value={this.state.number} onChange={this.changehandler}></input>
                    </div>
                    <div className=" form-group col-12 col-sm-2 ">
                        <label>Type</label>
                        <input type="text" name="type" className="form-control1" value={this.state.type} onChange={this.changehandler}></input>
                    </div>
                    <div class="btn-group col-12 col-sm-7 my-3 p-3 ">
                        <button className="  btn-danger1" onClick={this.deletePhone}>Phone Löschen</button>
                        <button className="  btn-dark1" onClick={this.handlePhone}>Phone ändern</button>
                    </div>
                </div>
            </div>
        )
    }
}
