import React, { Component } from 'react'

class Home extends Component {
    constructor(props) {
        super(props);
    
        this.state = {
                 LoggednIn: false 
             };
    }
    
    
    checklogin(){
        if(sessionStorage.getItem('sessionKey') == "Ausername"){
            this.setState(this.state.LoggednIn = true);
        }
      
    }

    render() {
        this.checklogin();
        if(this.state.LoggednIn == false){
            return(<p>nein</p>);
        }
        else
        {
            return(<p>ja</p>);
        }
       
    }
}
export default Home;


