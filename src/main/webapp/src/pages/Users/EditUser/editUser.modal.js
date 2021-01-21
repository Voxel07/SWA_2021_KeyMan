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
        company:'', 
        errorMsgCompanys: '',
        phones:[],
        phone:'',
        type:'',
        errorMsgPhone:'',
        errorMsgCp:''
       };
    }
    componentWillMount() {
      this.getCompany();
      this.getPhones();
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
    axios.put('http://localhost:8080/phone/'+this.props.user.id, {number : this.state.phone, type: this.state.type})
        .then(response => {
            console.log(response)
        })
        .catch(error => {
            console.log(error)
        })
}
getCompany() {
  axios.get('http://localhost:8080/company/'+this.props.contract.companyId)
      .then(response => {
          console.log(response);
          this.setState({ company: response.data });
          if (response.data.length == 0) {
              this.setState({ errorMsgCp: 'Keine Company Daten erhalten' })
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
                      <button type="submit" class="btn btn-primary btn-lg">Update</button>
                 </div>
              </form>  
           
        );
    }
}

export default EditUser;