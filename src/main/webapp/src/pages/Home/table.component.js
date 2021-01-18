import React from 'react';
import CustomersPage from '../Customers/customers.page';
import ContractsPage from '../Contracts/contracts.page';
import UsersPage from '../Users/user.page';


class TableComponent extends React.Component {

    createTable() {
        if (this.props.table === "Customers") {
            return(<CustomersPage/>)
        } else if (this.props.table === "Contracts") {
            return(<ContractsPage/>)
        } else {
            return(<UsersPage/>)
        }
    }
  
    render(){
      return(
        <table>
          {this.createTable()}
        </table>
      );
    }
  }

  export default TableComponent;