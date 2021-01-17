import React, { Component } from 'react'
import axios from 'axios'
import Contract from './Contract'

class Companylist extends Component {
    constructor(props) {
        super(props)

        this.state = {
            contracts: [],
            errorMsg: ''
        }
    }
    componentDidMount() {
        axios.get('http://localhost:8080/contract')
            .then(response => {
                console.log(response);
                this.setState({ contracts: response.data });
                if( response.data.length == 0)
                {
                    this.setState({ errorMsg: 'Keine Daten erhalten' })
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

export default Companylist