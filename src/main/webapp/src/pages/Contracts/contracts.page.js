import React from 'react';
import axios from 'axios';
import Contract from '../../components/Contract'


class ContractPage extends React.Component {
    constructor() {
        super();
        this.state = { contracts: [], errorMsg:"" };
    }

    componentWillMount() {
        axios.get('http://localhost:8080/contract')
            .then(response => {
                console.log(response);
                if( response.data.length === 0)
                {
                    this.setState({ errorMsg: 'Keine Contract Daten erhalten' })
                } else {
                    this.setState({ contracts: response.data });
                }

            })
            .catch(error => {
                // console.log(error);
                this.setState({ errorMsg: " "+error})
            })
    }
    
    render() {
        const { contracts, errorMsg } = this.state
        return (
        <div> 
        {
            contracts.length ? contracts.map(contract => <Contract contract={contract} />) : null
        }
        {
            errorMsg ? <div>{errorMsg}</div> : null
        } 
        </div>
        )
    }
}

export default ContractPage;