import React from 'react';
import CompanysPage from '../Company/company.page';
import ContractsPage from '../Contracts/contracts.page';
import UsersPage from '../Users/user.page';


class TableComponent extends React.Component {

    createTable() {
        if (this.props.table === "Companys") {
            return(<CompanysPage/>)
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