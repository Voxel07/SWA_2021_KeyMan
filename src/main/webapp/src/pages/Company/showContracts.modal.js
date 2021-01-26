import React, { Component } from 'react'
import axios from 'axios'
import Contract from '../../components/Contract'

export default class ShowContracts extends Component {
    constructor(props) {
        super(props)

        this.state = {
            contracts: [],
            errorMsg: ''
        }
    }
	componentWillMount() {
		this.fetchContracts();
	}
	fetchContracts() {
        axios.get('http://localhost:8080/contract', {params:{companyId: this.props.company.id}})
            .then(response => {
                console.log(response);
                this.setState({ contracts: response.data });
                if( response.data.length == 0)
                {
                    this.setState({ errorMsg: 'Keine Contract Daten erhalten' })
                }

            })
            .catch(error => {
                console.log(error);
                this.setState({ errorMsg: " "+error})
            })
    }
	handleCallback = (func, contract) => {
		switch (func) {
			case 'DELETE':
				this.handleRemove(contract);
				break;
			case 'UPDATE':
				this.handleUpdate(contract);
				break;
			case 'ADD':
				this.handleADD(contract);
				break;
			default:
				break;
		}
	}

	handleRemove = (contract) => {
		const newList = this.state.contracts.filter((item) => item.id !== contract.id);
		this.setState({ contracts: newList })
	}
	handleUpdate = (contract) => {
		const newList = this.state.contracts.map((item) => {
			if (item.id === contract.id) {
				const updatedItem = {
					startDate: contract.startDate,
					eDate: contract.endDate,
					version: contract.version,
					licenskey: contract.licenskey,
					id: contract.id,
					companyId: contract.companyId,
				};
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