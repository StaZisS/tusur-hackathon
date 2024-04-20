import {axiosInstance} from "@/interceptor/interceptor.ts";
import {ADMIN} from "@/lib/constants/api.ts";
import {ACCESS_TOKEN_NAME} from "@/lib/constants/userClaims.ts";

export const assignRole = async (userId: string, role: string): Promise<void> => {
    const response = await axiosInstance.post(
        `${ADMIN}/role?user_id=${userId}&role=${role}`,
        {},
        {
            headers: {
                Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN_NAME)}`,
            },
        },
    );
    return response.data;
}