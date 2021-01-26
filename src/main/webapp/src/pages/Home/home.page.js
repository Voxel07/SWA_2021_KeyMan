import React from 'react';
import Bar from './bar.component';
import Table from './table.component';

import './home.page.css';

class HomePage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            table: "Companys",
            Status: ''
        };
        this.onClickCompanys = this.onClickCompanys.bind(this);
        this.onClickContracts = this.onClickContracts.bind(this);
        this.onClickUsers = this.onClickUsers.bind(this);
    }

    onClickCompanys() {
        this.setState({
            table: "Companys"
        });
    }

    onClickContracts() {
        this.setState({
            table: "Contracts"
        });
    }

    onClickUsers() {
        this.setState({
            table: "Users"
        });
    }

    callBackHomepage = (status) => {
        this.setState({ Status: status });
    }

    render() {
        return (
            <div>
                <Bar table={this.state.table} callBackHomepage={this.callBackHomepage} />
                <div id="parent">
                    <div id="menu">
                        <nav className=" navbar-nav ">
                            <div id="menuButton" className="nav-link active btn-lg" href="#">
                                <button className="fontctr btn-outline-dark col-md-12" onClick={this.onClickCompanys}>Company</button>
                            </div>
                            <div id="menuButton" className="nav-link active btn-lg" href="#">
                                <button className="fontctr btn-outline-dark col-md-12" onClick={this.onClickContracts}>Contract</button>

                            </div>
                            <div id="menuButton" className="nav-link active btn-lg" href="#">
                                <button className="fontctr btn-outline-dark col-md-12" onClick={this.onClickUsers}>User</button>
                            </div>
                        </nav>
                    </div>
                    <div id="table">
                        <Table table={this.state.table} newItem={this.state.Status} /*cbToHP={this.callbackFromTable}*/ />
                    </div>
                </div>
            </div>
        );
    }
}

export default HomePage;

