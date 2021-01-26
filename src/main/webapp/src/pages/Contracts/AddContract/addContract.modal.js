import React from 'react';
import axios from 'axios'

class AddContract extends React.Component {
  constructor() {
    super();
    this.state = {
      startDate: '',
      endDate: '',
      version: '',
      companyId: '',
      companys: [],
      users: [],
      errorMsgUser: '',
      errorMsgCompany: '',
      person1: '',
      person2: '',
      ip1: '',
      ip2: '',
      ip3: '',
      feature1: '',
      feature2: '',
      feature3: '',
      contractId: '',
    };

    this.handleSubmit = this.handleSubmit.bind(this);
  }
  Changehandler = (event) => {
    this.setState({ [event.target.name]: event.target.value })
  }
  componentDidMount() {
    this.getCompanys();
    this.getUsers();
  }

  getCompanys() {
    axios.get('http://localhost:8080/company')
      .then(response => {
        this.setState({ companys: response.data })
        if (response.data.length === 0) {
          this.setState({ errorMsgCompany: 'Kein Company Daten erhalten' })
        }
      })
      .catch(error => {
        this.setState({ errorMsgCompany: error })
      })
  }

  getUsers() {
    axios.get('http://localhost:8080/user')
      .then(response => {
        this.setState({ users: response.data })
        if (response.data.length === 0) {
          this.setState({ errorMsgUser: 'Kein User Daten erhalten' })
        }
      })
      .catch(error => {
        this.setState({ errorMsgUser: error })
      })
  }

  handleSubmit = event => {
    event.preventDefault();
    this.addContract();
  }

  addContract() {
    axios.put('http://localhost:8080/contract/' + this.state.companyId,
      {
        startDate: this.state.startDate, endDate: this.state.endDate,
        version: this.state.version
      })
      .then(response => {

        this.setState({ contractId: response.data })
        if (this.state.ip1 !== '') {
          this.addIps(this.state.ip1);
        }
        if (this.state.feature1 !== '') {
          this.addFeatures(this.state.feature1);
        }
        if (this.state.ip2 !== '') {
          this.addIps(this.state.ip2);
        }
        if (this.state.feature2 !== '') {
          this.addFeatures(this.state.feature2);
        }
        if (this.state.ip3 !== '') {
          this.addIps(this.state.ip3);
        }
        if (this.state.feature3 !== '') {
          this.addFeatures(this.state.feature3);
        }
        if (this.state.person1 !== '') {
          this.addUser(this.state.person1);
        }
        if (this.state.person2 !== '') {
          this.addUser(this.state.person2);
        }
        this.props.cbToBar(true);
        this.props.cbToBar(false);
        this.ClearInput();
      })
      .catch(error => {

      })
  }

  addFeatures = feature => {
    axios.put('http://localhost:8080/feature/' + this.state.contractId,
      { number: feature })
      .then(response => {
      })
      .catch(error => {
      })
  }

  addIps = ip => {
    axios.put('http://localhost:8080/IpNumber/' + this.state.contractId,
      { ipNumber: ip })
      .then(response => {
      })
      .catch(error => {
      })
  }

  addUser = person => {
    axios.post('http://localhost:8080/contract', { id: this.state.contractId }, { params: { usrId: person } })
      .then(response => {
      })
      .catch(error => {
      })
  }
  ClearInput() {
    this.setState({
      startDate: '',
      endDate: '',
      version: '',
      companyId: '',
      companys: [],
      users: [],
      errorMsgUser: '',
      errorMsgCompany: '',
      person1: '',
      person2: '',
      ip1: '',
      ip2: '',
      ip3: '',
      feature1: '',
      feature2: '',
      feature3: '',
      contractId: '',
    })
  }


