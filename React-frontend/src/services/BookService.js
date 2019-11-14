import axios from 'axios';
import config from '../config/config';


export const bookService = {
    get,
    post,
    put,
    deleteDetail
};

function get(apiEndpoint, params) {
    return axios.get(config.baseUrl + apiEndpoint,
        {
            params: params
        }).then((response) => {
        return response;
    }).catch((err) => {
        console.log("Error in response");
        console.log(err);
    })
}

function post(apiEndpoint, payload) {
    return axios.post(config.baseUrl + apiEndpoint, payload).then((response) => {
        return response;
    }).catch((err) => {
        console.log(err);
    })
}

function put(apiEndpoint, payload) {
    return axios.put(config.baseUrl + apiEndpoint, payload).then((response) => {
        return response;
    }).catch((err) => {
        console.log(err);
    })
}

function deleteDetail(apiEndpoint) {
    return axios.delete(config.baseUrl + apiEndpoint).then((response) => {
        return response;
    }).catch((err) => {
        console.log(err);
    })
}
