import React, { Component } from 'react'
import axios from 'axios'

export default class Feature extends Component {

    constructor(props) {
        super(props);
    
        this.state = {
            id: this.props.Feature.id,
            number: this.props.Feature.number
        };
        this.handleEdit = this.handleEdit.bind(this);
        this.deleteContract = this.deleteContract.bind(this);
        this.changehandler = this.changehandler.bind(this);
    }
    changehandler = (event) => {
        console.log("änderung")
        this.setState({ [event.target.name]: event.target.value })
    }
    deleteFeature() {
        console.log(this.state);
        axios.delete("http://localhost:8080/Feature", { data: this.state })
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
            <div key ={this.state.Feature}>
                <input type="text" name="Feature" value={this.state.number} onChange={this.changehandler}></input>
                <button className="btn btn-danger" onClick={() => this.deleteFeature()}>Feature Löschen</button>
            </div>
        )
    }
}
