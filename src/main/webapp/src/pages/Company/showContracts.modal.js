import React, { Component } from 'react'
import axios from 'axios'
import Contract from '../../components/Contract'

export default class ShowContracts extends Component {
    constructor(props) {
        super(props)

        this.state = {
            Contracts: [],
            errorMsg: ''
        }
    }
    componentDidMount() {
        axios.get('http://localhost:8080/contract', { params: { companyId: this.props.company.id } })
            .then(response => {
                this.setState({ Contracts: response.data });
                if (response.data.length === 0) {
                    this.setState({ errorMsg: 'Keine Contract Daten erhalten' })
                }
            })
            .catch(error => {
                this.setState({ errorMsg: " " + error })
            })
    }
    handleCallback = () => {
       //Macht hier halt nix
    }

    render() {
        const { Contracts, errorMsg } = this.state
        return (
            <div>
                {
                    Contracts.length ? Contracts.map(contract => <Contract contract={contract} parentCallback={this.handleCallback} />) : null
                }
                {
                    errorMsg ? <div>{errorMsg}</div> : null
                }
            </div>
        )
    }
}