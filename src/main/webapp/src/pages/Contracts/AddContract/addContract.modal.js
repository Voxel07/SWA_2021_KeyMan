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
        companyId:'' };

        this.handleSubmit = this.handleSubmit.bind(this);
    }
    Changehandler = (event) =>{
        this.setState({[event.target.name]:event.target.value})
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
            <div className="contract-form">
            <form  onSubmit={this.handleSubmit}>
              <div >
                <div className="user-input">
                  <label>
                  StartDate
                </label>
                  <input
                    placeholder="Name"
                    name="startDate"
                    type="text"
                    value={startDate} onChange={this.Changehandler} />
                </div>
                <div className="user-input">
                  <label>
                  EndDate
                </label>
                  <input
                    placeholder="EndDate"
                    name="endDate"
                    type="text"
                    value={endDate} onChange={this.Changehandler} />
                </div>
                <div className="user-input">
                  <label>
                  Version
                </label>
                  <input
                    placeholder="Version"
                    name="version"
                    type="text"
                    value={version} onChange={this.Changehandler} />
                </div>
                <div className="user-input">
                  <label>
                  Licenskey
                </label>
                  <input
                    placeholder="Licenskey"
                    name="licenskey"
                    type="number"
                    value={licenskey} onChange={this.Changehandler} />
                </div>
                <div className="user-input">
                  <label>
                  CompanyId
                </label>
                  <input
                    placeholder="CompanyId"
                    name="companyId"
                    type="number"
                    value={companyId} onChange={this.Changehandler} />
                </div>
                <div> <input type="submit" value="Submit"/></div>
              </div>
    
    
            </form>
          </div>
        );
    }
}

export default AddContract;