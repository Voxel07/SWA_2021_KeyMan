import React from 'react';
import axios from 'axios'
class AddCustomer extends React.Component {
    constructor() {
        super();
        this.state = { name: '',
        department:'',
        street:'',
        postalcode:'',
        state:'',
        country:'' };

        this.handleSubmit = this.handleSubmit.bind(this);
    }
    Changehandler = (event) =>{
        this.setState({[event.target.name]:event.target.value})
      }

    handleSubmit = event => {
    event.preventDefault();
    axios.put('http://localhost:8080/company',this.state)
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
    //Nicht getestet 
    ClearInput(){
        this.setState({name: ''})
        this.setState({department: ''})
        this.setState({street: ''})
        this.setState({postalcode: ''})
        this.setState({state: ''})
        this.setState({country: ''})
    }
    
    
    render() {
        const{name,department,street,postalcode,state,country} = this.state
        return (
            <div className="customer-form">
            <form  onSubmit={this.handleSubmit}>
              <div >
                <div className="user-input">
                  <label>
                    Name
                </label>
                  <input
                    placeholder="Name"
                    name="name"
                    type="text"
                    value={name} onChange={this.Changehandler} />
                </div>
                <div className="user-input">
                  <label>
                  Department
                </label>
                  <input
                    placeholder="Department"
                    name="department"
                    type="text"
                    value={department} onChange={this.Changehandler} />
                </div>
                <div className="user-input">
                  <label>
                  Street
                </label>
                  <input
                    placeholder="Street"
                    name="street"
                    type="text"
                    value={street} onChange={this.Changehandler} />
                </div>
                <div className="user-input">
                  <label>
                  Postalcode
                </label>
                  <input
                    placeholder="Postalcode"
                    name="postalcode"
                    type="number"
                    value={postalcode} onChange={this.Changehandler} />
                </div>
                <div className="user-input">
                  <label>
                  State
                </label>
                  <input
                    placeholder="State"
                    name="state"
                    type="text"
                    value={state} onChange={this.Changehandler} />
                </div>
                <div className="user-input">
                  <label>
                  Country
                </label>
                  <input
                    placeholder="Country"
                    name="country"
                    type="text"
                    value={country} onChange={this.Changehandler} />
                </div>
    
                <div> <input type="submit" value="Submit"/></div>
              </div>
    
    
            </form>
          </div>
        );
    }
}

export default AddCustomer;