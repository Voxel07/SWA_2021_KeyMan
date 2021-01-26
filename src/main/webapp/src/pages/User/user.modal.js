import React from 'react'
import axios from 'axios'
import userService from "../Login/user.service";
import Phone from '../Users/EditUser/Phone';
class User extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            id: userService.getId(),
            email: '',
            username: '',
            password: '',
            firstName: '',
            lastName: '',
            isAdmin: '',
            companyName: '',
            errorMsgCompanys: '',
            phones: [],
            errorMsgPhone: '',
            errorMsgCp: '',
            number: '',
            type: ''
        };
    }

    componentDidMount() {
        this.getUser();
        this.getCompany();
        this.getPhones();
    }

    Changehandler = (event) => {
        this.setState({ [event.target.name]: event.target.value })
    }
    handleSubmit = event => {
        event.preventDefault();
        axios.post('http://localhost:8080/user', this.state)
            .then(response => {
                this.props.cbToBar(true)
                this.props.cbToBar(false)
            })
            .catch(error => {
            })
    }

    handleSubmitPhone = event => {
        event.preventDefault();
        axios.put('http://localhost:8080/phone/' + this.state.id, { number: this.state.number, type: this.state.type })
            .then(response => {
                this.getPhones();
                this.setState({ number: '', type: '' });
            })
            .catch(error => {
            })
    }

    getCompany() {
        axios.get('http://localhost:8080/company', { params: { usrId: this.state.id } })
            .then(response => {
                this.setState({ companyName: response.data[0].name });
                if (response.data.length === 0) {
                    this.setState({ errorMsgCp: 'Keine Company Daten erhalten' })
                }
            })
            .catch(error => {
                this.setState({ errorMsgCp: " " + error })
            })
    }

    getUser() {
        axios.get('http://localhost:8080/user', { params: { usrId: this.state.id } })
            .then(response => {
                this.setState(response.data[0]);
                if (response.data.length === 0) {
                    this.setState({ errorMsgCp: 'Keine User Daten erhalten' })
                }
            })
            .catch(error => {
                this.setState({ errorMsgCp: " " + error })
            })
    }

    getPhones() {
        axios.get('http://localhost:8080/phone', { params: { usrId: this.state.id } })
            .then(response => {
                this.setState({ phones: response.data });
                if (response.data.length === 0) {
                    this.setState({ errorMsgPhone: 'Keine Phoes Daten erhalten' })
                }

            })
            .catch(error => {
                this.setState({ errorMsgPhone: " " + error })
            })
    }

    render() {
        const { username, firstName, lastName, password, email } = this.state
        return (
            <div>
                <legend>Edit User: {username}</legend>
                <form onSubmit={this.handleSubmit}>
                    <div className="container"  >
                        <h1 className="title">{username}</h1>
                        <div className=" form-row ">
                            <div className="form-group col-6 col-sm-6 my-2 p-2">
                                <label> Company </label>
                                <input name="companyId" className="form-control1" id="inputGroupSelect01"
                                    value={this.state.companyName} readOnly>
                                </input>
                            </div>
                            <div className=" col-12 col-sm-6 my-2 p-2">
                                <label> Email </label>
                                <input
                                    placeholder="Email"
                                    className="form-control1"
                                    name="email"
                                    type="text"
                                    value={email} onChange={this.Changehandler}
                                />
                            </div>
                        </div>
                        <div className=" form-row ">
                            <div className="form-group col-12 col-sm-6 my-2 p-2 ">
                                <label>First Name</label>
                                <input
                                    placeholder="First Name"
                                    className="form-control1 "
                                    name="firstName"
                                    type="text"
                                    value={firstName} onChange={this.Changehandler} />
                            </div>
                            <div className=" col-12 col-sm-6 my-2 p-2">
                                <label> Last Name </label>
                                <input
                                    placeholder="LastName"
                                    className="form-control1"
                                    name="lastName"
                                    type="text"
                                    value={lastName} onChange={this.Changehandler} />
                            </div>
                        </div>
                        <div className=" form-row ">
                            <div className=" col-12 col-sm-6 my-2 p-2">
                                <label> Username </label>
                                <input
                                    placeholder="Username"
                                    className="form-control1"
                                    name="username"
                                    type="text"
                                    value={username} onChange={this.Changehandler}
                                />
                            </div>
                            <div className=" col-12 col-sm-6 my-2 p-2">
                                <label> Password </label>
                                <input
                                    placeholder="Password"
                                    className="form-control1"
                                    name="password"
                                    type="password"
                                    value={password} onChange={this.Changehandler}
                                />
                            </div>
                        </div>
                    </div>
                    <div className="mt-4 text-center">
                        <button type="submit" class="btn btn-primary btn-lg">Update User</button>
                    </div>
                </form>

                <form onSubmit={this.handleSubmitPhone} key="Phone">
                    <div >
                        <legend>Phone's</legend>
                        <div className="container"  >
                            <h1 className="title">Phone</h1>
                            <div>
                                {
                                    this.state.phones.length ? this.state.phones.map(phone => <Phone phone={phone} />) : null
                                }
                                {
                                    this.state.errorMsgPhone ? <div>{this.state.errorMsgPhone}</div> : null
                                }
                            </div>
                            <div className=" form-row ">
                                <div className=" col-12 col-sm-2 my-1 p-1">
                                    <label>Number</label>
                                    <input type="number" name="number" className="form-control1 " value={this.state.number} onChange={this.Changehandler}></input>
                                </div>
                                <div className=" col-12 col-sm-2 my-1 p-1">
                                    <label>Type</label>
                                    <input type="text" name="type" className="form-control1 " value={this.state.type} onChange={this.Changehandler}></input>
                                </div>

                                <div class="col-12 col-sm-2 my-3 p-3">
                                    <button type="submit" className=" btn btn-secondary btn-lg" value="addPhone">addPhone</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        );
    }
}

export default User;