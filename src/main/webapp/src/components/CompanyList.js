import React, { Component } from 'react'
import axios from 'axios'
import Company from './Company'

class Companylist extends Component {
    constructor(props) {
        super(props)

        this.state = {
            companys: [],
            errorMsg: ''
        }
    }
    componentDidMount() {
        axios.get('http://localhost:8080/company')
            .then(response => {
                console.log(response);
                this.setState({ companys: response.data });
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
        const { companys, errorMsg } = this.state
        return (
        <div> 
        {
            companys.length ? companys.map(company => <Company company={company} />) : null
        }
        {
            errorMsg ? <div>{errorMsg}</div> : null
        } 
        </div>
        )
    }
}

export default Companylist