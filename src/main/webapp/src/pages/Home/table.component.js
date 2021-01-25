import React from 'react';
import CompanysPage from '../Company/company.page';
import ContractsPage from '../Contracts/contracts.page';
import UsersPage from '../Users/user.page';


class TableComponent extends React.Component {

  constructor(props) {
    super(props)
  }

  // handleCallback=(elmWasupdated)=>{
  //   console.log("elmWasupdated "+elmWasupdated)
  //   this.props.cbToHP(elmWasupdated)
  // }
  

    createTable() {
        if (this.props.table === "Companys") {
            return(<CompanysPage newCompany={this.props.newItem} /*cbToTable={this.handleCallback}*//>)
        } else if (this.props.table === "Contracts") {
            return(<ContractsPage newContract={this.props.newItem}/>)
        } else {
            return(<UsersPage newUser={this.props.newItem}/>)
        }
    }
  
    render(){
      // console.log("Table new Item: ");
      // console.log( this.props.newItem);
      return(
        <table>
          {this.createTable()}
        </table>
      );
    }
  }

  export default TableComponent;