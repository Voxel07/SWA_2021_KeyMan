import React, { Component } from 'react'
import axios from 'axios';

export default class showDetails extends Component {
  constructor(props) {
    super(props);
    this.state = {
      id: this.props.user.id,
      email: this.props.user.email,
      username: this.props.user.username,
      password: this.props.user.password,
      firstName: this.props.user.firstName,
      lastName: this.props.user.lastName,
      admin: this.props.user.admin,
      companyId: this.props.companyId,
      companyName: this.props.companyName,
      companys: [],
      errorMsgCompanys: '',
      phones: [],
      phone: '',
      type: '',
      errorMsgPhone: '',
      errorMsgCp: '',
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
      })
      .catch(error => {
      })
  }

  handleSubmitPhone = event => {
    event.preventDefault();
    axios.put('http://localhost:8080/phone/' + this.state.id, { number: this.state.phone, type: this.state.type })
      .then(response => {
      })
      .catch(error => {
      })
  }

  getCompany() {
    axios.get('http://localhost:8080/company')
      .then(response => {
        this.setState({ companys: response.data });
        if (response.data.length === 0) {
          this.setState({ errorMsgCp: 'Keine Company Daten erhalten' })
        }
      })
      .catch(error => {
        this.setState({ errorMsgCp: " " + error })
      })
  }

  getContracts() {
    axios.get('http://localhost:8080/contract', { params: { usrId: this.state.id } })
      .then(response => {
        this.setState({ contracts: response.data });
        if (response.data.length === 0) {
          this.setState({ errorMsgCp: 'Keine Contracts Daten erhalten' })
        }
      })
      .catch(error => {
        this.setState({ errorMsgCp: " " + error })
      })
  }

  getPhones() {
    axios.get('http://localhost:8080/phone', { params: { usrId: this.state.id } })
      .then(response => {
        this.setState({ phones: response.data });
        if (response.data.length === 0) {
          this.setState({ errorMsgPhone: 'Keine Phoes Daten erhalten' })
        }
        else{
          this.setState({ errorMsgPhone:''});
        }
      })
      .catch(error => {
        this.setState({ errorMsgPhone: " " + error })
      })
  }

  render() {
    const { id, username, firstName, lastName, password, email, admin, companyName } = this.state
    return (
      <div>
        <legend>User Details:</legend>
        <form onSubmit={this.handleSubmit} key="User">
          <div className="container"  >
            <h1 className="title"> User id : {id}</h1>
            <div className=" form-row ">
              <div className="form-group col-6 col-sm-6 my-2 p-2">
                <label> Company </label>
                <input name="companyId" className="form-control1" id="inputGroupSelect01"
                  value={companyName} readOnly
                >
                </input>
              </div>
              <div className=" form-group  col-12 col-sm-6 my-2 p-2">
                <label> Email </label>
                <input
                  placeholder="Email"
                  className="form-control1"
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
                  className="form-control1 "
                  name="department"
                  type="text"
                  value={firstName} readOnly
                />
              </div>
              <div className=" form-group  col-12 col-sm-6 my-2 p-2">
                <label> Last Name </label>
                <input
                  placeholder="Street"
                  className="form-control1"
                  name="street"
                  type="text"
                  value={lastName} readOnly
                />
              </div>
            </div>
            <div className=" form-row ">
              <div className="form-group  col-12 col-sm-6 my-2 p-2">
                <label> Username </label>
                <input
                  placeholder="Username"
                  className="form-control1"
                  name="username"
                  type="text"
                  value={username} readOnly
                />
              </div>
              <div className="form-group  col-12 col-sm-6 my-2 p-2">
                <label> Password </label>
                <input
                  placeholder="Password"
                  className="form-control1"
                  name="password"
                  type="password"
                  value={password} readOnly
                />
              </div>
            </div>
            <div>
                {
                  this.state.phones.length ? this.state.phones.map(phone => <div key={phone.id}>
                    <div className=" form-row ">
                        <div className=" form-group col-12 col-sm-6 ">
                            <label>Number</label>
                            <input type="number" name="number" className="form-control1" value={phone.number} readOnly></input>
                        </div>
                        <div className=" form-group col-12 col-sm-6 ">
                            <label>Type</label>
                            <input type="text" name="type" className="form-control1" value={phone.type} readOnly ></input>
                        </div>
                    </div>
                 </div>
                ): null
                }
                {
                  this.state.errorMsgPhone ? <div>{this.state.errorMsgPhone}</div> : null
                }
              </div>
            <div className="form-group  col-12 col-sm-6 my-2 p-2">
              <label> admin </label>
              <input
                name="admin"
                className="form-control1"
                type="checkbox"
                checked={admin} readOnly
              />
            </div>
          </div>
        </form>
      </div>
    );
  }
}
