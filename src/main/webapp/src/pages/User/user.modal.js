import React from 'react';

class User extends React.Component {
    constructor() {
        super();
        this.state = {  };
    }
    
    render() {
        return (
            <div>
                <h6>
                    Username:
                </h6>
                <input defaultValue="User1" ></input>
                <h6>
                    Email:
                </h6>
                <input defaultValue="user1@custb.com" ></input>
                <h6>
                    Customer:
                </h6>
                <input defaultValue="Customer B" ></input>
                <h6>
                    Admin:
                </h6>
                <input defaultValue="yes" ></input>
            </div>
        );
    }
}

export default User;