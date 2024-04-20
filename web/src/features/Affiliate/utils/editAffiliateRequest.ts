import {axiosInstance} from "@/interceptor/interceptor.ts";
import {ADMIN} from "@/lib/constants/api.ts";
import {ACCESS_TOKEN_NAME} from "@/lib/constants/userClaims.ts";

export const editAffiliateRequest = async (affiliateId: string, name: string, address: string): Promise<void> => {
    const formData = new FormData();
    formData.append('affiliate_id', affiliateId);
    formData.append('name', name);
    formData.append('address', address);
    const response = await axiosInstance.put(
        `${ADMIN}/affiliate`,
        formData,
        {
            headers: {
                Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN_NAME)}`,
                "Content-Type": "multipart/form-data",
            },
        },
    )
    return response.data;
}