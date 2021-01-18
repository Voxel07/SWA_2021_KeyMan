import React from 'react';
import authService from '../Login/auth.service';
import { withRouter } from 'react-router-dom';
import { PersonSquare, BoxArrowRight } from 'react-bootstrap-icons';
import Modal from 'react-modal';
import AddCustomer from '../Customers/AddCustomer/addCustomer.modal';
import AddContract from '../Contracts/AddContract/addContract.modal';
import AddUser from '../Users/AddUser/addUser.modal';


import './bar.component.scss';
import User from '../User/user.modal';

export class BarComponent extends React.Component {

    constructor(props) {
        super(props);
        this.state = { modalIsOpen: false, modalShow: "Customer" };
        this.handleLogout = this.handleLogout.bind(this);
        this.handleAdd = this.handleAdd.bind(this);
        this.handleCancel = this.handleCancel.bind(this);
        // this.handleSave = this.handleSave.bind(this);
        this.createModal = this.createModal.bind(this);
        this.handleUser = this.handleUser.bind(this);
    }

    // handleSave() {
    //     this.setState({
    //         modalIsOpen: false
    //     });
    // }

    handleCancel() {
        this.setState({
            modalIsOpen: false
        });
    }

    handleUser() {
        this.setState({
            modalIsOpen: true,
            modalShow: "Nutzer"
        });
    }

    handleAdd() {
        if(this.props.table === "Customers") {
            this.setState({
                modalShow: "Customer",
                modalIsOpen: true
            });
        } else if(this.props.table === "Contracts") {
            this.setState({
                modalShow: "Contract",
                modalIsOpen: true
            });
        } else if(this.props.table === "Users") {
            this.setState({
                modalShow: "User",
                modalIsOpen: true
            });
        }
    }

    handleLogout() {
        authService.logout(() => {
            this.props.history.push("/");
        });
    }

    createModal(){
        
        if(this.state.modalShow === "Customer") {
            return(
                <div>
                    <h2>Add {this.state.modalShow}</h2>
                    <AddCustomer></AddCustomer>
                    {/* <button onClick={() => this.handleSave()}>Save</button> */}
                    <button onClick={() => this.handleCancel()}>Cancel</button>
                </div>
            );
        } else if(this.state.modalShow === "Contract") {
            return(
                <div>
                    <h2>Add {this.state.modalShow}</h2>
                    <AddContract></AddContract>
                    {/* <button onClick={() => this.handleSave()}>Save</button> */}
                    <button onClick={() => this.handleCancel()}>Cancel</button>
                </div>
            );
        } else if(this.state.modalShow === "User") {
            return(
                <div>
                    <h2>Add {this.state.modalShow}</h2>
                    <AddUser></AddUser>
                    {/* <button onClick={() => this.handleSave()}>Save</button> */}
                    <button onClick={() => this.handleCancel()}>Cancel</button>
                </div>
            );
        } else if(this.state.modalShow === "Nutzer") {
            return(
                <div>
                    <h2>User</h2>
                    <User></User>
                    <button onClick={() => this.handleCancel()}>Close</button>
                </div>
            );
        }
    }


    render(){
        return(
            <div id="bar">
                <Modal isOpen={this.state.modalIsOpen}>
                    {this.createModal()}
                </Modal>
                <div id="barItemsLeft">
                    <h3>{this.props.table}</h3>
                </div>
                <div id="barItemsLeft">
                    <button onClick={() => this.handleAdd()}>Add</button>
                </div>
                <div id="barItemsLeft">
                    <input defaultValue="filter"></input>
                </div>
                <div id="barItemsLeft">
                    <PersonSquare id="icon" onClick={() => this.handleUser()}></PersonSquare>
                </div>
                <div id="barItemsLeft">
                    <BoxArrowRight id="icon" onClick={() => this.handleLogout()}></BoxArrowRight>
                </div>
            </div>
        );
    }
}

export default withRouter(BarComponent);