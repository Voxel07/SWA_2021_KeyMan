import React from 'react'
import Modal from 'react-modal'
import axios from 'axios'
import EditCompany from '../pages/Company/EditCompany/editCompany.modal';
import ShowUsers from '../pages/Company/showUsers.modal'
import ShowContracts from '../pages/Company/showContracts.modal'
import '../css/buttons.css';
import UserService from '../pages/Login/user.service';

class Company extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            id: this.props.company.id,
            name: this.props.company.name,
            department: this.props.company.department,
            street: this.props.company.street,
            postalcode: this.props.company.postalcode,
            state: this.props.company.state,
            country: this.props.company.country,
            modalIsOpen: false, modalShow: "Edit"
        };

        this.deleteCompany = this.deleteCompany.bind(this);
    }

    deleteCompany() {
        axios.delete("http://localhost:8080/company", { data: this.state })
            .then(response => {
                this.props.parentCallback("DELETE", this.state);
            })
            .catch(error => {
            })
    }
    handleCallBack = (changendCompany) => {
        this.setState(changendCompany);
    }

    handleEdit() {
        this.setState({
            modalIsOpen: true,
            modalShow: "Edit"
        });
    }
    handleContract() {
        this.setState({
            modalIsOpen: true,
            modalShow: "Contract"
        });
    }
    handleUser() {
        this.setState({
            modalIsOpen: true,
            modalShow: "User"
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
                        <ul className="nav justify-content-end">
                            <li className="nav-item">
                                <i className="bi bi-file-excel-fill"></i>
                                <button type="button" className="close" aria-label="Close" onClick={() => this.handleCancel()}>
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-file-excel-fill" color="red" viewBox="0 0 16 16">
                                        <path d="M12 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zM5.884 4.68L8 7.219l2.116-2.54a.5.5 0 1 1 .768.641L8.651 8l2.233 2.68a.5.5 0 0 1-.768.64L8 8.781l-2.116 2.54a.5.5 0 0 1-.768-.641L7.349 8 5.116 5.32a.5.5 0 1 1 .768-.64z" />
                                    </svg>
                                </button>

                            </li>
                        </ul>
                        <EditCompany company={this.props.company} CallbackToCompanyJS={this.handleCallBack}></EditCompany>
                    </div>
                );

            case "Contract":
                return (
                    <div>
                        <ul className="nav justify-content-end">
                            <li className="nav-item">
                                <i className="bi bi-file-excel-fill"></i>
                                <button type="button" className="close" aria-label="Close" onClick={() => this.handleCancel()}>
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-file-excel-fill" color="red" viewBox="0 0 16 16">
                                        <path d="M12 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zM5.884 4.68L8 7.219l2.116-2.54a.5.5 0 1 1 .768.641L8.651 8l2.233 2.68a.5.5 0 0 1-.768.64L8 8.781l-2.116 2.54a.5.5 0 0 1-.768-.641L7.349 8 5.116 5.32a.5.5 0 1 1 .768-.64z" />
                                    </svg>
                                </button>
                            </li>
                        </ul>
                        <ShowContracts company={this.props.company}></ShowContracts>
                    </div>
                );

            case "User":
                return (
                    <div>
                        <ul className="nav justify-content-end">
                            <li className="nav-item">
                                <li className="bi bi-file-excel-fill"></li>
                                <button type="button" className="close" aria-label="Close" onClick={() => this.handleCancel()}>
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-file-excel-fill" color="red" viewBox="0 0 16 16">
                                        <path d="M12 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zM5.884 4.68L8 7.219l2.116-2.54a.5.5 0 1 1 .768.641L8.651 8l2.233 2.68a.5.5 0 0 1-.768.64L8 8.781l-2.116 2.54a.5.5 0 0 1-.768-.641L7.349 8 5.116 5.32a.5.5 0 1 1 .768-.64z" />
                                    </svg>
                                </button>
                            </li>
                        </ul>
                        <ShowUsers company={this.props.company}></ShowUsers>
                    </div>
                );

            default:
                break;
        }
    }
    render() {
        const { name, department, country } = this.props.company
        return (
            <div key={this.props.company.id} >
                <Modal isOpen={this.state.modalIsOpen}
                    ariaHideApp={false}
                >
                    {this.createModal()}
                </Modal>
                <div className=" form-row ">

                    <div className="form-group col-11 col-sm-2  my-sm-2 mx-2 ">
                        <input
                            readOnly
                            name="name"
                            className="form-control1 "
                            type="text"
                            value={name} />
                    </div>

                    <div className="form-group col-11 col-sm-2 my-sm-2 ">
                        <input
                            readOnly
                            className="form-control1"
                            name="department"
                            type="text"
                            value={department} />
                    </div>
                    <div className=" form-group col-11 col-sm-2  my-sm-2">

                        <input
                            readOnly
                            className="form-control1"
                            name="street"
                            type="text"
                            value={country} />
                    </div>
                    {
                        UserService.getAdmin() ? <div>
                            <div className="btn-group">
                                <div className="form-group col-12 col-sm-1 my-sm-2">
                                    <button className=" btn-dark1" onClick={() => this.handleEdit()}>Verändern</button>
                                </div>
                            </div>

                            <div className="btn-group">
                                <div className="form-group col-12 col-sm-1 my-sm-2">
                                    <button className=" btn-danger1" onClick={() => this.deleteCompany()}>Löschen</button>
                                </div>
                            </div>
                        </div> : null
                    }
                    <div className="btn-group">
                        <div className="form-group col-11 col-sm-1 my-sm-2">
                            <button className=" btn-dark1" onClick={() => this.handleContract()} >Contracts</button>
                        </div>
                    </div>
                    <div className="btn-group">
                        <div className="form-group col-11 col-sm-1 my-sm-2">
                            <button className=" btn-dark1" onClick={() => this.handleUser()}>Users</button>
                        </div>
                    </div>

                </div>
            </div>
        )
    }

}

export default Company
