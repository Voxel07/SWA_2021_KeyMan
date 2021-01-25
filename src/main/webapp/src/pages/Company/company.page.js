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
                else{
                    this.setState({ companys: response.data,errorMsg:'' });
                }
            })
            .catch(error => {
                this.setState({ errorMsg: " " + error })
            })
    }

    handleCallback = (func, company) => {
        console.log("CP hc")
        console.log(company.id,func)
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
        console.log("handleRemove");
        console.log(company);
        const newList = this.state.companys.filter((item) => item.id !== company.id);
        this.setState({companys: newList})
    }
    
    handleUpdate = (company) => {
        // console.log("handleUpdate");
        // console.log(company);
        const newList = this.state.companys.map((item) => {
            if (item.id === company.id) {
                // console.log("changedItem");
                const updatedItem = {
                    country: company.country,
                    department: company.department,
                    id: company.id,
                    name: company.name,
                    postalcode: company.postalcode,
                    street: company.street,
                    state: company.state
                };
                // console.log("was steth da drin? ")
                console.log(updatedItem)
                return updatedItem;
            }
            else {
                // console.log("sameItem");
                return item;
            }

        });
        // console.log("New List: ");
        // console.log(newList);

        this.setState({ companys: newList })
    }
    componentDidUpdate(prevProps) {
        // console.log("componentDidUpdate");
        // console.log("prev Props: ");
        // console.log(prevProps);
        // console.log("this.props " + this.props.newCompany);
        if (this.props.newCompany === true) {
            console.log("Props are " + this.props.newCompany);

            this.fetchCompanys();
        }
        // this.props.cbToTable(true);
        // this.setState({newCPstate: this.props.newCompany})

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