import React from 'react'
import Modal from 'react-modal'
import axios from 'axios'
import EditContract from '../pages/Contracts/EditContract/editContract.modal'
import ShowDetails from '../pages/Contracts/showContractDetails.modal'
import UserService from '../pages/Login/user.service';

class Contract extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            startDate: this.props.contract.startDate,
            endDate: this.props.contract.endDate,
            version: this.props.contract.version,
            licensKey: this.props.contract.licensKey,
            id: this.props.contract.id,
            companyName: '',
            companyId: '',
            modalIsOpen: false, modalShow: "Edit"
        };

        this.deleteContract = this.deleteContract.bind(this);
        this.handleCancel = this.handleCancel.bind(this);
        this.createModal = this.createModal.bind(this);
        this.handleEdit = this.handleEdit.bind(this);
        this.handleDetails = this.handleDetails.bind(this);
    }
    componentDidMount() {
        this.getCompany();
    }

    getCompany() {
        axios.get("http://localhost:8080/company", { params: { ctrId: this.state.id } })
            .then(response => {
                this.setState({ companyName: response.data[0].name, companyId: response.data[0].id })
            })
            .catch(error => {
            })
    }

    deleteContract() {
        axios.delete("http://localhost:8080/contract", { data: this.state })
            .then(response => {
                this.props.parentCallback("DELETE", this.state);
            })
            .catch(error => {
            })
    }
    handleCallBack = (changedContract) => {
        this.setState(changedContract);
    }

    handleDetails() {
        this.setState({
            modalIsOpen: true,
            modalShow: "Detail"
        });
    }

    handleEdit() {
        this.setState({
            modalIsOpen: true,
            modalShow: "Edit"
        });
    }

    handleCancel() {
        this.setState({
            modalIsOpen: false
        });
        this.props.parentCallback("UPDATE", this.state);
    }

    createModal() {
        switch (this.state.modalShow) {
            case "Edit":
                return (
                    <div>
                        <ul class="nav justify-content-end">
                            <li class="nav-item">
                                <i class="bi bi-file-excel-fill"></i>
                                <button type="button" class="close" aria-label="Close" onClick={() => this.handleCancel()}>
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-excel-fill" color="red" viewBox="0 0 16 16">
                                        <path d="M12 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zM5.884 4.68L8 7.219l2.116-2.54a.5.5 0 1 1 .768.641L8.651 8l2.233 2.68a.5.5 0 0 1-.768.64L8 8.781l-2.116 2.54a.5.5 0 0 1-.768-.641L7.349 8 5.116 5.32a.5.5 0 1 1 .768-.64z" />
                                    </svg>
                                </button>
                            </li>
                        </ul>
                        <EditContract contract={this.props.contract} companyName={this.state.companyName}
                            companyId={this.state.companyId} cbToCtrJs={this.handleCallBack}></EditContract>
                    </div>
                );

            case "Detail":
                return (
                    <div>
                        <ul class="nav justify-content-end">
                            <li class="nav-item">
                                <i class="bi bi-file-excel-fill"></i>
                                <button type="button" class="close" aria-label="Close" onClick={() => this.handleCancel()}>
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-excel-fill" color="red" viewBox="0 0 16 16">
                                        <path d="M12 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zM5.884 4.68L8 7.219l2.116-2.54a.5.5 0 1 1 .768.641L8.651 8l2.233 2.68a.5.5 0 0 1-.768.64L8 8.781l-2.116 2.54a.5.5 0 0 1-.768-.641L7.349 8 5.116 5.32a.5.5 0 1 1 .768-.64z" />
                                    </svg>
                                </button>
                            </li>
                        </ul>
                        <ShowDetails contract={this.props.contract} companyName={this.state.companyName}
                            companyId={this.state.companyId}></ShowDetails>
                    </div>
                );

            default:
                break;
        }
    }

    render() {
        const { startDate, endDate, version } = this.props.contract
        return (
            <div key={this.props.contract.id} >
                <Modal isOpen={this.state.modalIsOpen}
                    ariaHideApp={false}
                >
                    {this.createModal()}

                </Modal>
                <div className=" form-row ">

                    <div className="form-group col-11 col-sm-2 my-sm-2 mx-2">
                        <input
                            readOnly
                            name="name"
                            className="form-control1 "
                            type="text"
                            value={this.state.companyName}
                        />
                    </div>
                    <div className="form-group col-11 col-sm-2 my-sm-2 ">
                        <input
                            readOnly
                            className="form-control1"
                            name="department"
                            type="text"
                            value={startDate} />
                    </div>
                    <div className=" col-11 col-sm-2 my-sm-2">
                        <input
                            readOnly
                            className="form-control1"
                            name="street"
                            type="text"
                            value={endDate} />
                    </div>
                    <div className=" col-11 col-sm-2 my-sm-2">
                        <input
                            readOnly
                            className="form-control1"
                            name="street"
                            type="text"
                            value={version} />
                    </div>
                    <div>
                        {
                            UserService.getAdmin() ? <div>
                                <div className="btn-group">
                                    <div className="form-group col-12 col-sm-1 my-sm-2">
                                        <button className=" btn-dark1" onClick={() => this.handleEdit()}>Verändern</button>
                                    </div>
                                </div>

                                <div className="btn-group">
                                    <div className="form-group col-12 col-sm-1 my-sm-2">
                                        <button className=" btn-danger1" onClick={() => this.deleteContract()}>Löschen</button>
                                    </div>
                                </div>
                            </div> : null
                        }
                        <div className="btn-group">
                            <div className="form-group col-12 col-sm-1 my-sm-2">
                                <button className=" btn-dark1" onClick={() => this.handleDetails()} >Details</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default Contract
