import React from 'react';
import axios from 'axios'
class EditContract extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            startDate: this.props.contract.startDate,
            endDate: this.props.contract.endDate,
            version: this.props.contract.version,
            licenskey: this.props.contract.licenskey,
            id: this.props.contract.id,
            features:[],
            ips:[],
            company:this.props.contract.companyId 
        };
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

    getCompany() {
        axios.get('http://localhost:8080/company', { params: { company_id: this.state.companyId } })
            .then(response => {
                console.log(response);
                this.setState({ company: response.data });
                if (response.data.length == 0) {
                    this.setState({ errorMsg: 'Keine Company Daten erhalten' })
                }
            })
            .catch(error => {
                // console.log(error);
                this.setState({ errorMsg: " " + error })
            })
    }

    getFeatures() {
        axios.get('http://localhost:8080/feature', { params: { ctrId: this.state.id } })
        .then(response => {
            console.log(response);
            this.setState({ features: response.data });
            if (response.data.length == 0) {
                this.setState({ errorMsg: 'Keine Feature Daten erhalten' })
            }

        })
            .catch(error => {
                // console.log(error);
                this.setState({ errorMsg: " " + error })
            })
    }
    getIps() {
        axios.get('http://localhost:8080/IpNumber', { params: { ctrId: this.state.id } })
        .then(response => {
            console.log(response);
            this.setState({ ips: response.data });
            if (response.data.length == 0) {
                this.setState({ errorMsg: 'Keine IP Daten erhalten' })
            }

        })
            .catch(error => {
                // console.log(error);
                this.setState({ errorMsg: " " + error })
            })
    }
 
    render() {
        const { company, startDate, endDate, version, licenskey } = this.state
       
        return (
            <form onSubmit={this.handleSubmit}>
                <fieldset>
                    <legend>Edit Contract:</legend>
                    <label>Company</label>
                    <input type="text" name="name" value={company} readOnly></input>
                    <label>StartDate</label>
                    <input type="Date" name="startDate" value={startDate} onChange={this.Changehandler}></input>
                    <label>EndDate</label>
                    <input type="Date" name="endDate" value={endDate} onChange={this.Changehandler}></input>
                    <label>Version</label>
                    <input type="text" name="version" value={version} onChange={this.Changehandler}></input>
                    <label>Licenskey:</label>
                    <input type="text" name="licenskey" value={licenskey} onChange={this.Changehandler}></input>
                </fieldset>
                <div> <input type="submit" value="Submit" /></div>
            </form>
        );
    }
}

export default EditContract;