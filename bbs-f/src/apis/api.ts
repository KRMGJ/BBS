/* eslint-disable @typescript-eslint/no-explicit-any */
import axios from "axios";

const BASE_URL = 'http://localhost:8088/bbs';

export const searchParam = {
    searchCondition: '0',
    searchKeyword: '',
    pageIndex: 1,
    pageUnit: 10,
    pageSize: 10
}

const toQueryString = (params: typeof searchParam) => {
    const query = new URLSearchParams();
    Object.entries(params).forEach(([k, v]) => {
        if (v === undefined || v === null || v === '' || v === 'undefined' || v === 'null') return;

        if (Array.isArray(v)) {
            v.forEach((item) => {
                if (item !== undefined && item !== null && item !== '' && item !== 'undefined' && item !== 'null') query.append(k, String(item));
            });
        } else {
            query.append(k, String(v));
        }
    });
    return query.toString();
};

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
    nttList: (param: typeof searchParam) => `notice/list.do?${toQueryString(param)}`,
    deleteNttList: (nttIdList: string[]) => `notice/deleteList.do?nttIdList=${nttIdList.join(',')}`,
    nttDetail: (nttId: string) => `notice/selectNoticeDetail.do?nttId=${nttId}`,
    insertNtt: 'notice/insert.do',
    updateNtt: 'notice/update.do',
    nttLike: 'notice/like.do',
    nttReply: 'notice/reply.do',
    comments: (nttId: string) => `cmt/list.do?nttId=${nttId}`,
    commentLike: 'cmt/like.do',
    insertComment: 'cmt/insert.do',
    updateComment: 'cmt/update.do',
    deleteComment: 'cmt/delete.do'
}