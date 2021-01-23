import React from 'react';
import authService from "./auth.service";
import axios from 'axios'

import './login.page.scss';

export class LoginPage extends React.Component {
	constructor(props) {

		super(props);
		this.state = { username: '', password: '', show: false };
	}

	Changehandler = (event) => {
		console.log("änderung")
		this.setState({ [event.target.name]: event.target.value })
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

	render() {
		const username = this.state.username;
		const password = this.state.password;
		return (
			<section className="Form my-4 mx-1">
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
								<p> Don´t have an account? <a href="#">Register here</a></p>
							</div>
						</div>
					</div>
				</div>

			</section>
		);
	}
}

export default LoginPage;
