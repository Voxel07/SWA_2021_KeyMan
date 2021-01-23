import React, { Component } from 'react'
import axios from 'axios';


export default class showDetails extends Component {
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
        const { companyName, startDate, endDate, version, licenskey } = this.state
        return (
            <div>
                <form onSubmit={this.handleSubmit} key="Contract" >
                    <div >
                        <legend>Contract Details from Company:{companyName}S</legend>
                        <div className="container"  >
                        <h1 className="title">Contract Details</h1>


                        <div className=" form-row ">
                            <div className="form-group col-12 col-sm-6 ">
                            <label>StartDate</label>
                            <input type="date" className="form-control" name="startDate" value={startDate} readOnly ></input>
                            </div>
                            <div className="col-12 col-sm-6">
                            <label> EndDate </label>
                            <input type="Date" className="form-control" name="endDate" value={endDate} readOnly ></input>
                        </div>
                        </div>
                        <div className=" form-row ">
                            <div className="form-group col-12 col-sm-6">
                            <label> Company </label>
                                <input type="text" className="form-control" name="name" value={companyName} readOnly></input>
                            </div>
                            <div className="form-group col-12 col-sm-6 ">
                            <label>Version</label>
                                <input type="text" className="form-control" name="version" value={version} readOnly ></input>
                            </div>
                        </div>
                        <div className=" form-row ">
                            {
                              this.state.users.length ? 
                              this.state.users.map( user =><div className="form-group col-12 col-sm-6 "><label> Responsible </label> <input  className="form-control"  type="text" value={user.username} readOnly></input></div>) 
                              : null
                            }
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


            <form key="Ip">
                <div >
                        <legend>Ip's</legend>
                        <div className="container"  >
                            <h1 className="title">Ip</h1>
                        <div className=" form-row ">
                            {
                              this.state.ips.length ? 
                              this.state.ips.map( ip =><div className="form-group col-12 col-sm-4">
                                  <label> Ip </label>
                                   <input  className="form-control" type="text" value={ip.ipNumber} readOnly></input>
                                   </div>) 
                              : null
                            }
                        </div> 
                    </div>
                    </div>
            </form>
            <form key="Feature">
                
                <legend>Features</legend>
                    <div className="container"  >
                        <h1 className="title">Feature</h1>
                        <div className=" form-row ">
                            {
                              this.state.features.length ? 
                              this.state.features.map( feature =><div className="form-group col-12 col-sm-4">
                                  <label> Feature </label>
                                   <input  className="form-control" type="text" value={feature.number} readOnly></input>
                                   </div>) 
                              : null
                            }
                        </div> 
                   
                    </div>   
            </form>     
            </div>
            
            
        );
    }
}
