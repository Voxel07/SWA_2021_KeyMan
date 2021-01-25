class userService {
    constructor() {
        this.isAdmin = false;
		this.id = '';
    }

    getAdmin(){
        return this.isAdmin;
    }

    setAdmin(){
        this.isAdmin = true;
    }
	
	setId(usrId){
		this.id = usrId;
    }
    resetAdmin(){
        this.isAdmin = false;
    }
	
	getId(){
	return this.id;
	}
	
	

}

export default new userService();