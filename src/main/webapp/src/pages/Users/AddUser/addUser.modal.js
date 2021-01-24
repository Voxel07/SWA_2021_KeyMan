import React from 'react';
import axios from 'axios'

class AddUser extends React.Component {
  constructor() {
    super();
    this.state = {
      id: '',
      email: '',
      username: '',
      password: '',
      firstName: '',
      lastName: '',
      isAdmin: '',
      companys: [],
      errorMsgCompanys: '',
      phone1: '',
      type1: '',
      phone2: '',
      type2: '',
      companyId: ''
    };

    this.handleSubmit = this.handleSubmit.bind(this);
  }
  Changehandler = (event) => {
    console.log(this.state);
    this.setState({ [event.target.name]: event.target.value })
  }
  componentDidMount() {
    this.getCompanys();
  }

  getCompanys() {
    axios.get('http://localhost:8080/company')
      .then(response => {
        console.log(response)
        this.setState({ companys: response.data })
        if (response.data.length === 0) {
          this.setState({ errorMsgUser: 'Kein Company Daten erhalten' })
        }
      })
      .catch(error => {
        console.log(error)
        this.setState({ errorMsgCompany: error })
      })
  }
  handleSubmit = event => {
    event.preventDefault();
    this.addUser();
  }
  addUser() {
    const { username, firstName,lastName,password,email, isAdmin} = this.state
    axios.put('http://localhost:8080/user/' + this.state.companyId,
    { email: email,
    username: username,
    password: password,
    firstName: firstName,
    lastName: lastName,
    isAdmin: isAdmin
  })
    // axios.put('http://localhost:8080/user/' + this.state.companyId, this.state) //geht eh nicht
      .then(response => {
        console.log(response)
        this.setState({ id: response.data })

        if (this.state.phone1 !== '' && this.state.type1 !== '') {
          this.addPhone(this.state.phone1, this.state.type1);
        }
        if (this.state.phone2 !== '' && this.state.type2 !== '') {
          this.addPhone(this.state.phone2, this.state.type2);
        }
      })
      .catch(error => {
        console.log(error)
      })
  }

  addPhone = (phoneNumber, phoneType) => {
    axios.put('http://localhost:8080/phone/' + this.state.id,
      { number: phoneNumber, type: phoneType })
      .then(response => {
        console.log(response)
        // this.ClearInput();
      })
      .catch(error => {
        console.log(error)
      })
  }

  render() {
    const {username, firstName,lastName,password,email, isAdmin,phone1,type1,phone2,type2} = this.state
    return (
      <form onSubmit={this.handleSubmit}>
        <div>
        <legend>Add User: </legend>
          <div className="container"  >
            <h1 className="title">{username}</h1>
            <div className=" form-row ">
              <div className="form-group col-6 col-sm-6 my-2 p-2">
                <label> Company </label>
                <select name="companyId" class="custom-select" id="inputGroupSelect01" onChange={this.Changehandler}>
                  {
                    <option >Firma wählen</option>
                  }
                  {
                    this.state.companys.length ?
                      this.state.companys.map(company => <option value={company.id}  >{company.name}</option>)
                      : <option value={0} >{this.state.errorMsgCompany}</option>
                  }
                </select>
              </div>
              <div className=" col-12 col-sm-6 my-2 p-2">
                <label> Email </label>
                <input
                  placeholder="Email"
                  className="form-control"
                  name="email"
                  type="text"
                  value={email} onChange={this.Changehandler} />
              </div>
            </div>

            <div className=" form-row ">
              <div className="form-group col-12 col-sm-6 my-2 p-2 ">
                <label>First Name</label>
                <input
                  placeholder="First Name"
                  className="form-control "
                  name="firstName"
                  type="text"
                  value={firstName} onChange={this.Changehandler} />
              </div>
              <div className=" col-12 col-sm-6 my-2 p-2">
                <label> Last Name </label>
                <input
                  placeholder="Last Name"
                  className="form-control"
                  name="lastName"
                  type="text"
                  value={lastName} onChange={this.Changehandler} />
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
                  value={username} onChange={this.Changehandler} />
              </div>
              <div className=" col-12 col-sm-6 my-2 p-2">
                <label> Password </label>
                <input
                  placeholder="Password"
                  className="form-control"
                  name="password"
                  type="password"
                  value={password} onChange={this.Changehandler} />
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
                  value={phone1} onChange={this.Changehandler} />
              </div>
              <div className=" col-12 col-sm-6 my-2 p-2">
                <label> Type1 </label>
                <input
                  placeholder="Type"
                  className="form-control"
                  name="type1"
                  type="text"
                  value={type1} onChange={this.Changehandler} />
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
                  value={phone2} onChange={this.Changehandler} />
              </div>
              <div className=" col-12 col-sm-6 my-2 p-2">
                <label> Type2 </label>
                <input
                  placeholder="Type2"
                  className="form-control"
                  name="type2"
                  type="text"
                  value={type2} onChange={this.Changehandler} />
              </div>
            </div>
            <div className=" col-12 col-sm-6 my-2 p-2">
              <label> isAdmin </label>
              <input
                name="isAdmin"
                className="form-control"
                type="checkbox"
                value={isAdmin} onChange={this.Changehandler} />
            </div>
          </div>
        </div>
        <div className="mt-4 text-center">
          <button type="submit" class="btn btn-primary btn-lg">Add</button>
        </div>
      </form>
    );
  }
}

export default AddUser;