import React from 'react';
import axios from 'axios'
import Company from '../../components/Company'


class CompanyPage extends React.Component {
    constructor(props) {
        super(props);
        this.state ={ companys:[],
        newCP: this.props.newCompany,
        errorMsg:'',
        CallbackFunc:''
    }
    }

    componentDidMount(){
        console.log("componentDidMount")
        axios.get('http://localhost:8080/company')
            .then(response => {
                console.log(response);
                this.setState({ companys: response.data });
                if( response.data.length === 0)
                {
                    this.setState({ errorMsg: 'Keine Daten erhalten' })
                }

            })
            .catch(error => {
                // console.log(error);
                this.setState({ errorMsg: " "+error})
            })
    }
    handleCallback=(func,company)=>{
        // console.log(company,func)
        switch (func) {
            case 'DELETE':
                this.handleRemove(company);
                break;
            case 'UPDATE':
                this.handleUpdate(company);
                break;
            //geht hier nicht aaaa
            case 'ADD':
                this.handleADD(company);
                break;
            default:
                break;
        }
    }

    handleRemove= (company) =>{
        console.log("handleRemove");
        const newList = this.state.companys.filter((item)=>item.id !==company.id);
        this.setState({companys: newList}) 
    }
    handleUpdate =(company) =>{
        console.log("handleUpdate");
        console.log(company);
        const newList = this.state.companys.map((item)=>{
        if (item.id === company.id) {
            console.log("changedItem");
            const updatedItem = { 
                country: company.country  , 
                department: company.department   ,
                id: company.id,
                name: company.name   ,
                postalcode: company.postalcode  , 
                street: company.street ,  
                state: company.state   
            };
            console.log("was steth da drin? ")
            console.log(updatedItem)
        return updatedItem;
        }
        else{
            console.log("sameItem");
            return item;
        }
       
        });
        console.log("New List: ");
        console.log(newList);

        this.setState({companys: newList}) 
    }
    pro
    componentDidUpdate(){
        console.log("componentDidUpdate");
        // this.fuuu()
    }
    componentWillUnmount(){
        console.log("componentWillUnmount");

    }
    fuuu=(ev)=>{
        ev.preventDefault();

        console.log("fuu");
        const newList = this.state.companys.concat(this.props.newCompany);
        this.setState({companys: newList})
    }

       
    render() {
        console.log("CP Page newCompany: ")
        const { companys, errorMsg } = this.state
        return (
        <div> 
        {
            companys.length ? companys.map(company => <Company company={company} parentCallback = {this.handleCallback} />) : null
        }
        {
            errorMsg ? <div key={"error"}>{errorMsg}</div> : null
        } 
         <div className="mt-4 text-center">
        <button class="btn btn-primary btn-xlg" onClick={this.fuuu}></button>
        </div>
        </div>
        )
    }
}

export default CompanyPage;