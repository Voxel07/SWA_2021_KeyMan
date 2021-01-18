import React from 'react';
import authService from "./auth.service";

import './login.page.scss';

export class LoginPage extends React.Component {
    constructor(props) {

        super(props);
        this.state = {eMail: "", password: "", show: false};
        this.handleChangeEmail = this.handleChangeEmail.bind(this);
        this.handleChangePassword = this.handleChangePassword.bind(this);
        this.login = this.login.bind(this);
    
    }
    
    handleChangeEmail(e) {
        this.setState({
            eMail: e.target.value
        })
    }

    handleChangePassword(e) {
        this.setState({
            password: e.target.value
        })
    }

    login() {
        if(this.state.eMail === 'Marc' && this.state.password === 'test') {
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
    }

    render(){
        const eMail = this.props.eMail;
        const password = this.props.password;
        return(
            <div className="login">
                <h1>KeyMan</h1>
                <div>
                    <fieldset className="login">
                        <legend className="login">
                            Login
                        </legend>
                        E-Mail: 
                        <input type="email" className="login" value={eMail} onChange={this.handleChangeEmail}>
                        </input>
                        Passwort: 
                        <input type="password" className="login" value={password} onChange={this.handleChangePassword}>
                        </input>
                        <button className="login" onClick={() => this.login()}>Login</button>
                        <div>
                            {this.state.show && <span>Falsche Email oder falsches Passwort</span>}
                        </div>
                    </fieldset>
                </div>
            </div>
        );
    }
}

export default LoginPage;