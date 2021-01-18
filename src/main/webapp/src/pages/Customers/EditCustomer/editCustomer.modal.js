import React from 'react';
import axios from 'axios'
class EditCustomer extends React.Component {
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
                this.ClearInput();
                //this.setState({posts: response.data})
            })
            .catch(error => {
                console.log(error)
                // this.setState({errorMsg: 'Keine Daten erhalten'})
            })
        }

        render() {
            const { name, department, street, postalcode, state, country } = this.state;
            return (
                <form onSubmit={this.handleSubmit}>
                    <fieldset>
                        <legend>Edit Company: {name}</legend>
                        <label>Company</label>
                        <input type="text" name="name" value={name} onChange={this.Changehandler}></input>
                        <label>Department</label>
                        <input type="text" name="department" value={department} onChange={this.Changehandler}></input>
                        <label>Street</label>
                        <input type="text" name="street" value={street} onChange={this.Changehandler}></input>
                        <label>E-mail</label>
                        <input type="number" name="postalcode" value={postalcode} onChange={this.Changehandler}></input>
                        <label>Phone:</label>
                        <input type="text" name="state" value={state} onChange={this.Changehandler}></input>
                        <label>Mobile:</label>
                        <input type="text" name="country" value={country} onChange={this.Changehandler}></input>
                    </fieldset>
                    <div> <input type="submit" value="Submit" /></div>
                </form>
            );
        }
    }

    export default EditCustomer;