  render() {
    const { startDate, endDate, ip1, ip2, ip3, feature1, feature2, feature3, version } = this.state
    return (
      <div>
        <form onSubmit={this.handleSubmit}>
          <div >
            <legend>Add Contract:</legend>
            <div class="container"  >
              <h1 class="title">New Contract</h1>
              <div className=" form-row ">
                <div className="form-group col-12 col-sm-6 ">
                  <label>StartDate</label>
                  <input
                    placeholder="Name"
                    class="form-control1"
                    name="startDate"
                    type="date"
                    value={startDate} onChange={this.Changehandler} />
                </div>
                <div className="form-group col-12 col-sm-6">
                  <label> EndDate </label>
                  <input
                    placeholder="EndDate"
                    class="form-control1"
                    name="endDate"
                    type="date"
                    value={endDate} onChange={this.Changehandler} />
                </div>
              </div>
              <div className=" form-row ">
                <div className="form-group col-6 col-sm-6">
                  <label> Company </label>
                  <select name="companyId" class="custom-selected" id="inputGroupSelect01" onChange={this.Changehandler}>
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
                <div className="form-group col-12 col-sm-6 ">
                  <label>Version</label>
                  <input
                    placeholder="Version"
                    class="form-control1"
                    name="version"
                    type="number"
                    value={version} onChange={this.Changehandler}
                  />
                </div>
                <div className="form-group col-12 col-sm-6">
                  <label> Responsible </label>
                  <select name="person1" class="custom-selected" id="inputGroupSelect02" onChange={this.Changehandler}>
                    {
                      <option >User wählen</option>
                    }
                    {
                      this.state.users.length ?
                        this.state.users.map(user => <option value={user.id}  >{user.username}</option>)
                        : <option value={0} >{this.state.errorMsgUser}</option>
                    }
                  </select>
                </div>
                <div className="form-group col-6 col-sm-6">
                  <label> Responsible </label>
                  <select name="person2" class="custom-selected" id="inputGroupSelect03" onChange={this.Changehandler}>
                    {
                      <option >User wählen</option>
                    }
                    {

                      this.state.users.length ?
                        this.state.users.map(user => <option value={user.id}  >{user.username}</option>)
                        : <option value={0} >{this.state.errorMsgUser}</option>
                    }
                  </select>
                </div>
              </div>

              <div className=" form-row ">
                <div className="form-group col-12 col-sm-6 ">
                  <label>Ip Number</label>
                  <input
                    placeholder="Ip Number"
                    class="form-control1"
                    name="ip1"
                    type="number"
                    value={ip1} onChange={this.Changehandler}
                  />
                </div>
                <div className=" col-12 col-sm-2">
                  <label> Feature A </label>
                  <input
                    placeholder="Feature A"
                    class="form-control1"
                    name="feature1"
                    type="text"
                    value={feature1} onChange={this.Changehandler}
                  />
                </div>
              </div>
              <div className=" form-row ">
                <div className="form-group col-12 col-sm-6 ">
                  <label>Ip Number</label>
                  <input
                    placeholder="Ip Number"
                    class="form-control1"
                    name="ip2"
                    type="number"
                    value={ip2} onChange={this.Changehandler}
                  />
                </div>
                <div className=" col-12 col-sm-2">
                  <label> Feature B </label>
                  <input
                    placeholder="Feature B"
                    class="form-control1"
                    name="feature2"
                    type="text"
                    value={feature2} onChange={this.Changehandler}
                  />
                </div>
              </div>
              <div className=" form-row ">
                <div className="form-group col-12 col-sm-6 ">
                  <label>Ip Number</label>
                  <input
                    placeholder="Ip Number"
                    class="form-control1"
                    name="ip3"
                    type="number"
                    value={ip3} onChange={this.Changehandler}
                  />
                </div>
                <div className=" col-12 col-sm-2">
                  <label> Feature C </label>
                  <input
                    placeholder="Feature C"
                    class="form-control1"
                    name="feature3"
                    type="text"
                    value={feature3} onChange={this.Changehandler}
                  />
                </div>
              </div>
            </div>
            <div class="mt-4 text-center">
              <button type="submit" class=" btn-primary1" >Add</button>
            </div>
          </div>
        </form>
      </div>
    );
  }
}

export default AddContract;