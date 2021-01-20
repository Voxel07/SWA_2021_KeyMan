import React from 'react';
import Modal from 'react-modal';
import EditUser from './EditUser/editUser.modal';

class User extends React.Component {
    constructor(props) {
        super(props);
        this.state = { modalIsOpen: false };
        this.handleEdit = this.handleEdit.bind(this);
        this.handleDelete = this.handleDelete.bind(this);
        this.createModal = this.createModal.bind(this);
        this.handleCancel = this.handleCancel.bind(this);
        this.handleSave = this.handleSave.bind(this);
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

    handleEdit() {
        this.setState({
            modalIsOpen: true
        });
    }

    handleDelete() {

    }

    createModal(){
        return(
            <div>
                <EditUser></EditUser>
                <button onClick={() => this.handleSave()}>Save</button>
                <button onClick={() => this.handleCancel()}>Cancel</button>
            </div>
        );
    }


    render() {
        return(
            <div>
                <Modal isOpen={this.state.modalIsOpen}>
                    {this.createModal()}
                </Modal>
                <span>{this.props.company}</span>
                <span>{this.props.userName}</span>
                <span>{this.props.email}</span>
                <button onClick={() => this.handleEdit()}>Edit</button>
                <button onClick={() => this.handleDelete()}>Delete</button>
            </div>
        )
    }
}

export default User;