import React, { Component } from 'react'
import axios from 'axios';

export default class showDetails extends Component {
      constructor(props) {
        super(props);
        this.state = {
        id: this.props.user.id ,
        email: this.props.user.email ,
        username: this.props.user.username,
        password: this.props.user.password ,
        firstName: this.props.user.firstName ,
        lastName: this.props.user.lastName ,
        isAdmin: this.props.user.isAdmin ,
        companyId: this.props.companyId,
        companyName: this.props.companyName, 
        companys:[],
        errorMsgCompanys: '',
        phones:[],
        phone:'',
        type:'',
        errorMsgPhone:'',
        errorMsgCp:'',
        contracts: []
      };
    }
    componentDidMount() {
      this.getCompany();
      this.getPhones();
      this.getContracts();
    }
    Changehandler = (event) => {
    this.setState({ [event.target.name]: event.target.value })
    }
    handleSubmit = event => {
    event.preventDefault();
    axios.post('http://localhost:8080/user', this.state)
        .then(response => {
            console.log(response)
        })
        .catch(error => {
            console.log(error)
        })
    }

    handleSubmitPhone = event => {
    event.preventDefault();
    axios.put('http://localhost:8080/phone/'+this.state.id, {number : this.state.phone, type: this.state.type})
        .then(response => {
            console.log(response)
        })
        .catch(error => {
            console.log(error)
        })
    }
    // Holt alle Companys, damit man aswählen kann. 
    // Die aktuelle wird als erstes angezeigt. 
    getCompany() {
    axios.get('http://localhost:8080/company')
      .then(response => {
          console.log(response);
          this.setState({ companys: response.data });
          if (response.data.length === 0) {
              this.setState({ errorMsgCp: 'Keine Company Daten erhalten' })
          }
      })
      .catch(error => {
          // console.log(error);
          this.setState({ errorMsgCp: " " + error })
      })
    }
    getCopmanyForThisSingelFuckingUser(pls){
    this.setState({company: 1})
    }

    getContracts(){
    axios.get('http://localhost:8080/contract', { params: { usrId: this.state.id }})
      .then(response => {
          console.log(response);
          this.setState({ contracts: response.data });
          if (response.data.length === 0) {
              this.setState({ errorMsgCp: 'Keine Contracts Daten erhalten' })
          }
      })
      .catch(error => {
          // console.log(error);
          this.setState({ errorMsgCp: " " + error })
      })
    }

    getPhones() {
    axios.get('http://localhost:8080/phone', { params: { usrId: this.state.id } })
    .then(response => {
      console.log(response);
      this.setState({ phones: response.data });
      if (response.data.length === 0) {
          this.setState({ errorMsgPhone: 'Keine Phoes Daten erhalten' })
      }

    })
      .catch(error => {
          // console.log(error);
          this.setState({ errorMsgPhone: " " + error })
      })
    }
    
    render() {
        const { id, username, firstName,lastName,password,email, isAdmin,phone1,type1,phone2,type2,companyName} = this.state
        return (
            <div>
            <legend>User Details with id : {id}</legend>
            <form onSubmit={this.handleSubmit} key="User">
            
            <div className="container"  >
            <h1 className="title">My User</h1>
              <div className=" form-row ">
              <div className="form-group col-6 col-sm-6 my-2 p-2"> 
                <label> Company </label>
                <input name="companyId" className="form-control" id="inputGroupSelect01"
                 value={companyName} readOnly 
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
                  value={email} readOnly
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
                  value={firstName} readOnly
                  />
                  
               </div>
               <div className=" col-12 col-sm-6 my-2 p-2">
                <label> Last Name </label>
                <input
                  placeholder="Street"
                  className="form-control"
                  name="street"
                  type="text"
                  value={lastName} readOnly
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
                  value={username} readOnly
                  />
              </div>
              <div className=" col-12 col-sm-6 my-2 p-2">
                <label> Password </label>
                <input
                  placeholder="Password"
                  className="form-control"
                  name="password"
                  type="password"
                  value={password} readOnly
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
                  value={phone1} readOnly
                  />
              </div>
              <div className=" col-12 col-sm-6 my-2 p-2">
                <label> Type1 </label>
                <input
                  placeholder="Type"
                  className="form-control"
                  name="type1"
                  type="text"
                  value={type1} readOnly
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
                  value={phone2} readOnly
                   />
              </div>
              <div className=" col-12 col-sm-6 my-2 p-2">
                <label> Type2 </label>
                <input
                  placeholder="Type2"
                  className="form-control"
                  name="type2"
                  type="text"
                  value={type2} readOnly
                  />
              </div>
            </div>
            <div className=" col-12 col-sm-6 my-2 p-2">
              <label> isAdmin </label>
              <input
                name="isAdmin"
                className="form-control"
                type="checkbox"
                value={isAdmin} readOnly
                />
            </div>
          </div>       
      </form>
    </div>
        
        );
    }
}
