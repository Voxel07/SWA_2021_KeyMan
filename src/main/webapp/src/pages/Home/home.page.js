import React from 'react';
import Bar from './bar.component';
import Table from './table.component';

import './home.page.scss';

class HomePage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {table: "Customers"};
        this.onClickCustomers = this.onClickCustomers.bind(this);
        this.onClickContracts = this.onClickContracts.bind(this);
        this.onClickUsers = this.onClickUsers.bind(this);
    }

    onClickCustomers(){
        this.setState({
            table: "Customers"
        });
    }
    onClickContracts(){
        this.setState({
            table: "Contracts"
        });
    }
    onClickUsers(){
        this.setState({
            table: "Users"
        });
    }

    render(){
        return(
            <div>
                <div>
                     <Bar table={this.state.table}/>
                </div>
                <div id="parent">
                    <div id="menu">
                        <div id="menuButton">
                            <button onClick={this.onClickCustomers}>Customers</button>
                        </div>
                        <div id="menuButton">
                            <button onClick={this.onClickContracts}>Contracts</button>
                        </div>
                        <div id="menuButton">
                            <button onClick={this.onClickUsers}>Users</button>
                        </div>
                    </div>
                    <div id="table">
                        <Table table={this.state.table}/>
                    </div>
                </div>
            </div>
        );
    }
}

export default HomePage;