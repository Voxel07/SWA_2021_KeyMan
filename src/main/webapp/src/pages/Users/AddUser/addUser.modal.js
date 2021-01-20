import React from 'react';

class AddUser extends React.Component {
    constructor() {
        super();
        this.state = {  };
    }
    
    render() {
        return (
            <form>
            <div>
            <div className="container"  >
            <h1 className="title">My User</h1>
              <div className=" form-row ">
              <div className="form-group col-6 col-sm-6 my-2 p-2"> 
                <label> Company </label>
                  <select className="custom-select" id="inputGroupSelect01">
                    <option selected>select Company...</option>
                    <option value="1">One</option>       // input of companys
                    <option value="2">Two</option>
                    <option value="3">Two</option>
                  </select>
               </div>
               </div>
               
              <div className=" form-row ">
                <div className="form-group col-12 col-sm-6 my-2 p-2 ">
                <label>First Name</label>
                <input
                  placeholder="Department"
                  className="form-control "
                  name="department"
                  type="text" />
               </div>
               <div className=" col-12 col-sm-6 my-2 p-2">
                <label> Last Name </label>
                <input
                  placeholder="Street"
                  className="form-control"
                  name="street"
                  type="text"/>
                </div>
              </div>
            <div className=" form-row ">
             <div className=" col-12 col-sm-6 my-2 p-2">
                <label> Email </label>
                <input
                  placeholder="state"
                  className="form-control"
                  name="state"
                  type="text" />
               </div>
             <div className=" col-12 col-sm-6 my-2 p-2">
                <label> Phone </label>
                <input
                  placeholder="Country"
                  className="form-control"
                  name="Country"
                  type="text" />
               </div>
               </div>
               <div className=" form-row ">
             <div className=" col-12 col-sm-6 my-2 p-2">
                <label> isAdmin </label>
                <input
                  className="form-control"
                  type="checkbox" />
               </div>
             <div className=" col-12 col-sm-6 my-2 p-2">
                <label> Mobile </label>
                <input
                  placeholder="Country"
                  className="form-control"
                  name="Country"
                  type="text" />
               </div>
               </div>
               </div>
               </div>
                  <div className="mt-4 text-center">
                      <button type="submit" class="btn btn-primary btn-lg">hinzufügen</button>
                 </div>
              </form>  
        );
    }
}

export default AddUser;