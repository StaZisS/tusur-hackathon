import {axiosInstance} from "@/interceptor/interceptor.ts";
import {ACCESS_TOKEN_NAME} from "@/lib/constants/userClaims.ts";
import {ADMIN} from "@/lib/constants/api.ts";

export const blockUserRequest = async (userId: string): Promise<void> => {
    const response = await axiosInstance.put(
        `${ADMIN}/block?user_id=${userId}`,
        {},
        {
            headers: {
                Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN_NAME)}`,
            },
        },
    );
    return response.data;
}