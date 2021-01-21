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
            company:'',
            errorMsgIp: '',
            errorMsgFe: '',
            errorMsgCp: ''
        };
    }
    componentWillMount() {
        console.log("Daten Holen !");
        this.getCompany();
        this.getFeatures();
        this.getIps();
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
		console.log(this.state.ipNumber);
        axios.put('http://localhost:8080/IpNumber/'+this.props.contract.id, {ipNumber : this.state.ipNumber})
            .then(response => {
                console.log(response)
            })
            .catch(error => {
                console.log(error)
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
    getIps() {
        axios.get('http://localhost:8080/IpNumber', { params: { ctrId: this.state.id } })
        .then(response => {
            console.log(response);
            this.setState({ ips: response.data });
            if (response.data.length == 0) {
                this.setState({ errorMsgIp: 'Keine IP Daten erhalten' })
            }

        })
            .catch(error => {
                // console.log(error);
                this.setState({ errorMsgIp: " " + error })
            })
    }
 
    render() {
        //Daten holen Obacht !!
        const { company, startDate, endDate, version, licenskey } = this.state
        return (
            <div>
                <form onSubmit={this.handleSubmit}>
                    <div >
                        <legend>Edit Contract:</legend>
                        <div class="container"  >
                        <h1 class="title">Edit Contract</h1>

                        <div className=" form-row ">
                            <div className="form-group col-12 col-sm-6 ">
                            <label>StartDate</label>
                            <input type="Date" name="startDate" value={startDate} onChange={this.Changehandler}></input>
                            </div>
                            <div className="col-12 col-sm-6">
                            <label> EndDate </label>
                            <input type="Date" name="endDate" value={endDate} onChange={this.Changehandler}></input>
                        </div>

                        <div className=" form-row ">
                            <div className="form-group col-6 col-sm-6">
                            <label> Company </label>
                                <input type="text" name="name" value={company} readOnly></input>
                            </div>
                            <div className="form-group col-12 col-sm-6 ">
                            <label>Version</label>
                            <input type="text" name="version" value={version} onChange={this.Changehandler}></input>
                            </div>
                            </div>
                        <div className=" form-row ">
                            <div className="form-group col-12 col-sm-6">
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
                        <div className=" col-12 col-sm-12">
                            <label> licenskey </label>
                            <textarea class="form-group col-12" rows="5" value={licenskey} onChange={this.Changehandler}></textarea>
                        </div>
                        </div>
                        </div>
                        </div>
                        </div>
                 </form>
            <form onSubmit={this.handleSubmitIp}>
                <fieldset>
                <legend>Ip´s</legend>
                    <label>Ip</label>
                    <div> 
                    {
                        this.state.ips.length ?  this.state.ips.map( ip => <IpNumber ip={ip} />) : null
                    }
                    {
                        this.state.errorMsgIp ? <div>{this.state.errorMsgIp}</div> : null
                    } 
                    </div>
					<div>
						<input type="text" name="ipNumber" value={this.state.ipNumber} onChange={this.Changehandler}></input>
                		<input type="submit" value="addIp" />
					</div>
                </fieldset>
            </form>
            <form onSubmit={this.handleSubmitFeature}>
                <fieldset>
                <legend>Features</legend>
                    <label>Feature</label>
                    <div> 
                    {
                       this.state.features.length ? this.state.features.map(feature => <Feature feature={feature} />) : null
                    }
                    {
                        this.state.errorMsgFe ? <div>{this.state.errorMsgFe}</div> : null
                    } 
                    </div>
                    <div>
						<input type="text" name="feature" value={this.state.feature} onChange={this.Changehandler}></input>
                		<input type="submit" value="addFeature" />
					</div>
                </fieldset>
            </form>     
            </div>
        );
    }
}

export default EditContract;


                   {/* <label>Company</label>
                    <input type="text" name="name" value={company} readOnly></input>
                  
                    <label>Licenskey:</label>
                    <input type="text" name="licenskey" value={licenskey} onChange={this.Changehandler}></input>
           */}