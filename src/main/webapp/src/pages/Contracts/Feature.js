import React, { Component } from 'react'
import axios from 'axios'

export default class Feature extends Component {

    constructor(props) {
        super(props);

        this.state = {
            id: this.props.feature.id,
            number: this.props.feature.number,
        };
        this.changehandler = this.changehandler.bind(this);
        this.deleteFeature = this.deleteFeature.bind(this);
        this.handleFeature = this.handleFeature.bind(this);
    }

    changehandler = (event) => {
        this.setState({ [event.target.name]: event.target.value })
    }

    deleteFeature = event => {
        event.preventDefault();
        axios.delete("http://localhost:8080/feature", { data: this.state })
            .then(response => {
                this.props.cbToEditCon('Feature');
            })
            .catch(error => {
            })
    }

    handleFeature = event => {
        event.preventDefault();
        axios.post('http://localhost:8080/feature', this.state)
            .then(response => {
                this.props.cbToEditCon('Feature');
            })
            .catch(error => {
            })
    }

    render() {
        return (
            <div key={this.state.id}>
                <div className=" form-row ">
                    <div className=" col-12 col-sm-2 my-2 p-2">
                        <input type="text" name="number" className="form-control1" value={this.state.number} onChange={this.changehandler}></input>
                    </div>
                    <div class="btn-group col-12 col-sm-8 my-2 p-2">
                        <button className=" btn-danger1" onClick={this.deleteFeature}>Feature Löschen</button>
                        <button className=" btn-dark1" onClick={this.handleFeature}>Feature ändern</button>
                    </div>
                </div>
            </div>
        )
    }
}
