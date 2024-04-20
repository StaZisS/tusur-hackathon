import {axiosInstance} from "@/interceptor/interceptor.ts";
import {ADMIN} from "@/lib/constants/api.ts";
import {ACCESS_TOKEN_NAME} from "@/lib/constants/userClaims.ts";

export const createAffiliateRequest = async (name: string, address: string): Promise<string> => {
    const formData = new FormData();
    formData.append('name', name);
    formData.append('address', address);
    const response = await axiosInstance.post(
        `${ADMIN}/affiliate`,
        formData,
        {
            headers: {
                Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN_NAME)}`,
                'Content-Type': 'multipart/form-data',
            },
        });
    return response.data;
}