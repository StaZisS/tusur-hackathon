import {Button, Card, CardContent, CardTitle} from "@/components/ui";
import {useState} from "react";
import {EditCommandModal} from "@/features/Command/EditCommandModal.tsx";
import {CustomAlertDialog} from "@/features/AlertDialog/CustomAlertDialog.tsx";
import {deleteCommandRequest} from "@/features/Command/utils/deleteCommandRequest.ts";

interface CommandCardProps {
    id: string,
    name: string,
    description: string,
}

export const CommandCard = ({id, name, description}: CommandCardProps) => {
    const [isDeleteLoading, setIsDeleteLoading] = useState(false);
    const [isModalEditActive, setIsModalEditActive] = useState(false);
    const [isAlertDialogOpen, setIsAlertDialogOpen] = useState(false);

    const onDelete = async () => {
        setIsDeleteLoading(true);
        deleteCommandRequest(id)
            .then(() => {
                window.location.reload();
            }).finally(() => setIsDeleteLoading(false));
    }

    return (
        <>
            <EditCommandModal
                isModalEditActive={isModalEditActive}
                setIsModalEditActive={setIsModalEditActive}
                id={id}
                name={name}
                description={description}
            />
            <CustomAlertDialog
                isActive={isAlertDialogOpen}
                setIsActive={setIsAlertDialogOpen}
                title={'Удалить команду'}
                description={`Вы действительно хотите удалить команду ${name}`}
                onContinue={onDelete}
            />

            <Card className="w-full flex justify-between h-16 items-center p-6 py-10">
                <div className='flex items-center justify-items-center gap-4'>
                    <CardTitle>{name}</CardTitle>
                    <CardContent className='p-0'>{description}</CardContent>
                </div>
                <div className="flex justify-between gap-2">
                    <Button onClick={() => setIsModalEditActive(true)}>Редактировать</Button>
                    <Button variant="destructive" loading={isDeleteLoading}
                            onClick={() => setIsAlertDialogOpen(true)}>Удалить</Button>
                </div>
            </Card>

        </>
    );
}