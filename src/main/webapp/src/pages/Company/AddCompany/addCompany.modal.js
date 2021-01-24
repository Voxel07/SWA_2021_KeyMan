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
        country:'',
        status: '123'
      };

        this.handleSubmit = this.handleSubmit.bind(this);
    }
    Changehandler = (event) =>{
        this.setState({[event.target.name]:event.target.value})
      }

    handleSubmit = event => {
    event.preventDefault();
    this.props.cbToBar(this.state,true);
    
    // axios.put('http://localhost:8080/company',this.state)
    // .then(response => {
    //   this.setState({status: response.data})
    //     this.ClearInput();
    //     this.props.cbToBar("addCompany",true);
    // })
    // .catch(error => {
    //     // this.setState({errorMsg: 'Keine Daten erhalten'})
    // })

    }
    
    ClearInput(){
        this.setState({name: ''})
        this.setState({department: ''})
        this.setState({street: ''})
        this.setState({postalcode: ''})
        this.setState({state: ''})
        this.setState({country: ''})
    }
    
    
    render() {
        const{name,department,street,postalcode,state,country,status} = this.state
        return (
          <div>
          <form  onSubmit={this.handleSubmit}>
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
                type="text"
                value={name} onChange={this.Changehandler} />
             </div>
             <div className=" col-12 col-sm-6 my-2 p-2">
              <label> Department </label>
              <input
                placeholder="Department"
                className="form-control"
                name="department"
                type="text"
                value={department} onChange={this.Changehandler}/>
              </div>
            </div>
          <div className=" form-row ">
           <div className=" col-12 col-sm-6 my-2 p-2">
              <label> Street </label>
              <input
                 placeholder="Street"
                 className="form-control"
                 name="street"
                 type="text"               
                 value={street} onChange={this.Changehandler}/>
                
             </div>
           <div className=" col-12 col-sm-6 my-2 p-2">
              <label> state </label>
              <input
              placeholder="state"
              className="form-control"
              name="state"
              type="text"
              value={state} onChange={this.Changehandler} />
              
             </div>
             </div>
             <div className=" form-row ">
           <div className=" col-12 col-sm-6 my-2 p-2">
              <label> Country </label>
              <input
                  placeholder="Country"
                  className="form-control"
                  name="country"
                  type="text"
                  value={country} onChange={this.Changehandler} />
             </div>
           <div className=" col-12 col-sm-6 my-2 p-2">
              <label> postalcode </label>
              <input
                placeholder="postalcode"
                className="form-control"
                name="postalcode"
                type="number" 
                value={postalcode} onChange={this.Changehandler}/>
             </div>
             </div>
             </div>
             </div>
             <div>
             {
               status
             }
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