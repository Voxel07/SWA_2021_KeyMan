import React from 'react';
import Customer from './customer.component';
import axios from 'axios'
import Company from '../../components/Company'


class CustomersPage extends React.Component {
    constructor() {
        super();
        this.state ={ companys:[],
        errorMsg:''
    }
    }

    componentWillMount(){
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
    
    
    deleteUser() {
        
    }

    editUser() {
        
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

export default CustomersPage;