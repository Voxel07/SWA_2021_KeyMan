import React from 'react';
import authService from "./auth.service";
import axios from 'axios'
import Modal from 'react-modal';
import AddUser from '../Users/AddUser/addUser.modal';
import User from '../User/user.modal';

import './login.page.css';

export class LoginPage extends React.Component {
	constructor(props) {

		super(props);
		this.state = { username: '', password: '', show: false,  modalIsOpen: false };
		this.handleSignUp = this.handleSignUp.bind(this);
		this.handleCancel = this.handleCancel.bind(this);
	};

	Changehandler = (event) => {
		console.log("änderung")
		this.setState({ [event.target.name]: event.target.value })
	}
	handleCancel() {
        this.setState({
            modalIsOpen: false
        });
    }

	login = event => {
		event.preventDefault();
		console.log(this.state.username);
		axios.post('http://localhost:8080/user/login', this.state)//{ username: this.state.username, password: this.state.password})
			.then(response => {
				console.log(response);
				if (response.data === true) {
					this.setState({
						show: false
					})
					authService.login(() => {
						this.props.history.push("/keyman");
					});
				} else {
					this.setState({
						show: true
					})
				}
			})
	}

	handleSignUp() {
            this.setState({
                modalIsOpen: true
            });
		
	}
		createModal(){	
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
						
					
				}
	render() {
		const username = this.state.username;
		const password = this.state.password;
		return (
			
			<section className="Form my-4 mx-1">
				<Modal isOpen={this.state.modalIsOpen}  ariaHideApp={false}>
                    {this.createModal()}
                </Modal>
				<div className="container">
					<div className="row no-gutters">
						<div className="col-lg-12 üx-5 pt-5">
							<div className="mt-5 text-center">
								  <h1 className="fontlogin font-weight-bold py-3">Log in</h1>
								<h4>Sign into your account</h4>
							 	 <div className="form-row">
                   					  <div className="col-md-7 col-sm-12 center-block">
										<input type="text" name="username" className="form-control my-4 p-4 " value={username} onChange={this.Changehandler}></input>
									</div>
								</div>
								<div className="form-row">
									<div className="col-md-7 col-sm-12 center-block">
										<input type="password" name="password" className="form-control my-4 p-4" value={password} onChange={this.Changehandler}></input>
									</div>
								</div>
								<div className="form-row">
									<div className="col-lg-12 ">
										<button className="btn1 mt-3 mb-5" onClick={this.login}>Login</button>
										<div>
											{this.state.show && <span>Falscher username oder falsches Passwort</span>}
										</div>
									</div>

								</div>
								<a href="#">password?</a>
								<p> Don´t have an account? <a href="#" onClick={this.handleSignUp}>Register here</a></p>
								{/* <button className="btn btn-dark" onClick={this.handleSignUp}>Sign Up</button> */}
							</div>
						</div>
					</div>
				</div>

			</section>
		);
	}
}


export default LoginPage;
