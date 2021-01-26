import React from 'react';
import axios from 'axios'
import Company from '../../components/Company'


class CompanyPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            companys: [],
            errorMsg: ''
        }
        this.handleRemove = this.handleRemove.bind(this);
        this.handleCallback = this.handleCallback.bind(this);
    }

    componentDidMount() {
        this.fetchCompanys();
    }

    fetchCompanys() {
        axios.get('http://localhost:8080/company')
            .then(response => {
                if (response.data.length === 0) {
                    this.setState({ errorMsg: 'Keine Daten erhalten' })
                }
                else {
                    this.setState({ companys: response.data, errorMsg: '' });
                }
            })
            .catch(error => {
                this.setState({ errorMsg: " " + error })
            })
    }

    handleCallback = (func, company) => {
        switch (func) {
            case 'DELETE':
                this.handleRemove(company);
                break;
            case 'UPDATE':
                this.handleUpdate(company);
                break;
            default:
                break;
        }
    }

    handleRemove = (company) => {
        const newList = this.state.companys.filter((item) => item.id !== company.id);
        this.setState({ companys: newList })
    }

    handleUpdate = (company) => {
        const newList = this.state.companys.map((item) => {
            if (item.id === company.id) {
                const updatedItem = {
                    country: company.country,
                    department: company.department,
                    id: company.id,
                    name: company.name,
                    postalcode: company.postalcode,
                    street: company.street,
                    state: company.state
                };
                return updatedItem;
            }
            else {
                return item;
            }

        });
        this.setState({ companys: newList })
    }

    componentDidUpdate() {
        if (this.props.newCompany === true) {
            this.fetchCompanys();
        }
    }

    render() {
        const { companys, errorMsg } = this.state
        return (
            <div>
                {
                    companys.length ? companys.map(company => <Company company={company} parentCallback={this.handleCallback} />) : null
                }
                {
                    errorMsg ? <div key={"error"}>{errorMsg}</div> : null
                }
            </div>
        )
    }
}

export default CompanyPage;