import {FormFields} from "@/features/User/EditUser/form.ts";
import {axiosInstance} from "@/interceptor/interceptor.ts";
import {ACCESS_TOKEN_NAME} from "@/lib/constants/userClaims.ts";
import {ADMIN} from "@/lib/constants/api.ts";

export const editUserRequest = async (data: FormFields, id: string): Promise<void> => {
    const formData = new FormData();
    formData.append('user_id', id);
    formData.append('username', data.username);
    formData.append('email', data.email);
    if (data.password) formData.append('password', data.password);
    formData.append('full_name', data.fullName);
    formData.append('birth_date', data.birthDate);
    if (data.affiliate) formData.append('affiliate_id', data.affiliate);
    if (data.command) {
        data.command.forEach((command) => {
            formData.append('command_id', command);
        });
    }
    formData.append('photo', data.photoUrl as Blob);

    const response = await axiosInstance.put(
        `${ADMIN}/user`,
        formData,
        {
            headers: {
                Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN_NAME)}`,
                "Content-Type": "multipart/form-data",
            },
        },
    );
    return response.data;
}