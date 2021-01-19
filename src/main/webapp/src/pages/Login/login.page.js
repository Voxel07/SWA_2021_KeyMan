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
            <section class="Form my-4 mx-1">
            <div class="container">
              <div class="row no-gutters"> 
                <div class="col-lg-12 üx-5 pt-5">
                 <div class="mt-5 text-center">
                   <h1 class="font-weight-bold py-3">Log in</h1>
                   <h4>Sign into your account</h4>
                  <form action="/user/login" method="put">
                    <div class="form-row">
                      <div class="col-lg-12">
                      <input type="email" className="form-control my-4 p-4 " value={eMail} onChange={this.handleChangeEmail}></input>
                      </div>
                     </div>
                    <div class="form-row">
                      <div class="col-lg-12">
                      <input type="password" className="form-control my-4 p-4" value={password} onChange={this.handleChangePassword}></input>
                       </div>
                      </div>
                     <div class="form-row">
                       <div class="col-lg-12 ">
                         <button className="btn1 mt-3 mb-5" onClick={() => this.login()}>Login</button>
                         <div>
                             {this.state.show && <span>Falsche Email oder falsches Passwort</span>}
                         </div>
                      </div>

                  </div>
                  <a href="#">password?</a>
                  <p> Don´t have an account? <a href="#">Register here</a></p>
              </form>
              </div>
              </div>  
              </div>  
              </div>  
               
            </section>    
        );
    }
}

export default LoginPage;


