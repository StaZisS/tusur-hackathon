import {FormFields} from '@/features/Login/types/form';
import {LOGIN} from '@/lib/constants/api';
import {axiosInstance} from '@/interceptor/interceptor.ts';

export const loginRequest = async (data: FormFields): Promise<string> => {
    const response = await axiosInstance.post(
        LOGIN,
        data
    );
    return response.data.token;
};
