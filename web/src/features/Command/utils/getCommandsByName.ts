import {axiosInstance} from "@/interceptor/interceptor.ts";
import {COMMAND} from "@/lib/constants/api.ts";
import {ACCESS_TOKEN_NAME} from "@/lib/constants/userClaims.ts";

export const getCommandsByName = async (name: string): Promise<CommandDto[]> => {
    const response = await axiosInstance.get(
        `${COMMAND}?command_name=${name}`,
        {
            headers: {
                Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN_NAME)}`,
            },
        },
    );
    return response.data;
}