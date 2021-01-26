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
            feature: '',
            features: [],
            ips: [],
            companyName: this.props.companyName,
            companyId: this.props.companyId,
            errorMsgIp: '',
            errorMsgFe: '',
            errorMsgCp: '',
            errorMsgUser: '',
            users: [],
            contractUserName1: '',
            contractUserName2: '',
            person1: '',
            person2: ''
        };
    }
    componentDidMount() {
        this.getFeatures();
        this.getIps();
        this.getUsers();
        this.getContractUsers();
    }

    Changehandler = (event) => {
        this.setState({ [event.target.name]: event.target.value })
    }
    handleSubmit = event => {
        event.preventDefault();
        axios.post('http://localhost:8080/contract', this.state)
            .then(response => {
                if (this.state.person1 !== '') {
                    this.addUser(this.state.person1);
                }
                if (this.state.person2 !== '') {
                    this.addUser(this.state.person2);
                }
                console.log("state in edit: ")
                console.log(this.state)
                this.props.cbToCtrJs(this.state);
            })
            .catch(error => {
            })
    }
    addUser = person => {
        axios.post('http://localhost:8080/contract', { id: this.state.id }, { params: { usrId: person } })
            .then(response => {
            })
            .catch(error => {
            })
    }

    handleSubmitIp = event => {
        event.preventDefault();
        axios.put('http://localhost:8080/IpNumber/' + this.props.contract.id, { ipNumber: this.state.ipNumber })
            .then(response => {
                this.getIps();
                this.setState({ ipNumber: '' });
            })
            .catch(error => {
            })
    }

    getIps() {
        axios.get('http://localhost:8080/IpNumber', { params: { ctrId: this.state.id } })
            .then(response => {
                if (response.data.length === 0) {
                    this.setState({ ips: response.data });
                    this.setState({ errorMsgIp: 'Keine IP Daten erhalten' })
                }
                else {
                    this.setState({ ips: response.data });
                    this.setState({ errorMsgIp: '' })
                }
            })
            .catch(error => {
                this.setState({ errorMsgIp: " " + error })
            })
    }

    handleSubmitFeature = event => {
        event.preventDefault();
        axios.put('http://localhost:8080/feature/' + this.props.contract.id, { number: this.state.feature })
            .then(response => {
                this.getFeatures();
                this.setState({ feature: '' });
            })
            .catch(error => {
            })
    }

    getFeatures() {
        axios.get('http://localhost:8080/feature', { params: { ctrId: this.state.id } })
            .then(response => {
                if (response.data.length === 0) {
                    this.setState({ features: response.data });
                    this.setState({ errorMsgFe: 'Keine Feature Daten erhalten' })
                }
                else {
                    this.setState({ features: response.data });
                    this.setState({ errorMsgFe: '' })
                }
            })
            .catch(error => {
                this.setState({ errorMsgFe: '' + error })
            })
    }

    getContractUsers() {
        axios.get('http://localhost:8080/user', { params: { ctrIdU: this.state.id } })
            .then(response => {
                if (response.data.length === 0) {
                    this.setState({ errorMsgUser: 'Keine ContractUser Daten erhalten' })
                    this.setState({ contractUserName1: 'User wählen', contractUserName2: 'User wählen' });
                }
                if (response.data.length === 1) {
                    this.setState({ contractUserName1: response.data[0].username, contractUserName2: 'User wählen' });
                }
                if (response.data.length === 2) {
                    this.setState({ contractUserName1: response.data[0].username, contractUserName2: response.data[1].username });
                }
            })
            .catch(error => {
                this.setState({ errorMsgUser: " " + error })
            })
    }

    getUsers() {
        axios.get('http://localhost:8080/user', { params: { companyId: this.state.companyId } })
            .then(response => {
                if (response.data.length === 0) {
                    this.setState({ errorMsgUser: 'Keine CompanyUser Daten erhalten' })
                }
                else {
                    this.setState({ users: response.data });
                    this.setState({ errorMsgUser: '' })
                }
            })
            .catch(error => {
                this.setState({ errorMsgUser: " " + error })
            })
    }

    
    handleCallback = (test, id) => {
        switch (test) {
            case 'Ip':
                this.getIps();
                break;
            case 'Feature':
                this.getFeatures();
                break;

            default:
                break;
        }
    }

    handleRemove = (company) => {
        const newList = this.state.companys.filter((item) => item.id !== company.id);
        this.setState({ companys: newList })
    }

    render() {
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
                                    <input type="Date" className="form-control1" name="startDate" value={startDate} onChange={this.Changehandler}></input>
                                </div>
                                <div className="form-group col-12 col-sm-6">
                                    <label> EndDate </label>
                                    <input type="Date" className="form-control1" name="endDate" value={endDate} onChange={this.Changehandler}></input>
                                </div>
                            </div>
                            <div className=" form-row ">
                                <div className="form-group col-12 col-sm-6">
                                    <label> Company </label>
                                    <input type="text" className="form-control1" name="name" value={companyName} readOnly></input>
                                </div>
                                <div className="form-group col-12 col-sm-6 ">
                                    <label>Version</label>
                                    <input type="text" className="form-control1" name="version" value={version} onChange={this.Changehandler}></input>
                                </div>
                            </div>
                            <div className=" form-row ">
                                <div className="form-group col-12 col-sm-6">
                                    <label> Responsible </label>
                                    <select name="person1" className="custom-selected" id="inputGroupSelect02" onChange={this.Changehandler}>
                                        {
                                            <option>{this.state.contractUserName1}</option>
                                        }
                                        {
                                            this.state.users.length ?
                                                this.state.users.map(user => <option value={user.id}  >{user.username}</option>)
                                                : <option value={0} >{this.state.errorMsgUser}</option>
                                        }
                                    </select>
                                </div>
                                <div className="form-group col-12 col-sm-6">
                                    <label> Responsible </label>
                                    <select name="person2" className="custom-selected" id="inputGroupSelect01" onChange={this.Changehandler}>
                                        {

                                            <option>{this.state.contractUserName2}</option>
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
                                <div className=" form-group col-12 col-sm-12">
                                    <label> licenskey </label>
                                    <textarea className="form-control1 col-12" rows="5" name="licenskey" value={licenskey} readOnly></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="mt-4 text-center">
                        <button type="submit" class="btn-primary1">Update Contract</button>
                    </div>
                </form>

                <form onSubmit={this.handleSubmitIp} key="Ip">
                    <div >
                        <legend>Ip's</legend>
                        <div className="container"  >
                            <h1 className="title">Ip</h1>

                            {
                                this.state.ips.length ? this.state.ips.map(ip => <IpNumber ip={ip} cbToEditCon={this.handleCallback} />) : null
                            }
                            {
                                this.state.errorMsgIp ? <div>{this.state.errorMsgIp}</div> : null
                            }

{
                            this.state.ips.length < 3 ?  <div className=" form-row ">
                                <div className=" col-12 col-sm-2">
                                    <input type="text" name="ipNumber" className="form-control1" value={this.state.ipNumber} onChange={this.Changehandler}></input>
                                </div>

                                <div class="btn-group col-12 col-sm-2 ">
                                    <button type="submit" class="btn-secondary1" value="addfeature">addIp</button>
                                </div>
                            </div>
                             : null
                        }
                        </div>
                    </div>
                </form>

                <form onSubmit={this.handleSubmitFeature} key="Feature">
                    <legend>Features</legend>
                    <div className="container"  >
                        <h1 className="title">Feature</h1>

                        {
                            this.state.features.length ? this.state.features.map(feature => <Feature feature={feature} cbToEditCon={this.handleCallback} />) : null
                        }
                        {
                            this.state.errorMsgFe ? <div>{this.state.errorMsgFe}</div> : null
                        }
                        {
                            this.state.features.length < 3 ? 
                             <div className=" form-row ">
                                <div className=" col-12 col-sm-2">
                                    <input type="text" name="feature" className="form-control1" value={this.state.feature} onChange={this.Changehandler}></input>
                                </div>

                                <div class="btn-group col-12 col-sm-2 ">
                                    <button type="submit" class="btn-secondary1" value="addfeature">addFeature</button>
                                </div>
                            </div>
                            : null
                        }

                    </div>
                </form>
            </div>
        );
    }
}

export default EditContract;
