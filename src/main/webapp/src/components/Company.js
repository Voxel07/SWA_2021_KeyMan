import React from 'react'
import Modal from 'react-modal'
import axios from 'axios'
import EditCompany from '../pages/Company/EditCompany/editCompany.modal';
import ShowUsers from '../pages/Company/showUsers.modal'
import ShowContracts from '../pages/Company/showCompanys.modal'

// id | country | department | name | postalcode | state | street
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
        this.handleCancel = this.handleCancel.bind(this);
        this.createModal = this.createModal.bind(this);
        this.handleEdit = this.handleEdit.bind(this);
    }



    deleteCompany() {
        console.log(this.state);
        axios.delete("http://localhost:8080/company", { data: this.state })
            .then(response => {
                console.log(response);
                // console.log(company.id);
            })
            .catch(error => {
                console.log(error);

            })
    }

    handleEdit() {
        console.log("hanldeEdit");
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
    }
    createModal() {
        switch (this.state.modalShow) {
            case "Edit":
                return (
                    <div>
                        <EditCompany company={this.props.company}></EditCompany>
                        <button onClick={() => this.handleCancel()}>Cancel</button>
                    </div>
                );
            
            case "Contract":
                return (
                    <div>
                        <ShowContracts company={this.props.company}></ShowContracts>
                        <button onClick={() => this.handleCancel()}>Cancel</button>
                    </div>
                );
             
            case "User":
                return (
                    <div>
                        <ShowUsers company={this.props.company}></ShowUsers>
                        <button onClick={() => this.handleCancel()}>Cancel</button>
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
                            value={name} />
                    </div>

                    <div className="form-group col-11 col-sm-2 ">
                        <input
                            readOnly
                            className="form-control"
                            name="department"
                            type="text"
                            value={department} />
                    </div>
                    <div className=" col-11 col-sm-2">

                        <input
                            readOnly
                            className="form-control"
                            name="street"
                            type="text"
                            value={country} />
                    </div>

                    <div className="form-group col-11 col-sm-1">
                        <button className="btn btn-dark" onClick={() => this.handleEdit()}>Verändern</button>
                    </div>

                    <div className="form-group col-11 col-sm-1">
                        <button className="btn btn-danger" onClick={() => this.deleteCompany()}>Löschen</button>
                    </div>

                    <div className="form-group col-11 col-sm-1">
                        <button className="btn btn-dark" onClick={() => this.handleContract()} >Contracts</button>
                    </div>

                    <div className="form-group col-11 col-sm-1">
                        <button className="btn btn-dark" onClick={() => this.handleUser()}>Users</button>
                    </div>
                </div>
            </div>
        )
    }

}

export default Company
