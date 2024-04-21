import {axiosInstance} from "@/interceptor/interceptor.ts";
import {USER} from "@/lib/constants/api.ts";
import {ACCESS_TOKEN_NAME} from "@/lib/constants/userClaims.ts";

export const getUsersByName = async (name: string) => {
    const response = await axiosInstance.get(
        `${USER}?user_full_name=${name}`,
        {
            headers: {
                Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN_NAME)}`
            }
        });
    return response.data;
}