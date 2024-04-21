import {ADMIN} from "@/lib/constants/api.ts";
import {axiosInstance} from "@/interceptor/interceptor.ts";
import {ACCESS_TOKEN_NAME} from "@/lib/constants/userClaims.ts";

export const deleteAffiliateRequest = async (id: string): Promise<void> => {
    const response = await axiosInstance.delete(
        `${ADMIN}/affiliate?affiliate_id=${id}`,
        {
            headers: {
                Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN_NAME)}`,
            },
        },
    )
    return response.data;
}