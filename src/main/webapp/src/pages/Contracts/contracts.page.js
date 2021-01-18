import React from 'react';
import Contract from './contract.component';
import axios from 'axios';

class ContractPage extends React.Component {
    constructor() {
        super();
        this.state = { contracts: [], msg:"" };
    }

    /*componentWillMount(){
        const contract1 = {customer: "CustomerA", contractStart: "Contract Start", contractEnd: "Contract End", version: 1.2};
        const contract2 = {customer: "CustomerB", contractStart: "Contract Start", contractEnd: "Contract End", version: 2.2};

        this.setState(state => {
            const list = state.contracts.push(contract1);
        });
        this.setState(state => {
            const list = state.contracts.push(contract2);
        });
    }*/

    componentDidMount() {
        axios.get('http://localhost:8080/Contract')
            .then(response => {
                console.log(response);
                if( response.data.length === 0)
                {
                    this.setState({ msg: 'Keine Daten erhalten' })
                } else {
                    this.setState({ contracts: response.data });
                }

            })
            .catch(error => {
                // console.log(error);
                this.setState({ msg: " "+error})
            })
    }
    
    
    deleteUser() {
        
    }

    editUser() {
        
    }
    
    render() {
        return (
            <div>
                {this.state.contracts.length ? this.state.contracts.map(contract => (
                    <Contract customer={contract.customer} contractStart={contract.contractStart} contractEnd={contract.contractEnd} version={contract.version}/>
                )) : this.state.msg ? this.state.msg : null}
            </div>
        );
    }
}

export default ContractPage;