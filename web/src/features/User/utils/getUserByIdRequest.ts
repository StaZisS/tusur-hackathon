import {USER} from "@/lib/constants/api.ts";
import {axiosInstance} from "@/interceptor/interceptor.ts";
import {ACCESS_TOKEN_NAME} from "@/lib/constants/userClaims.ts";

export const getUserByIdRequest = async (id: string): Promise<FullUserDto> => {
    const response = await axiosInstance.get(
        `${USER}/profile/${id}`,
        {
            headers: {
                Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN_NAME)}`,
            },
        },
    );
    return response.data;
}