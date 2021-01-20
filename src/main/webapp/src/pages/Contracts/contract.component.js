import React from 'react';
import Modal from 'react-modal';
import EditContract from './EditContract/editContract.modal';

class Contract extends React.Component {
    constructor(props) {
        super(props);
        this.state = { modalIsOpen: false, modalShow: "Edit" };
        this.handleCancel = this.handleCancel.bind(this);
        this.handleSave = this.handleSave.bind(this);
        this.createModal = this.createModal.bind(this);
        this.handleEdit = this.handleEdit.bind(this);
        this.handleDetail = this.handleDetail.bind(this);
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
            modalShow: "Edit",
            modalIsOpen: true
        });
    }

    handleDetail() {
        this.setState({
            modalShow: "Detail",
            modalIsOpen: true
        });
    }

    createModal(){
        if(this.state.modalShow === "Edit") {
            return(
                <div>
                    <EditContract></EditContract>
                    <button onClick={() => this.handleSave()}>Save</button>
                    <button onClick={() => this.handleCancel()}>Cancel</button>
                </div>
            );
        } else if(this.state.modalShow === "Detail") {
            return(
                <div>
                    <h2>{this.state.modalShow}</h2>
                    <button onClick={() => this.handleSave()}>Save</button>
                    <button onClick={() => this.handleCancel()}>Cancel</button>
                </div>
            );
        }
    }

    render() {
        return(
            <div id="rowOfTable">
                <Modal isOpen={this.state.modalIsOpen}>
                    {this.createModal()}
                </Modal>
                <div id="items">
                    <span>{this.props.company}</span>
                </div>
                <div id="items">
                    <span>{this.props.contractStart}</span>
                </div>
                <div id="items">
                    <span>{this.props.contractEnd}</span>
                </div>
                <div id="items">
                    <span>{this.props.version}</span>
                </div>
                <div id="items">
                    <button onClick={() => this.handleEdit()}>Edit</button>
                </div>
                <div class="items">
                    <button>Delete</button>
                </div>
                <div id="items">
                    <button onClick={() => this.handleDetail()}>Details</button>
                </div>
            </div>
        )
    }
}

export default Contract;