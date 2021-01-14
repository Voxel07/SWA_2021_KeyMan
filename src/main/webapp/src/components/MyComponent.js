import '../css/MyComponent.css'
import React, { Component } from 'react'
import axios from 'axios'
class MyComponent extends Component {
  
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
  //String name, String department, String street, int postalcode, String state, String country
  handleSubmit = event => {
    event.preventDefault();
    var data = JSON.stringify(this.state)
    console.log(data)
    axios.put('http://localhost:8080/company',data)
    .then(response => {
      console.log(response)
      //this.setState({posts: response.data})
  })
  .catch(error => {
      console.log(error)
     // this.setState({errorMsg: 'Keine Daten erhalten'})
  })

  }

  render() {
    const{name,department,street,postalcode,state,country} = this.state
    //oder value = {this.state.name}
    return (
      <div className="user-form">
        <form id="test"  onSubmit={this.handleSubmit}>
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
export default MyComponent;
