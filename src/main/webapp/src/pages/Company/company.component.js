import React from 'react';
import Modal from 'react-modal';
import EditCompany from './EditCompany/editCompany.modal';

class Company extends React.Component {
    constructor(props) {
        super(props);
        this.state = { modalIsOpen: false, modalShow: "Edit" };
        this.handleCancel = this.handleCancel.bind(this);
        this.handleSave = this.handleSave.bind(this);
        this.createModal = this.createModal.bind(this);
        this.handleEdit = this.handleEdit.bind(this);
    }

    handleEdit() {
        this.setState({
            modalIsOpen: true,
            modalShow: "Edit"
        });
    }

    handleSave() {
        this.setState({
            modalIsOpen: false
        });
    }

    handleCancel() {
        this.setState({
            modalIsOpen: false
        });
    }

    createModal(){
        if(this.state.modalShow === "Edit") {
            return(     
                <div>
                    <EditCompany></EditCompany>
                    <button onClick={() => this.handleSave()}>Save</button>
                    <button onClick={() => this.handleCancel()}>pfuschel</button>
                    
                </div>
            );
        } 
    }

    render() {
        return(
            <div>
                <Modal isOpen={this.state.modalIsOpen}>
                    {this.createModal()}
                </Modal>
                <span>{this.props.company}</span>
                <span>{this.props.addressDetailA}</span>
                <span>{this.props.addressDetailB}</span>
                <button onClick={() => this.handleEdit()}>Edit</button>
                <button>Delete</button>
                <button>Contracts</button>
                <button>Users</button>
            </div>
        )
    }
}

export default Company;