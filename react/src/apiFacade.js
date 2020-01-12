/* eslint-disable no-throw-literal */
const URL = "https://jmhdat3.com/eksamen";
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

    login = async (user, pass) => {
        const options = this.makeOptions("POST", false, { username: user, password: pass });
        // return fetch(URL + "/api/login", options)
        //     .then(handleHttpErrors) 
        //     .then(res => this.setToken(res.token))
        //     .then(res => res)
        const res = await fetch("api/login", options)
        const json = await res.json();
        if (!res.ok) {
            throw { status: res.status, fullError: json }
        }
        this.setToken(json.token)
        sessionStorage.setItem("user", JSON.stringify({ Username: json.username}));
        return json;
    }

    register = async (user, pass) => {
        const options = this.makeOptions("POST", false, { username: user, password: pass });
        const res = await fetch("api/register", options)
        if (!res.ok) {
            return Promise.reject({ status: res.status, fullError: res.json() })
        }
       
        this.setToken(res.token)
        return res.json();
    }

    CheckIfUser(list) {
        return fetch(URL + "/api/Example/user")
            .then(function (response) {
                return response.json();
            }).then(res => { list.unshift(res) })
    }
    CheckIfAdmin(list) {
        return fetch(URL + "/api/Example/admin")
            .then(function (response) {
                return response.json();
            }).then(res => { list.unshift(res) })
    }

    TryGet() {
        return fetch(URL + "/api/products/all").then(res => res.json())

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
    logout = async () => {
        await localStorage.removeItem("jwtToken");
    }

}
const facade = new ApiFacade();

export default facade;
