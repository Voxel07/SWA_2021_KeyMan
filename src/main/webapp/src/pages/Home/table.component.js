import React from 'react';
import CompanysPage from '../Company/company.page';
import ContractsPage from '../Contracts/contracts.page';
import UsersPage from '../Users/user.page';


class TableComponent extends React.Component {

  createTable() {
    if (this.props.table === "Companys") {
      return (<CompanysPage newCompany={this.props.newItem} />)
    } else if (this.props.table === "Contracts") {
      return (<ContractsPage newContract={this.props.newItem} />)
    } else {
      return (<UsersPage newUser={this.props.newItem} />)
    }
  }

  render() {
    return (
      <table>
        {this.createTable()}
      </table>
    );
  }
}

export default TableComponent;