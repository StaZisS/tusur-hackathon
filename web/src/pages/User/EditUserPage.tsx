import {useParams} from "react-router-dom";
import {EditUser} from "@/features/User/EditUser/EditUser.tsx";

export const EditUserPage = () => {
    const {id} = useParams<{ id: string }>();

    return (
        <div className="flex justify-center items-center *:flex-auto min-h-screen px-3">
            <EditUser id={id!}/>
        </div>
    )
}