import {Avatar, AvatarFallback, AvatarImage} from "@/components/ui/avatar.tsx";
import {Button, Card} from "@/components/ui";
import {Link} from "react-router-dom";
import {CustomAlertDialog} from "@/features/AlertDialog/CustomAlertDialog.tsx";
import {useState} from "react";
import {deleteUser} from "@/features/User/utils/deleteUser.ts";

interface UserCartProps {
    id: string;
    username: string;
    email: string;
    fullName: string;
    birthDate: string;
    affiliateId: string;
    affiliateName: string;
    photoUrl: string;
}

export const UserCart = ({
                             id,
                             username,
                             email,
                             fullName,
                             birthDate,
                             affiliateName,
                             photoUrl
                         }: UserCartProps) => {
    const [isDeleteLoading, setIsDeleteLoading] = useState(false);
    const [isAlertDialogOpen, setIsAlertDialogOpen] = useState(false);

    const onDelete = async () => {
        setIsDeleteLoading(true);
        deleteUser(id)
            .then(() => {
                window.location.reload();
            }).finally(() => setIsDeleteLoading(false));
    }

    return (
        <>
            <CustomAlertDialog
                isActive={isAlertDialogOpen}
                setIsActive={setIsAlertDialogOpen}
                title={'Удалить филиал'}
                description={`Вы действительно хотите удалить пользователя ${fullName}`}
                onContinue={onDelete}
            />
            <Card className="w-full flex flex-col px-4 py-4 space-y-2">
                <div className='flex gap-2 items-center'>
                    <div className='flex flex-auto space-x-4 items-center'>
                        <div>
                            <Avatar className='h-20 w-20'>
                                <AvatarImage src={photoUrl} alt={fullName}/>
                                <AvatarFallback>{fullName[0]}</AvatarFallback>
                            </Avatar>
                        </div>
                        <div>
                            <div className='font-bold text-xl'>
                                <Link to={`/users/${id}`}>
                                    <>{fullName}</>
                                </Link>
                            </div>
                            <div className='text-gray-500'>{username}</div>
                        </div>
                        <div>
                            <div className='text-gray-500'>{email}</div>
                            <div className='text-gray-500'>{affiliateName}</div>
                        </div>
                        <div>
                            <div className='text-gray-500 font-medium'>{birthDate}</div>
                        </div>

                    </div>

                    <div>
                        <Button variant="destructive" loading={isDeleteLoading}
                                onClick={() => setIsAlertDialogOpen(true)}>Удалить</Button>
                    </div>
                </div>
            </Card>
        </>
    )
}