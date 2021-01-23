import React from 'react';
import axios from 'axios';
import IpNumber from './Ip';
import Feature from './Feature';

class EditContract extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            startDate: this.props.contract.startDate,
            endDate: this.props.contract.endDate,
            version: this.props.contract.version,
            licenskey: this.props.contract.licenskey,
            id: this.props.contract.id,
            ipNumber: '',
            feature:'',
            features:[],
            ips:[],
            companyName: this.props.companyName,
            errorMsgIp: '',
            errorMsgFe: '',
            errorMsgCp: '',
            errorMsgUser: '',
            users:[]
        };
    }
    componentDidMount() {
        console.log("Daten Holen !");
        this.getFeatures();
        this.getIps();
        this.getUsers();
    }

    Changehandler = (event) => {
        console.log("änderung")
        this.setState({ [event.target.name]: event.target.value })
    }
    handleSubmit = event => {
        event.preventDefault();
        axios.post('http://localhost:8080/contract', this.state)
            .then(response => {
                console.log(response)
            })
            .catch(error => {
                console.log(error)
            })
    }

    handleSubmitIp = event => {
        event.preventDefault();
		console.log("Handle submit "+this.state.ipNumber);
        axios.put('http://localhost:8080/IpNumber/'+this.props.contract.id, {ipNumber : this.state.ipNumber})
            .then(response => {
                console.log(response)
                this.setState({
                    ips: [...this.state.ips, this.state.ipNumber]
                })
                console.log("Nach Submit "+this.state.ips);
                this.getIps();
            })
            .catch(error => {
                console.log(error)
            })   
    }
    getIps() {
        console.log("GetIp´s "+this.state.ips);
        axios.get('http://localhost:8080/IpNumber', { params: { ctrId: this.state.id } })
        .then(response => {
            console.log(response);
            this.setState({ ips:  [...this.state.ips,response.data] });
            // this.setState({ ips:  response.data });
            if (response.data.length == 0) {
                this.setState({ errorMsgIp: 'Keine IP Daten erhalten' })
            }

        })
            .catch(error => {
                // console.log(error);
                this.setState({ errorMsgIp: " " + error })
            })
    }
 
    handleSubmitFeature = event => {
        event.preventDefault();
		console.log(this.state.feature);
        axios.put('http://localhost:8080/feature/'+this.props.contract.id, {number : this.state.feature})
            .then(response => {
                console.log(response)
            })
            .catch(error => {
                console.log(error)
            })
            this.getFeatures();
    }
    getUsers(){
        axios.get('http://localhost:8080/user', { params: { ctrIdU: this.state.id } })
        .then(response => {
            console.log(response);
            this.setState({ users: response.data });
            if (response.data.length == 0) {
                this.setState({ errorMsgUser: 'Keine User Daten erhalten' })
            }

        })
            .catch(error => {
                // console.log(error);
                this.setState({ errorMsgUser: " " + error })
            })
    }
    getFeatures() {
        axios.get('http://localhost:8080/feature', { params: { ctrId: this.state.id } })
        .then(response => {
            console.log(response);
            this.setState({ features: response.data });
            if (response.data.length == 0) {
                this.setState({ errorMsgFe: 'Keine Feature Daten erhalten' })
            }

        })
            .catch(error => {
                // console.log(error);
                this.setState({ errorMsgFe: " " + error })
            })
    }
  
    render() {
        //Daten holen Obacht !!
        const { companyName, startDate, endDate, version, licenskey } = this.state
        return (
            <div>
                <form onSubmit={this.handleSubmit} key="Contract" >
                    <div >
                    <legend>Edit Contract from Company:{companyName}</legend>
                        <div className="container"  >
                        <h1 className="title">Edit Contract</h1>


                        <div className=" form-row ">
                            <div className="form-group col-12 col-sm-6 ">
                            <label>StartDate</label>
                            <input type="ate" className="form-control" name="startDate" value={startDate} onChange={this.Changehandler}></input>
                            </div>
                            <div className="col-12 col-sm-6">
                            <label> EndDate </label>
                            <input type="Date" className="form-control" name="endDate" value={endDate} onChange={this.Changehandler}></input>
                        </div>
                        </div>
                        <div className=" form-row ">
                            <div className="form-group col-12 col-sm-6">
                            <label> Company </label>
                                <input type="text" className="form-control" name="name" value={companyName} readOnly></input>
                            </div>
                            <div className="form-group col-12 col-sm-6 ">
                            <label>Version</label>
                                <input type="text" className="form-control" name="version" value={version} onChange={this.Changehandler}></input>
                            </div>
                        </div>
                        <div className=" form-row ">
                            <div className="form-group col-12 col-sm-6">
                            <label> Responsible </label>
                            <select name="person1" className="custom-select" id="inputGroupSelect02"onChange={this.Changehandler}>
                                {
                                this.state.users.length ?
                                this.state.users.map(user => <option value={user.id}  >{user.username}</option>)
                                : <option value={0} >{this.state.errorMsgUser}</option>
                                }
                            </select>
                            </div>
                            <div className="form-group col-12 col-sm-6">
                            <label> Responsible </label>
                            <select name="person2" className="custom-select" id="inputGroupSelect01"onChange={this.Changehandler}>
                            
                                {
                                this.state.users.length ?
                                this.state.users.map(user => <option value={user.id}  >{user.username}</option>)
                                : <option value={0} >{this.state.errorMsgUser}</option>
                                }
                            </select>
                            </div>
                        </div> 


                        <div className=" form-row ">
                        <div className=" col-12 col-sm-12">
                            <label> licenskey </label>
                            <textarea className="form-control col-12" rows="5" value={licenskey} readOnly></textarea>
                        </div>
                        </div>
                        </div>
                        </div>
                       
                 </form>



            <form onSubmit={this.handleSubmitIp} key="Ip">
                <div >
                        <legend>Ip's</legend>
                        <div className="container"  >
                        <h1 className="title">Ip</h1>
                    <div> 
                    {
                        this.state.ips.length ?  this.state.ips.map( ip => <IpNumber ip={ip} />) : null
                    }
                    {
                        this.state.errorMsgIp ? <div>{this.state.errorMsgIp}</div> : null
                    } 
                    </div>
                    <div className=" form-row ">
                        <div className=" col-12 col-sm-2">
                        <input type="text" name="ipNumber" className="form-control " value={this.state.ipNumber} onChange={this.Changehandler}></input>
                        </div>

                        <div class="btn-group col-12 col-sm-2 ">
                        <button type="submit" className="btn btn-secondary btn-lg" value="addIp">addIp</button>
                		{/* <input type="submit" value="addIp" /> */}
                        </div>
					</div>
                    </div>
                    </div>
            </form>
            <form onSubmit={this.handleSubmitFeature} key="Feature">
                
                <legend>Features</legend>
                    <div className="container"  >
                        <h1 className="title">Feature</h1>
                    <div> 
                    {
                       this.state.features.length ? this.state.features.map(feature => <Feature feature={feature} />) : null
                    }
                    {
                        this.state.errorMsgFe ? <div>{this.state.errorMsgFe}</div> : null
                    } 
                    
                    </div>
                    <div className=" form-row ">
                        <div className=" col-12 col-sm-2">
                        <input type="text" name="feature" className="form-control" value={this.state.feature} onChange={this.Changehandler}></input>
                        </div>

                        <div class="btn-group col-12 col-sm-2 ">
                        <button type="submit" class="btn btn-secondary btn-lg" value="addIp">addFeature</button>
                		{/* <input type="submit" value="addIp" /> */}
                        </div>
                    
					</div>
                    </div>   
            </form>     

            <form onSubmit={this.handleSubmit} key="ContractUpdate" >
            <div className="mt-4 text-center">
                    <button type="submit" class="btn btn-primary btn-lg">Update Contract</button>
               </div>
            </form>  
               </div>
        );
    }
}

export default EditContract;
