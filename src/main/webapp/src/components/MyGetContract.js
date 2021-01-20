import React, { Component } from 'react'
import axios from 'axios'
class MyGetContract extends Component {
    constructor(props) {
        super(props)
    
        this.state = {
             posts: [],
             errorMsg: ''
        }
    }
    componentDidMount(){
        axios.get('http://localhost:8080/contract')
        .then(response => {
            console.log(response)
            this.setState({posts: response.data})
        })
        .catch(error => {
            console.log(error)
            this.setState({errorMsg: 'Kein Contract Daten erhalten'})
        })
    }
    
    render() {
        const {posts,errorMsg} = this.state
        return (
            <div>
                List of Posts
                {
                posts.length ? 
                posts.map(post => <div key = {post.id}>{post.name}</div>):
                null
                }
                {
                    errorMsg ? <div>{errorMsg}</div>:null
                }
            </div>
          
        )
    }
}

export default MyGetContract
