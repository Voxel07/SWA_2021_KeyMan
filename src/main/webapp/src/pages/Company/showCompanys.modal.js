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
        axios.get('http://localhost:8080/contract', {params:{company_id: this.props.company.id}})
            .then(response => {
                console.log(response);
                this.setState({ Contracts: response.data });
                if( response.data.length === 0)
                {
                    this.setState({ errorMsg: 'Keine Contract Daten erhalten' })
                }

            })
            .catch(error => {
                // console.log(error);
                this.setState({ errorMsg: " "+error})
            })
    }

    render() {
        const { Contracts, errorMsg } = this.state
        return (
        <div> 
        {
            Contracts.length ? Contracts.map(contract => <Contract contract={contract} />) : null
        }
        {
            errorMsg ? <div>{errorMsg}</div> : null
        } 
        </div>
        )
    }
}