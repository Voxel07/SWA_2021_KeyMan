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
        console.log("änderung")
        this.setState({ [event.target.name]: event.target.value })
    }

    deleteFeature = event => {
        event.preventDefault();
        console.log(this.state);
        axios.delete("http://localhost:8080/feature", { data: this.state })
            .then(response => {
                console.log(response);
                // console.log(contract.id);
            })
            .catch(error => {
                console.log(error);
            })
    }

    handleFeature = event => {
        event.preventDefault();
        console.log(this.state);
        axios.post('http://localhost:8080/feature', this.state)
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
                        <input type="number" name="Feature" className="form-control"value={this.state.number} onChange={this.changehandler}></input>
                        </div>
                      
                        
                        
                        <div class="btn-group col-12 col-sm-2 my-2 p-2">
                        <button className="btn btn-danger" onClick={this.deleteFeature}>Feature Löschen</button>
                                               
                        <button className="btn btn-dark" onClick={this.handleFeature}>Feature ändern</button>
                         </div>  
                         </div>  
                       
                         
                          
                
            </div>
        )
    }
}
