import {Button, Card, CardContent, CardTitle} from "@/components/ui";
import {useState} from "react";
import {EditAffiliateModal} from "@/features/Affiliate/EditAffiliateModal.tsx";
import {deleteAffiliateRequest} from "@/features/Affiliate/utils/deleteAffiliateRequest.ts";
import {CustomAlertDialog} from "@/features/AlertDialog/CustomAlertDialog.tsx";

interface AffiliateCardProps {
    id: string;
    name: string;
    address: string;
}

export const AffiliateCard = ({id, name, address}: AffiliateCardProps) => {
    const [isModalEditActive, setIsModalEditActive] = useState(false);
    const [isDeleteLoading, setIsDeleteLoading] = useState(false);
    const [isAlertDialogOpen, setIsAlertDialogOpen] = useState(false);

    const onDelete = async () => {
        setIsDeleteLoading(true);
        deleteAffiliateRequest(id)
            .then(() => {
                window.location.reload();
            }).finally(() => setIsDeleteLoading(false));
    }

    return (
        <>
            <EditAffiliateModal
                isModalEditActive={isModalEditActive}
                setIsModalEditActive={setIsModalEditActive}
                id={id}
                name={name}
                address={address}
            />
            <CustomAlertDialog
                isActive={isAlertDialogOpen}
                setIsActive={setIsAlertDialogOpen}
                title={'Удалить филиал'}
                description={`Вы действительно хотите удалить филиал ${name}`}
                onContinue={onDelete}
            />

            <Card className="w-full flex justify-between h-16 items-center px-4">
                <div>
                    <CardTitle>{name}</CardTitle>
                    <CardContent>{address}</CardContent>
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