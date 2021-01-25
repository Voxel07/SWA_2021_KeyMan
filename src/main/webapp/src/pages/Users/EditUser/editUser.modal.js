import React from 'react';
import axios from 'axios';
import Phone from './Phone';

class EditUser extends React.Component {
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
      errorMsgPhone: '',
      errorMsgCp: '',
      contracts: [],
      number: '',
      type: ''
    };
  }
  componentDidMount() {
    this.getCompany();
    this.getPhones();
    this.getContracts();
  }
  Changehandler = (event) => {

    if (event.target.name === "admin") {
      console.log('ändern');
          this.setState({ [event.target.name]: event.target.checked })
        }
        else {
          this.setState({ [event.target.name]: event.target.value })
  }
}
  handleSubmit = event => {
    event.preventDefault();
    axios.post('http://localhost:8080/user', this.state)
      .then(response => {
        console.log(response)
        this.props.cbToUsrJs(this.state);
      })
      .catch(error => {
        console.log(error)
      })
  }

  handleSubmitPhone = event => {
    event.preventDefault();
    axios.put('http://localhost:8080/phone/' + this.state.id, { number: this.state.number, type: this.state.type })
      .then(response => {
        console.log(response)
        this.getPhones()
        this.setState({ number: '', type: '' });
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
  getCopmanyForThisSingelFuckingUser(pls) {
    this.setState({ company: 1 })
  }

  getContracts() {
    axios.get('http://localhost:8080/contract', { params: { usrId: this.state.id } })
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
    const { username, firstName, lastName, password, email, admin, companyName } = this.state
    return (
      <div>
        <legend>Edit User:</legend>
        <form onSubmit={this.handleSubmit}>

          <div className="container"  >
            <h1 className="title">{username}</h1>
            <div className=" form-row ">
              <div className="form-group col-6 col-sm-6 my-2 p-2">
                <label> Company </label>
                <input name="companyId" className="form-control1" id="inputGroupSelect01"
                  value={companyName} readOnly
                >
                </input>
              </div>
              <div className=" form-group col-12 col-sm-6 my-2 p-2">
                <label> Email </label>
                <input
                  placeholder="Email"
                  className="form-control1"
                  name="email"
                  type="text"
                  value={email} onChange={this.Changehandler}
                />
              </div>
            </div>

            <div className=" form-row ">
              <div className="form-group col-12 col-sm-6 my-2 p-2 ">
                <label>First Name</label>
                <input
                  placeholder="First Name"
                  className="form-control1"
                  name="firstName"
                  type="text"
                  value={firstName} onChange={this.Changehandler} />
              </div>
              <div className=" form-group col-12 col-sm-6 my-2 p-2">
                <label> Last Name </label>
                <input
                  placeholder="LastName"
                  className="form-control1"
                  name="lastName"
                  type="text"
                  value={lastName} onChange={this.Changehandler} />
              </div>
            </div>
            <div className=" form-row ">
              <div className="form-group col-12 col-sm-6 my-2 p-2">
                <label> Username </label>
                <input
                  placeholder="Username"
                  className="form-control1"
                  name="username"
                  type="text"
                  value={username} onChange={this.Changehandler}
                />
              </div>
              <div className=" form-group col-12 col-sm-6 my-2 p-2">
                <label> Password </label>
                <input
                  placeholder="Password"
                  className="form-control1"
                  name="password"
                  type="password"
                  value={password} onChange={this.Changehandler}
                />
              </div>
            </div>
            <div className="form-group col-12 col-sm-6 my-2 p-2">
              <label> isadmin </label>
              <input
                name="admin"
                className="form-control1"
                type="checkbox"           
                checked={admin} onChange={this.Changehandler}
              />
            </div>
          </div>
          <div className="mt-4 text-center">
            <button type="submit" class=" btn-primary1">Edit User</button>
          </div>
        </form>

        <form onSubmit={this.handleSubmitPhone} key="Phone">
          <div >
            <legend>Phone's</legend>
            <div className="container"  >
              <h1 className="title">Phone</h1>
              <div>
                {
                  this.state.phones.length ? this.state.phones.map(phone => <Phone phone={phone} />) : null
                }
                {
                  this.state.errorMsgPhone ? <div>{this.state.errorMsgPhone}</div> : null
                }
              </div>
              <div className=" form-row ">
                <div className=" form-group col-12 col-sm-2 my-1 p-1">
                  <label>Number</label>
                  <input type="number" name="number" className="form-control1" value={this.state.number} onChange={this.Changehandler}></input>
                </div>
                <div className=" form-group col-12 col-sm-2 my-1 p-1">
                  <label>Type</label>
                  <input type="text" name="type" className="form-control1" value={this.state.type} onChange={this.Changehandler}></input>
                </div>

                <div class="form-group col-12 col-sm-2 my-4 p-4">
                  <button type="submit" className=" btn-secondary1" value="addPhone">addPhone</button>
                </div>
              </div>
            </div>
          </div>
        </form>

        <form>

        </form>
      </div>

    );
  }
}

export default EditUser;