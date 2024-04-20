import {FormFields} from '@/features/CreateUser/types/form';
import {CREATE_USER} from '@/lib/constants/api';
import {axiosInstance} from '@/interceptor/interceptor.ts';
import {ACCESS_TOKEN_NAME} from "@/lib/constants/userClaims.ts";

export const createUserRequest = async (data: FormFields): Promise<any> => {
    const formData = new FormData();
    formData.append('username', data.username);
    formData.append('email', data.email);
    formData.append('password', data.password);
    formData.append('full_name', data.fullName);
    formData.append('birth_date', data.birthDate);
    formData.append('affiliate_id', data.affiliateId);
    data.commandIds.forEach((commandId) => {
        formData.append('command_id', commandId);
    });
    formData.append('photo', data.photo as Blob);

    const response = await axiosInstance.post(
        CREATE_USER,
        formData,
        {
            headers: {
                Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN_NAME)}`,
                'Content-Type': 'multipart/form-data',
            },
        },
    );

    return response.data;
};
