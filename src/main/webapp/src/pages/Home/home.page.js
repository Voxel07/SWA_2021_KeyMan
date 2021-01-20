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
                     <nav class=" navbar-nav ">
                        <div id="menuButton" class="nav-link active btn-lg" href="#">
                        <button className="fontctr" onClick={this.onClickCustomers}>Customer</button>
                        </div>
                        <div id="menuButton" class="nav-link active btn-lg" href="#">
                            <button className="fontctr" onClick={this.onClickContracts}>Contract</button>
                            
                        </div>
                        <div id="menuButton" class="nav-link active btn-lg" href="#">
                              <button className="fontctr" onClick={this.onClickUsers}>User</button>
                        </div>
                        </nav>
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

