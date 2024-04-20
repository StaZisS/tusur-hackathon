import axios from 'axios';
import {clearToken} from '@/auth/auth/slice.ts';
import {store} from '@/store/store.tsx';

export const axiosInstance = axios.create();
const {dispatch} = store;

axiosInstance.interceptors.response.use(
    response => {
        return response;
    },
    error => {
        if (error.response.status === 401) {
            dispatch(clearToken());
            localStorage.clear();
            window.location.href = "/login";
        }
    }
);