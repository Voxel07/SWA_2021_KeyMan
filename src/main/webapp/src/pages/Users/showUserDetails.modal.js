import React, { Component } from 'react'

class showUserDetails extends React.Component {
    constructor() {
        super();
        this.state = {  };
    }
    
    render() {
        const { id, username, firstName,lastName,password,email, isAdmin,phone1,type1,phone2,type2} = this.state
        return (
            <div>
            <legend>User Details: {id}</legend>
            <form>
            
            <div className="container"  >
            <h1 className="title">My User</h1>
              <div className=" form-row ">
              <div className="form-group col-6 col-sm-6 my-2 p-2"> 
                <label> Company </label>
                <input name="companyId" className="form-control" id="inputGroupSelect01"
                 //value={Company} onChange={this.Changehandler} 
                 >
                </input>
               </div>
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
               </div>
               
              <div className=" form-row ">
                <div className="form-group col-12 col-sm-6 my-2 p-2 ">
                <label>First Name</label>
                <input
                  placeholder="Department"
                  className="form-control "
                  name="department"
                  type="text"
                   // value={username} onChange={this.Changehandler} 
                  />
                  
               </div>
               <div className=" col-12 col-sm-6 my-2 p-2">
                <label> Last Name </label>
                <input
                  placeholder="Street"
                  className="form-control"
                  name="street"
                  type="text"
                   // value={username} onChange={this.Changehandler}
                   />
                  
                </div>
              </div>
              <div className=" form-row ">
             <div className=" col-12 col-sm-6 my-2 p-2">
                <label> Username </label>
                <input
                  placeholder="Username"
                  className="form-control"
                  name="username"
                  type="text"
                 // value={username} onChange={this.Changehandler}
                  />
              </div>
              <div className=" col-12 col-sm-6 my-2 p-2">
                <label> Password </label>
                <input
                  placeholder="Password"
                  className="form-control"
                  name="password"
                  type="password"
                  //value={password} onChange={this.Changehandler} 
                  />
              </div>
            </div>

            <div className=" form-row ">
              <div className=" col-12 col-sm-6 my-2 p-2">
                <label> Number1 </label>
                <input
                  placeholder="Number"
                  className="form-control"
                  name="phone1"
                  type="number"
                  //value={phone1} onChange={this.Changehandler} 
                  />
              </div>
              <div className=" col-12 col-sm-6 my-2 p-2">
                <label> Type1 </label>
                <input
                  placeholder="Type"
                  className="form-control"
                  name="type1"
                  type="text"
                  //value={type1} onChange={this.Changehandler} 
                  />
              </div>
            </div>
            <div className=" form-row ">
              <div className=" col-12 col-sm-6 my-2 p-2">
                <label> Number2 </label>
                <input
                  placeholder="Number"
                  className="form-control"
                  name="phone2"
                  type="number"
                  //value={phone2} onChange={this.Changehandler}
                   />
              </div>
              <div className=" col-12 col-sm-6 my-2 p-2">
                <label> Type2 </label>
                <input
                  placeholder="Type2"
                  className="form-control"
                  name="type2"
                  type="text"
                  //value={type2} onChange={this.Changehandler} 
                  />
              </div>
            </div>
            <div className=" col-12 col-sm-6 my-2 p-2">
              <label> isAdmin </label>
              <input
                name="isAdmin"
                className="form-control"
                type="checkbox"
               // value={isAdmin} onChange={this.Changehandler}
                />
            </div>
          </div>       
      </form>
    </div>
        
        );
    }
}

export default showUserDetails;

// export default class showUserDetails extends Component {
//     render() {
//         return (
//             <div>
//                 Mich gibt es noch nicht.
//             </div>
//         )
//     }
// }
