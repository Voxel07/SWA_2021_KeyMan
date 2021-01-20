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
      users:[],
      errorMsgUser: '',
      errorMsgCompany:'',
      person1: '',
      person2: '',
      ip1:'',
      ip2:'',
      ip3:'',
      feature1:'',
      feature2:'',
      feature3:'',
      contractId:'5'
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

  getCompanys(){
    axios.get('http://localhost:8080/company')
    .then(response => {
      console.log(response)
      this.setState({ companys: response.data })
      if(response.data.length ==0){
        this.setState({ errorMsgUser: 'Kein User Daten erhalten' })
      }
    })
    .catch(error => {
      console.log(error)
      this.setState({ errorMsgCompany: 'Error Company Daten erhalten' })
    })
  }

  getUsers(){
    axios.get('http://localhost:8080/user')
    .then(response => {
      console.log(response)
      this.setState({ users: response.data })
      if(response.data.length ==0){
        this.setState({ errorMsgUser: 'Kein User Daten erhalten' })
      }
    })
    .catch(error => {
      console.log(error)
      this.setState({ errorMsgUser: 'Error User Daten erhalten' })
    })
  }
 
  handleSubmit = event => {
    event.preventDefault();
    this.addContract();
    if(this.state.ip1 !== null){
      this.addIps(this.state.ip1);
    }
    if(this.state.feature1 !== null){
      this.addFeatures(this.state.feature1);
    }
  }
  addContract(){
    console.log( this.state.companyId);
    console.log( this.state);
    axios.put('http://localhost:8080/contract/' + this.state.companyId,
    {startDate : this.state.startDate ,endDate : this.state.endDate,
    version: this.state.version})
      .then(response => {
        console.log(response)
        this.setState({contractId: response.data})
        // this.ClearInput();
      })
      .catch(error => {
        console.log(error)
      })
  }
  addFeatures(){
    axios.put('http://localhost:8080/feature/' + this.state.contractId,
    {number : this.props.feature1 })
    .then(response => {
      console.log(response)
      // this.ClearInput();
    })
    .catch(error => {
      console.log(error)
    })
  }
  addIps(){
    axios.put('http://localhost:8080/IpNumber/' + this.state.contractId,
    {ipNumber : this.props.ip1 })
    .then(response => {
      console.log(response)
      // this.ClearInput();
    })
    .catch(error => {
      console.log(error)
    })
  }

  //Nicht getestet 
  ClearInput() {
    this.setState({ startDate: '' })
    this.setState({ endDate: '' })
    this.setState({ version: '' })
    this.setState({ licenskey: '' })
    this.setState({ companyId: '' })
  }

  render() {
    //Styling fehlt
    const { startDate, endDate, ip1,ip2,ip3,feature1,feature2,feature3,version } = this.state
    return (

      <div>
        <form onSubmit={this.handleSubmit}>
          <div >
            <div class="container"  >
              <h1 class="title">Contract Details for (companyA) eingabefeld</h1>

              <div className=" form-row ">
                <div className="form-group col-12 col-sm-6 ">
                  <label>StartDate</label>
                  <input
                    placeholder="Name"
                    class="form-control"
                    name="startDate"
                    type="date"
                    value={startDate} onChange={this.Changehandler} />
                </div>
                <div className="col-12 col-sm-6">
                  <label> EndDate </label>
                  <input
                    placeholder="EndDate"
                    class="form-control"
                    name="endDate"
                    type="date"
                    value={endDate} onChange={this.Changehandler} />
                </div>
              </div>

              <div className=" form-row ">
                <div className="form-group col-6 col-sm-6">
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
                <div className="form-group col-12 col-sm-6 ">
                  <label>Version</label>
                  <input
                    placeholder="Version"
                    class="form-control"
                    name="version"
                    type="number"
                    value={version} onChange={this.Changehandler}
                  />
                </div>
                <div className="form-group col-6 col-sm-6">
                  <label> Responsible </label>
                  <select name="persoperson1" class="custom-select" id="inputGroupSelect01"onChange={this.Changehandler}>
                    {
                      this.state.users.length ?
                      this.state.users.map(user => <option value={user.id}  >{user.username}</option>)
                      : <option value={0} >{this.state.errorMsgUser}</option>
                    }
                  </select>
                </div>
                <div className="form-group col-6 col-sm-6">
                  <label> Responsible </label>
                  <select name="persoperson2" class="custom-select" id="inputGroupSelect01"onChange={this.Changehandler}>
                   
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
                    class="form-control"
                    name="ip1"
                    type="number"
                    value={ip1} onChange={this.Changehandler}
                  />
                </div>
                <div className=" col-12 col-sm-1">
                  <label> Feature A </label>
                  <input
                    placeholder="Feature A"
                    class="form-control"
                    name="feature1"
                    type="text"
                  value={feature1} onChange={this.Changehandler} 
                  />
                </div>
              </div>
              <div className=" form-row ">
                <div className=" col-12 col-sm-6">
                  <label> Ip Number </label>
                  <input
                    placeholder="Ip Number"
                    class="form-control"
                    name="ip2"
                    type="number"
                  value={ip2} onChange={this.Changehandler} 
                  />
                </div>
                <div className=" col-12 col-sm-1">
                  <label> Feature B </label>
                  <input
                    placeholder="Feature B"
                    class="form-control"
                    name="feature2"
                    type="text"
                   value={feature2} onChange={this.Changehandler}
                  />
                </div>
              </div>
              <div className=" form-row ">
                <div className=" col-12 col-sm-6">
                  <label> Ip Number </label>
                  <input
                    placeholder="Ip Number"
                    class="form-control"
                    name="ip3"
                    type="number"
                   value={ip3} onChange={this.Changehandler}
                  />
                </div>
                <div className=" col-12 col-sm-1">
                  <label> Feature C </label>
                  <input
                    placeholder="Feature C"
                    class="form-control"
                    name="feature3"
                    type="text"
                  value={feature3} onChange={this.Changehandler} 
                  />
                </div>
              </div>
            </div>
            <div class="mt-4 text-center">
              <button type="submit" class="btn btn-primary btn-lg" >Add</button>
            </div>
          </div>
        </form>
      </div>
    );
  }
}

export default AddContract;