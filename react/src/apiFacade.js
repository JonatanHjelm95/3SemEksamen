/* eslint-disable no-throw-literal */
const URL = "http://localhost:8080/semproject";
//const URL = "http://localhost:8080/ca3";
function handleHttpErrors(res) {
    if (!res.ok) {
        return Promise.reject({ status: res.status, fullError: res.json() })
    }
    return res.json();
}

class ApiFacade {
    //Insert utility-methods from a latter step (d) here
    makeOptions(method, addToken, body) {
        var opts = {
            method: method,
            headers: {
                "Content-type": "application/json",
                'Accept': 'application/json',
            }
        }
        if (addToken && this.loggedIn()) {
            opts.headers["x-access-token"] = this.getToken();
        }
        if (body) {
            opts.body = JSON.stringify(body);
        }
        return opts;
    }

    login = async(user,pass) => {
        return {username: user, role:"admin"}
    }

    realogin = async (user, pass) => {
        const options = this.makeOptions("POST", true, { username: user, password: pass });
       // return fetch(URL + "/api/login", options)
       //     .then(handleHttpErrors) 
       //     .then(res => this.setToken(res.token))
       //     .then(res => res)
       const res = await fetch(URL + "/api/login", options)
       const json = await res.json();
       if(!res.ok){
           throw {status: res.status, fullError: json}
       }
       this.setToken(res.token)
       return json;
    }
    
    CheckIfUser(list){
        return fetch(URL+"/api/Example/user")
                .then(function(response) {
                        return response.json();
                }).then(res=>{list.unshift(res)})
    }
    CheckIfAdmin(list){
        return fetch(URL+"/api/Example/admin")
                .then(function(response) {
                        return response.json();
                }).then(res=>{list.unshift(res)})
    }

    TryGet (){
        return fetch(URL + "/api/products/all").then(res=> res.json())

    }

    setToken = (token) => {
        localStorage.setItem('jwtToken', token)
    }
    getToken = () => {
        return localStorage.getItem('jwtToken')
    }
    loggedIn = () => {
        const loggedIn = this.getToken() != null;
        return loggedIn;
    }
    logout = () => {
        localStorage.removeItem("jwtToken");
    }

}
const facade = new ApiFacade();

export default facade;
