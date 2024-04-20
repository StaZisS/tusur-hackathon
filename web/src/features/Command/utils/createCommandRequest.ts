import {ACCESS_TOKEN_NAME} from "@/lib/constants/userClaims.ts";
import {ADMIN} from "@/lib/constants/api.ts";
import {axiosInstance} from "@/interceptor/interceptor.ts";

export const createCommandRequest = async (name: string, description: string): Promise<void> => {
    const formData = new FormData();
    formData.append('name', name);
    formData.append('description', description);
    const response = await axiosInstance.post(
        `${ADMIN}/command`,
        formData,
        {
            headers: {
                Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN_NAME)}`,
            },
        },
    )
    return response.data;
}