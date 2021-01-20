import React from 'react';
class User extends React.Component {
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
                 <input
                  placeholder="Company"
                  className="form-control "
                  name="Company"
                  type="text" 
                  //value={Company} onChange={this.Changehandler}
                  />
               </div>
               </div>
               
              <div className=" form-row ">
                <div className="form-group col-12 col-sm-6 my-2 p-2 ">
                <label>First Name</label>
                <input
                  placeholder="Department"
                  className="form-control "
                  name="department"
                  type="text" 
                  //value={firstName} onChange={this.Changehandler}
                  />
               </div>
               <div className=" col-12 col-sm-6 my-2 p-2">
                <label> Last Name </label>
                <input
                  placeholder="LastName"
                  className="form-control"
                  name="lastName"
                  type="text"
                  //value={lastName} onChange={this.Changehandler}
                  />
                </div>
              </div>
            <div className=" form-row ">
             <div className=" col-12 col-sm-6 my-2 p-2">
                <label> Email </label>
                <input
                  placeholder="Email"
                  className="form-control"
                  name="email"
                  type="text"
                  //value={email} onChange={this.Changehandler}
                   />
               </div>
             <div className=" col-12 col-sm-6 my-2 p-2">
                <label> Phone </label>
                <input
                  placeholder="Phone"
                  className="form-control"
                  name="phone"
                  type="text"
                 // value={phone} onChange={this.Changehandler}
                  />
               </div>
               </div>
               <div className=" form-row ">
             <div className=" col-12 col-sm-6 my-2 p-2">
                <label> isAdmin </label>
                <input
                  className="form-control"
                  type="checkbox"
                 // value={isAdmin} onChange={this.Changehandler}
                  />
               </div>
             <div className=" col-12 col-sm-6 my-2 p-2">
                <label> Mobile </label>
                <input
                  placeholder="Mobile"
                  className="form-control"
                  name="mobile"
                  type="text"
                  //classNamealue={mobile} onChange={this.Changehandler} 
                  />
               </div>
               </div>
               </div>
               </div>
              </form>   
        );
    }
}

export default User;