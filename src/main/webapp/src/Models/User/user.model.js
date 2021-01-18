export class User{
    constructor(id, active, admin, email, firstname, lastname, loginname, password, company) {
        this.id = id
        this.active = active
        this.admin = admin
        this.email = email
        this.firstname = firstname 
        this.lastname = lastname
        this.loginname = loginname
        this.password = password
        this.company = company

        this.getCompany = this.getCompany.bind(this);
    }

    getCompany() {
        return this.company;
    }
}
