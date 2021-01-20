import React from 'react';
import axios from 'axios'
class AddCompany extends React.Component {
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
          <div>
          <form>
          <div>
          <div className="container"  >
          <h1 className="title">My Company</h1>             
            <div className=" form-row ">
              <div className="form-group col-12 col-sm-6 my-2 p-2 ">
              <label> Name</label>
              <input
                placeholder="Name"
                className="form-control "
                name="name"
                type="text" />
             </div>
             <div className=" col-12 col-sm-6 my-2 p-2">
              <label> Department </label>
              <input
                placeholder="Department"
                className="form-control"
                name="department"
                type="text"/>
              </div>
            </div>
          <div className=" form-row ">
           <div className=" col-12 col-sm-6 my-2 p-2">
              <label> Street </label>
              <input
                 placeholder="Street"
                 className="form-control"
                 name="street"
                 type="text"/>
                
             </div>
           <div className=" col-12 col-sm-6 my-2 p-2">
              <label> state </label>
              <input
              placeholder="state"
              className="form-control"
              name="state"
              type="text" />
              
             </div>
             </div>
             <div className=" form-row ">
           <div className=" col-12 col-sm-6 my-2 p-2">
              <label> Country </label>
              <input
                  placeholder="Country"
                  className="form-control"
                  name="Country"
                  type="text" />
             </div>
           <div className=" col-12 col-sm-6 my-2 p-2">
              <label> postalcode </label>
              <input
                placeholder="postalcode"
                className="form-control"
                name="postalcode"
                type="number" />
             </div>
             </div>
             </div>
             </div>
                <div className="mt-4 text-center">
                    <button type="submit" class="btn btn-primary btn-lg">hinzufügen</button>
               </div>
            </form>  

          </div>
        );
    }
}

export default AddCompany;