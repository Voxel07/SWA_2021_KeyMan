import React from 'react';
import axios from 'axios';
import Phone from './Phone';

class EditUser extends React.Component {
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
        companyId: 1, 
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
    componentWillMount() {
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
          if (response.data.length == 0) {
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
  axios.get('http://localhost:8080/user/'+ this.state.id)
      .then(response => {
          console.log(response);
          this.setState({ contracts: response.data });
          if (response.data.length == 0) {
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
      if (response.data.length == 0) {
          this.setState({ errorMsgPhone: 'Keine Phoes Daten erhalten' })
      }

  })
      .catch(error => {
          // console.log(error);
          this.setState({ errorMsgPhone: " " + error })
      })
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
                <select name="companyId" class="custom-select" id="inputGroupSelect01" onChange={this.Changehandler}>
                  {
                    //aktFirma anzeigen
                    <option>Firma wählen{this.state.companyId}</option>
                  }
                  {
                    this.state.companys.length ?
                      this.state.companys.map(company => <option value={company.id}  >{company.name}</option>)
                      : <option value={0} >{this.state.errorMsgCompany}</option>
                  }
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
                      <button type="submit" class="btn btn-primary btn-lg">Update</button>
                 </div>
              </form>  
           
        );
    }
}

export default EditUser;