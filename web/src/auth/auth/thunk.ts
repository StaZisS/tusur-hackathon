import {createAsyncThunk} from '@reduxjs/toolkit';
import {LOGIN} from '@/lib/constants/api.ts';
import {AuthData} from '@/auth/auth/types.ts';
import {clearToken} from '@/auth/auth/slice.ts';
import {axiosInstance} from '@/interceptor/interceptor.ts';

export const loginUser = createAsyncThunk(
    'auth/login',
    async (data: AuthData): Promise<Tokens> => {
        const response = await axiosInstance.post(
            LOGIN,
            new URLSearchParams({
                client_id: 'users-app',
                client_secret: '12345678',
                grant_type: 'password',
                username: data.email,
                password: data.password,
            })
        );
        return {
            accessToken: response.data.access_token,
            refreshToken: response.data.refresh_token,
            expiresIn: response.data.expires_in,
            refreshExpiresIn: response.data.refresh_expires_in,
            tokenType: response.data.token_type,
            notBeforePolicy: response.data.not_before_policy,
            sessionState: response.data.session_state,
            scope: response.data.scope,
        };
    },
);

export const logoutUser = createAsyncThunk(
    'auth/logout',
    async (_, {dispatch}) => {
        dispatch(clearToken());
        localStorage.clear();
    },
);