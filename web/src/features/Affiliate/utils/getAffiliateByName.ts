import {axiosInstance} from "@/interceptor/interceptor.ts";
import {AFFILIATE} from "@/lib/constants/api.ts";
import {ACCESS_TOKEN_NAME} from "@/lib/constants/userClaims.ts";

export const getAffiliateByName = async (name: string): Promise<AffiliationDto[]> => {
    const response = await axiosInstance.get(
        `${AFFILIATE}?affiliate_name=${name}`,
        {
            headers: {
                Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN_NAME)}`,
            },
        },
    );
    return response.data;
}