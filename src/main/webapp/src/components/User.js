import React from 'react'
import Modal from 'react-modal'
import axios from 'axios'
import EditUser from '../pages/Users/EditUser/editUser.modal'
import ShowDetails from '../pages/Users/showUserDetails.modal'

// id | email | first_name | is_admin | last_name | password | username | company_Id
class User extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            email: this.props.user.email,
            username: this.props.user.username,
            id: this.props.user.id,
            companyName:'',
            modalIsOpen: false, modalShow: "Edit"
        };

        this.deleteUser = this.deleteUser.bind(this);
        this.handleCancel = this.handleCancel.bind(this);
        this.createModal = this.createModal.bind(this);
        this.handleEdit = this.handleEdit.bind(this);
        this.handleDetails = this.handleDetails.bind(this);
    }
    componentWillMount() {
        this.getCompany();
    }

//geht nicht
    getCompany(){
        console.log(this.state);
        axios.get("http://localhost:8080/company/"+ this.props.user.companyId)
            .then(response => {
               console.log(response);
               this.setState({[this.state.companyName]:response.data.name})
            })
            .catch(error => {
                console.log(error);

            })
    }


    deleteUser() {
        console.log(this.state);
        axios.delete("http://localhost:8080/user", { data: this.state })
            .then(response => {
                console.log(response);
                // console.log(user.id);
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
                        <EditUser user={this.props.user}></EditUser>
                        <button onClick={() => this.handleCancel()}>Cancel</button>
                    </div>
                );

            case "Detail":
                return (
                    <div>
                        <ShowDetails user={this.props.user}></ShowDetails>
                        <button onClick={() => this.handleCancel()}>Cancel</button>
                    </div>
                );

            default:
                break;
        }
    }
    render() {
        const {username, email, id, companyName } = this.state
        return (
            <div key={id} >
                <Modal isOpen={this.state.modalIsOpen}>
                    {this.createModal()}
                </Modal>
                <div className=" form-row ">
                    <div className="form-group col-11 col-sm-2">
                        <input
                            readOnly
                            name="companyName"
                            className="form-control "
                            type="text"
                            value={companyName} 
                            />
                    </div>
                    <div className="form-group col-11 col-sm-2 ">
                        <input
                            readOnly
                            className="form-control"
                            name="usernmae"
                            type="text"
                            value={username} />
                    </div>
                    <div className=" col-11 col-sm-2">
                        <input
                            readOnly
                            className="form-control"
                            name="email"
                            type="text"
                            value={email} />
                    </div>
                    <div className="form-group col-11 col-sm-1">
                        <button className="btn btn-dark" onClick={() => this.handleEdit()}>Verändern</button>
                    </div>

                    <div className="form-group col-11 col-sm-1">
                        <button className="btn btn-danger" onClick={() => this.deleteUser()}>Löschen</button>
                    </div>

                    <div className="form-group col-11 col-sm-1">
                        <button className="btn btn-dark" onClick={() => this.handleDetails()} >Details</button>
                    </div>

                </div>
            </div>
        )
    }

}

export default User
