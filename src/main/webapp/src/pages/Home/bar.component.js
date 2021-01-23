import React from 'react';
import authService from '../Login/auth.service';
import { withRouter } from 'react-router-dom';
import { PersonSquare, BoxArrowRight } from 'react-bootstrap-icons';
import Modal from 'react-modal';
import AddCompany from '../Company/AddCompany/addCompany.modal';
import AddContract from '../Contracts/AddContract/addContract.modal';
import AddUser from '../Users/AddUser/addUser.modal';


import './bar.component.scss';
import User from '../User/user.modal';

export class BarComponent extends React.Component {

    constructor(props) {
        super(props);
        this.state = { modalIsOpen: false, modalShow: "Company" };
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
        if(this.props.table === "Companys") {
            this.setState({
                modalShow: "Company",
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
        
        if(this.state.modalShow === "Company") {
            return(
                <div>
                    
                    <ul class="nav justify-content-end">
                        <li class="nav-item">
                        <i class="bi bi-file-excel-fill"></i>
                        <button  type="button" class="close" aria-label="Close" onClick={() => this.handleCancel()}>
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-excel-fill" color="red" viewBox="0 0 16 16">
                                <path d="M12 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zM5.884 4.68L8 7.219l2.116-2.54a.5.5 0 1 1 .768.641L8.651 8l2.233 2.68a.5.5 0 0 1-.768.64L8 8.781l-2.116 2.54a.5.5 0 0 1-.768-.641L7.349 8 5.116 5.32a.5.5 0 1 1 .768-.64z"/>
                            </svg>
                           </button>
                        </li>
                    </ul>
                    <h2>Add {this.state.modalShow}</h2>
                    <AddCompany></AddCompany>                    
                </div>
            );
        } else if(this.state.modalShow === "Contract") {
            return(
                <div>                  
                    <ul class="nav justify-content-end">
                        <li class="nav-item">
                        <i class="bi bi-file-excel-fill"></i>
                        <button  type="button" class="close" aria-label="Close" onClick={() => this.handleCancel()}>
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-excel-fill" color="red" viewBox="0 0 16 16">
                                <path d="M12 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zM5.884 4.68L8 7.219l2.116-2.54a.5.5 0 1 1 .768.641L8.651 8l2.233 2.68a.5.5 0 0 1-.768.64L8 8.781l-2.116 2.54a.5.5 0 0 1-.768-.641L7.349 8 5.116 5.32a.5.5 0 1 1 .768-.64z"/>
                            </svg>
                           </button>
                    </li>
                    </ul>
                    <h2>Add {this.state.modalShow}</h2>
                    <AddContract></AddContract>
                </div>
            );
        } else if(this.state.modalShow === "User") {
            return(
                <div>    
                    <ul class="nav justify-content-end">
                        <li class="nav-item">
                        <i class="bi bi-file-excel-fill"></i>
                        <button  type="button" class="close" aria-label="Close" onClick={() => this.handleCancel()}>
                          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-excel-fill" color="red" viewBox="0 0 16 16">
                                <path d="M12 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zM5.884 4.68L8 7.219l2.116-2.54a.5.5 0 1 1 .768.641L8.651 8l2.233 2.68a.5.5 0 0 1-.768.64L8 8.781l-2.116 2.54a.5.5 0 0 1-.768-.641L7.349 8 5.116 5.32a.5.5 0 1 1 .768-.64z"/>
                            </svg>
                           </button>
                    </li>
                    </ul>
                    <h2>Add {this.state.modalShow}</h2>
                    <AddUser></AddUser>
                </div>
            );
        } else if(this.state.modalShow === "Nutzer") {
            return(
                <div>
                    
                    <ul class="nav justify-content-end">
                        <li class="nav-item">
                        <button  type="button" class="close" aria-label="Close" onClick={() => this.handleCancel()}>
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" font-size = "3rem" class="bi bi-file-excel-fill" color="red" viewBox="0 0 16 16">
                                <path d="M12 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zM5.884 4.68L8 7.219l2.116-2.54a.5.5 0 1 1 .768.641L8.651 8l2.233 2.68a.5.5 0 0 1-.768.64L8 8.781l-2.116 2.54a.5.5 0 0 1-.768-.641L7.349 8 5.116 5.32a.5.5 0 1 1 .768-.64z"/>
                            </svg>
                           </button>
                    </li>
                    </ul>
                    <h2>User</h2>
                    <User></User>
                </div>
            );
        }
    }


    render(){
        return(
            
            <div>
                <Modal isOpen={this.state.modalIsOpen}>
                    {this.createModal()}
                </Modal>
            <nav className="navbar navbar-expand-sm navbar-dark bg-primary">
                <h3>{this.props.table}</h3>
              <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent">
                <span className="navbar-toggler-icon"></span>
              </button>
              <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul className="nav nav-pills nav-fill">
                    <li className="nav-item" >
                      <button className="btn btn-light btn-lg" onClick={() => this.handleAdd()}>Add</button>
                    </li>
                  </ul>
                
                <ul className="navbar-nav ml-auto" id="barItemsRight">
                <form className="form-inline" id="barItemsRight" >
                    <input className="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search"></input>
                    <button className="btn btn-outline-light btn-lg my-2 my-sm-0" type="submit">Search</button>
                  </form>
                  <li className="nav-item active" id="barItemsRight">
                    <PersonSquare id="icon" onClick={() => this.handleUser()}></PersonSquare>
                  </li>
                  <li className="nav">
                  <a >    
                  </a>
                  </li>
                  <li className="nav-item">
                  <BoxArrowRight  id="icon" onClick={() => this.handleLogout()}></BoxArrowRight>                 
                  </li>
                </ul>
              </div>
              </nav> 
              
            </div>
        );
    }
}

export default withRouter(BarComponent);