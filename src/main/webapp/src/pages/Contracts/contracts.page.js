import React from 'react';
import axios from 'axios';
import Contract from '../../components/Contract'


class ContractPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            contracts: [],
            errorMsg: ""
        };
        this.handleRemove = this.handleRemove.bind(this);
        this.handleCallback = this.handleCallback.bind(this);
    }

    componentDidMount() {
        this.fetchContracts();
    }

    fetchContracts() {
        axios.get('http://localhost:8080/contract')
            .then(response => {
                if (response.data.length === 0) {
                    this.setState({ errorMsg: 'Keine Contract Daten erhalten' })
                } else {
                    this.setState({ contracts: response.data,errorMsg:'' });
                }
            })
            .catch(error => {    
                this.setState({ errorMsg: " " + error })
            })
    }

    handleCallback = (func, contract) => {
        console.log("CP hc")
        console.log(contract.id, func)
        switch (func) {
            case 'DELETE':
                this.handleRemove(contract);
                break;
            case 'UPDATE':
                this.handleUpdate(contract);
                break;
            default:
                break;
        }
    }

    handleRemove = (contract) => {
        console.log("handleRemove");
        console.log(contract);
        const newList = this.state.contracts.filter((item) => item.id !== contract.id);
        this.setState({ contracts: newList })
    }

    handleUpdate = (contract) => {
		const newList = this.state.contracts.map((item) => {
			if (item.id === contract.id) {
				const updatedItem = {
					startDate: contract.startDate,
					endDate: contract.endDate,
					version: contract.version,
					licenskey: contract.licenskey,
					id: contract.id,
					companyId: contract.companyId,
				};
				console.log(updatedItem)
				return updatedItem;
			}
			else {
				return item;
			}

		});

		this.setState({ contracts: newList })
	}
    componentDidUpdate() {
        if (this.props.newContract === true) {
            console.log("Props are " + this.props.newContract);

            this.fetchContracts();
        }

    }

    render() {
        const { contracts, errorMsg } = this.state
        return (
            <div>
                {
                    contracts.length ? contracts.map(contract => <Contract contract={contract} parentCallback={this.handleCallback} />) : null
                }
                {
                    errorMsg ? <div>{errorMsg}</div> : null
                }
            </div>
        )
    }
}

export default ContractPage;