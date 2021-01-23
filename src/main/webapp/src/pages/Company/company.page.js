import React from 'react';
import axios from 'axios'
import Company from '../../components/Company'


class CompanyPage extends React.Component {
    constructor() {
        super();
        this.state ={ companys:[],
        errorMsg:'',
        data:[]
    }
    }

    componentDidMount(){
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

    handleCallback = (childData) =>{
       
        this.handleRemove(childData);
    }
    handleRemove(id){
       
        const newList = this.state.companys.filter((item)=>item.id !==id);
        this.setState({companys: newList})
      
        // this.setState({})
    }
       
    render() {
        const { companys, errorMsg } = this.state
        return (
        <div> 
        {
            companys.length ? companys.map(company => <Company company={company} parentCallback = {this.handleCallback} />) : null
        }
        {
            errorMsg ? <div key={"error"}>{errorMsg}</div> : null
        } 
        {this.state.data}
        </div>
        )
    }
}

export default CompanyPage;