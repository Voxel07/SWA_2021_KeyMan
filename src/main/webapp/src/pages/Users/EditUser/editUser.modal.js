import React from 'react';

class EditUser extends React.Component {
    constructor(props) {
        super(props);
        this.state = {  };
    }
    
    render() {
        return (
            <form>
                <fieldset>
                    <legend>Edit User Hulk Mogan:</legend>
                    <label>Customer</label>
                    <input type="text" defaultValue="Customer D"></input>
                    <label>First name</label>
                    <input type="text" defaultValue="Hulk"></input>
                    <label>Last name</label>
                    <input type="text" defaultValue="Mogan"></input>
                    <label>E-mail</label>
                    <input type="E-Mail" defaultValue="hm@custd.com"></input>
                    <label>Phone:</label>
                    <input type="number" defaultValue="1-555-3478"></input>
                    <label>Mobile:</label>
                    <input type="text" defaultValue="1-333-8743"></input>
                    <label>
                        <input type="checkbox" value="is Admin"></input>
                    </label>
                </fieldset>
            </form>
        );
    }
}

export default EditUser;