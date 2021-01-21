import React from 'react'
import Modal from 'react-modal'
import axios from 'axios'
import EditContract from '../pages/Contracts/EditContract/editContract.modal'
import ShowDetails from '../pages/Contracts/showDetails.modal'


// id | country | department | name | postalcode | state | street
class Contract extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            startDate: this.props.contract.startDate,
            endDate: this.props.contract.endDate,
            version: this.props.contract.version,
            licensKey: this.props.contract.licensKey,
            id: this.props.contract.id,
            companyName:'',
            modalIsOpen: false, modalShow: "Edit"
        };

        this.deleteContract = this.deleteContract.bind(this);
        this.handleCancel = this.handleCancel.bind(this);
        this.createModal = this.createModal.bind(this);
        this.handleEdit = this.handleEdit.bind(this);
        this.handleDetails = this.handleDetails.bind(this);
    }
    componentWillMount() {
        this.getCompany();
    }


    getCompany(){
        console.log(this.state);
        axios.get("http://localhost:8080/company/"+ this.props.contract.companyId)
            .then(response => {
               console.log(response);
               this.setState({[this.state.companyName]:response.data.name})
            })
            .catch(error => {
                console.log(error);

            })
    }


    deleteContract() {
        console.log(this.state);
        axios.delete("http://localhost:8080/contract", { data: this.state })
            .then(response => {
                console.log(response);
                // console.log(contract.id);
            })
            .catch(error => {
                console.log(error);

            })
    }
    handleDetails() {
        console.log("handleDetails");
        this.setState({
            modalIsOpen: true,
            modalShow: "Detail"
        });
    }
    handleEdit() {
        console.log("hanldeEdit");
        this.setState({
            modalIsOpen: true,
            modalShow: "Edit"
        });
    }

    handleCancel() {
        this.setState({
            modalIsOpen: false
        });
    }
    createModal() {
        switch (this.state.modalShow) {
            case "Edit":
                return (
                    <div>
                        <EditContract contract={this.props.contract}></EditContract>
                        <button onClick={() => this.handleCancel()}>Cancel</button>
                    </div>
                );

            case "Detail":
                return (
                    <div>
                        <ShowDetails contract={this.props.contract}></ShowDetails>
                        <button onClick={() => this.handleCancel()}>Cancel</button>
                    </div>
                );

            default:
                break;
        }
    }
    render() {
        const {startDate, endDate, version } = this.props.contract
        return (
            <div key={this.props.contract.id} >
                <Modal isOpen={this.state.modalIsOpen}>
                    {this.createModal()}
                </Modal>
                <div className=" form-row ">

                    <div className="form-group col-11 col-sm-2">
                        <input
                            readOnly
                            name="name"
                            className="form-control "
                            type="text"
                            value={this.state.companyName} 
                            />
                    </div>
                    <div className="form-group col-11 col-sm-2 ">
                        <input
                            readOnly
                            className="form-control"
                            name="department"
                            type="text"
                            value={startDate} />
                    </div>
                    <div className=" col-11 col-sm-2">
                        <input
                            readOnly
                            className="form-control"
                            name="street"
                            type="text"
                            value={endDate} />
                    </div>
                    <div className=" col-11 col-sm-2">
                        <input
                            readOnly
                            className="form-control"
                            name="street"
                            type="text"
                            value={version} />
                    </div>

                    <div className="form-group col-11 col-sm-1">
                        <button className="btn btn-dark" onClick={() => this.handleEdit()}>Verändern</button>
                    </div>

                    <div className="form-group col-11 col-sm-1">
                        <button className="btn btn-danger" onClick={() => this.deletecontract()}>Löschen</button>
                    </div>

                    <div className="form-group col-11 col-sm-1">
                        <button className="btn btn-dark" onClick={() => this.handleDetails()} >Details</button>
                    </div>

                </div>
            </div>
        )
    }

}

export default Contract
