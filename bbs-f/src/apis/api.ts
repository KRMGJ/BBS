/* eslint-disable @typescript-eslint/no-explicit-any */
import axios from "axios";

const BASE_URL = 'http://localhost:8088/bbs';

export const getData = async (endpoint: string) => {
    try {
        const res = await axios.get(`${BASE_URL}/${endpoint}`);
        return res.data.data;
    } catch (error) {
        console.error(error);
        throw error;
    }
}

export const postData = async (endpoint: string, data: any) => {
    try {
        const res = await axios.post(`${BASE_URL}/${endpoint}`, data);
        return res.data.data;
    } catch (error) {
        console.error(error);
        throw error;
    }
}

export const apis = {
    get: <T>(endpoint: string) => getData(endpoint) as Promise<T>,
    post: <T>(endpoint: string, data: any) => postData(endpoint, data) as Promise<T>
}

export const endpoints = {
    login: 'user/login.do',
    logout: 'user/logout.do',
    me: 'user/me.do',
    join: 'user/join.do',
    checkDuplicate: 'user/checkUserId.do',
}