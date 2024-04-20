import {axiosInstance} from "@/interceptor/interceptor.ts";
import {ACCESS_TOKEN_NAME} from "@/lib/constants/userClaims.ts";
import {ADMIN} from "@/lib/constants/api.ts";

export const editCommandRequest = async (commandId: string, name: string, description: string): Promise<void> => {
    const formData = new FormData();
    formData.append("command_id", commandId);
    formData.append("name", name);
    formData.append("description", description);
    const response = await axiosInstance.put(
        `${ADMIN}/command`,
        formData,
        {
            headers: {
                Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN_NAME)}`,
                "Content-Type": "multipart/form-data",
            },
        },
    );
    return response.data;
}