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
        this.setState({ [event.target.name]: event.target.value })
    }
    handleSubmit = event => {
        
        event.preventDefault();
        // this.props.CallbackToCompanyJS(this.state);

        axios.post('http://localhost:8080/company', this.state)
            .then(response => {
                console.log(response)
                this.props.CallbackToCompanyJS(this.state);
            })
            .catch(error => {
                console.log(error)
            })

    render() {
        const { name, department, street, postalcode, state, country } = this.state;
        return (
            <div>
                <legend>Edit Company:</legend>
                <form onSubmit={this.handleSubmit}>
                    <div>
                        <div className="container"  >
                            <h1 className="title">{name}</h1>
                            <div className=" form-row ">
                                <div className="form-group col-12 col-sm-6 my-2 p-2 ">
                                    <label> Name</label>
                                    <input type="text" className="form-control1" name="name" value={name} onChange={this.Changehandler}></input>
                                </div>
                                <div className="form-group col-12 col-sm-6 my-2 p-2">
                                    <label> Department </label>
                                    <input type="text" className="form-control1" name="department" value={department} onChange={this.Changehandler}></input>
                                </div>
                            </div>
                            <div className=" form-row ">
                                <div className="form-group col-12 col-sm-6 my-2 p-2">
                                    <label> Street </label>
                                    <input type="text" className="form-control1" name="street" value={street} onChange={this.Changehandler}></input>

                                </div>
                                <div className=" form-group col-12 col-sm-6 my-2 p-2">
                                    <label> postalcode </label>
                                    <input type="number" className="form-control1" name="postalcode" value={postalcode} onChange={this.Changehandler}></input>
                                </div>
                            </div>
                            <div className=" form-row ">
                                <div className="form-group col-12 col-sm-6 my-2 p-2">
                                    <label> state </label>
                                    <input type="text" className="form-control1" name="state" value={state} onChange={this.Changehandler}></input>
                                </div>
                                <div className="form-group col-12 col-sm-6 my-2 p-2">
                                    <label> country </label>
                                    <input type="text" className="form-control1" name="country" value={country} onChange={this.Changehandler}></input>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="mt-4 text-center">
                        <button type="submit" class=" btn-primary1 ">Edit</button>
                    </div>
                </form>

            </div>
        );
    }
}

export default EditCompany;