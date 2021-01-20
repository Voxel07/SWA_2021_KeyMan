import React from 'react';
import axios from 'axios'
class AddContract extends React.Component {
    constructor() {
        super();
        this.state = {
        startDate:'',
        endDate: '',
        version:'',
        licenskey:'',
        companyId:'',
        companys:[] };

        this.handleSubmit = this.handleSubmit.bind(this);
    }
    Changehandler = (event) =>{
        this.setState({[event.target.name]:event.target.value})
      }
      componentDidMount() {
        axios.get('http://localhost:8080/company')
          .then(response => {
            console.log(response)
            this.setState({ companys: response.data })
          })
          .catch(error => {
            console.log(error)
            this.setState({ errorMsg: 'Kein Company Daten erhalten' })
          })
      }
    handleSubmit = event => {
    event.preventDefault();
    axios.put('http://localhost:8080/contract/'+this.state.companyId,this.state)
    .then(response => {
        console.log(response)
        this.ClearInput();
    })
    .catch(error => {
        console.log(error)
    })

    }
    //Nicht getestet 
    ClearInput(){
        this.setState({startDate: ''})
        this.setState({endDate: ''})
        this.setState({version: ''})
        this.setState({licenskey: ''})
        this.setState({companyId: ''})
    }
    
    render() {
        //Styling fehlt
        const{startDate,endDate,version,licenskey,companyId} = this.state
        return (

            <div>
              <form id="test"  onSubmit={this.handleSubmit}>
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
            <select name="companyId"  class="custom-select" id="inputGroupSelect01" onChange={this.Changehandler}>
            {
                this.state.companys.length ?
                this.state.companys.map(company => <option  value = {company.id}  >{company.name}</option> )
                :<option value = {0} >no companys found</option>
              }
             
            </select>
          </div>
                <div className="form-group col-6 col-sm-6"> 
                  <label> Responsible </label>
                    <select class="custom-select" id="inputGroupSelect01">
                      <option selected>User...</option>
                      <option value="1">One</option>
                      <option value="2">Two</option>
                      <option value="3">Three</option>
                    </select>
                </div>
                </div>
                
                <div className=" form-row ">
                  <div className="form-group col-12 col-sm-6 ">
                  <label>Ip Number</label>
                  <input
                    placeholder="Ip Number"
                    class="form-control"
                    name="department"
                    type="text"
                   // value={department} onChange={this.Changehandler}
                    />
                </div>
                <div className=" col-12 col-sm-1">
                  <label> Feature A </label>
                  <input
                    placeholder="Feature A"
                    class="form-control"
                    name="street"
                    type="number"
                    //value={street} onChange={this.Changehandler} 
                    />
                  </div>
                </div>
              <div className=" form-row ">
              <div className=" col-12 col-sm-6">
                  <label> Ip Number </label>
                  <input
                    placeholder="Ip Number"
                    class="form-control"
                    name="state"
                    type="text"
                   //value={state} onChange={this.Changehandler} 
                   />
                </div>
              <div className=" col-12 col-sm-1">
                  <label> Feature B </label>
                  <input
                    placeholder="Feature B"
                    class="form-control"
                    name="Country"
                    type="number"
                   // value={country} onChange={this.Changehandler}
                    />
                </div>
                </div>
                <div className=" form-row ">
              <div className=" col-12 col-sm-6">
                  <label> Ip Number </label>
                  <input
                    placeholder="Ip Number"
                    class="form-control"
                    name="state"
                    type="text"
                   // value={state} onChange={this.Changehandler}
                    />
                </div>
              <div className=" col-12 col-sm-1">
                  <label> Feature C </label>
                  <input
                    placeholder="Feature C"
                    class="form-control"
                    name="Country"
                    type="number"
                    //value={country} onChange={this.Changehandler} 
                    />
                </div>
                </div>
                <div className=" form-row ">
              <div className=" col-12 col-sm-12">
                      <label> licenskey </label>
                      <textarea 
                        class="form-group col-12" rows="5"
                        placeholder="Licenskey"
                        name="licenskey"
                        type="text"
                        value={licenskey} onChange={this.Changehandler}>
                      </textarea>
                </div>
                </div>
                </div>
                    <div class="mt-4 text-center">
                        <button type="submit" class="btn btn-primary btn-lg">Save</button>
                        <a >    </a>
                        <button type="submit" class="btn btn-primary btn-lg">Cancel</button>
                  </div>    
                  </div> 
          </form>
      </div> 
        );
    }
}

export default AddContract;