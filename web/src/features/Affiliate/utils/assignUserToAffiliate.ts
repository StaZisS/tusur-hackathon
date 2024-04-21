import {axiosInstance} from "@/interceptor/interceptor.ts";
import {ADMIN} from "@/lib/constants/api.ts";
import {ACCESS_TOKEN_NAME} from "@/lib/constants/userClaims.ts";

export const assignUserToAffiliate = async (userId: string, affiliateId: string): Promise<void> => {
    const response = await axiosInstance.put(
        `${ADMIN}/affiliate/${affiliateId}/assign?user_id=${userId}`,
        {},
        {
            headers: {
                Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN_NAME)}`,
            },
        },
    )
    return response.data;
}