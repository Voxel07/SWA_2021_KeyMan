import React from 'react';
import axios from 'axios'
class EditCompany extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            id: this.props.company.id,
            name: this.props.company.name,
            department: this.props.company.department,
            street: this.props.company.street,
            postalcode: this.props.company.postalcode,
            state: this.props.company.state,
            country: this.props.company.country
        };
    }

    Changehandler = (event) => {
        console.log("änderung")
        this.setState({ [event.target.name]: event.target.value })
    }
    handleSubmit = event => {
        event.preventDefault();
        axios.post('http://localhost:8080/company', this.state)
            .then(response => {
                console.log(response)
            })
            .catch(error => {
                console.log(error)
            })
        }

        render() {
            const { name, department, street, postalcode, state, country } = this.state;
            return (
                <div>
                    <legend>Edit Company: {name}</legend>
                    <form onSubmit={this.handleSubmit}>
                    <div>
                    <div className="container"  >
                    <h1 className="title">Edit Company</h1>             
                        <div className=" form-row ">
                        <div className="form-group col-12 col-sm-6 my-2 p-2 ">
                        <label> Name</label>
                        <input type="text" name="name" value={name} onChange={this.Changehandler}></input>
                        </div>
                        <div className=" col-12 col-sm-6 my-2 p-2">
                        <label> Department </label>
                        <input type="text" name="department" value={department} onChange={this.Changehandler}></input>
                        </div>
                        </div>
                    <div className=" form-row ">
                    <div className=" col-12 col-sm-6 my-2 p-2">
                        <label> Street </label>
                        <input type="text" name="street" value={street} onChange={this.Changehandler}></input>
                            
                        </div>
                    <div className=" col-12 col-sm-6 my-2 p-2">
                        <label> postalcode </label>
                        <input type="number" name="postalcode" value={postalcode} onChange={this.Changehandler}></input>
                        </div>
                        </div>
                        <div className=" form-row ">
                    <div className=" col-12 col-sm-6 my-2 p-2">
                        <label> state </label>
                        <input type="text" name="state" value={state} onChange={this.Changehandler}></input>
                        </div>
                    <div className=" col-12 col-sm-6 my-2 p-2">
                        <label> country </label>
                        <input type="text" name="country" value={country} onChange={this.Changehandler}></input>
                        </div>
                        </div>
                        </div>
                        </div>
                            <div className="mt-4 text-center">
                                <button type="submit" class="btn btn-primary btn-lg">Edit</button>
                        </div>
                        </form>  

          </div>
            );
        }
    }

    export default EditCompany;