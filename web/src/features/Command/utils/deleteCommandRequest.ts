import {axiosInstance} from "@/interceptor/interceptor.ts";
import {ADMIN} from "@/lib/constants/api.ts";
import {ACCESS_TOKEN_NAME} from "@/lib/constants/userClaims.ts";

export const deleteCommandRequest = async (commandId: string): Promise<void> => {
    const response = await axiosInstance.delete(
        `${ADMIN}/command?command_id=${commandId}`,
        {
            headers: {
                Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN_NAME)}`,
            },
        },
    );
    return response.data;
}