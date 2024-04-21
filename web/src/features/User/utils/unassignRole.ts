import {ADMIN} from "@/lib/constants/api.ts";
import {axiosInstance} from "@/interceptor/interceptor.ts";
import {ACCESS_TOKEN_NAME} from "@/lib/constants/userClaims.ts";

export const unassignRole = async (userId: string, roleId: string): Promise<void> => {
    const response = await axiosInstance.delete(
        `${ADMIN}/role?role${roleId}?user_id=${userId}`,
        {
            headers: {
                Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN_NAME)}`,
            },
        },
    );
    return response.data;
}