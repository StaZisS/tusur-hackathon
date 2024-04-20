import React, {useState} from "react";
import {
    Button,
    Dialog,
    DialogContent,
    DialogFooter,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
    Input
} from "@/components/ui";
import {editAffiliateRequest} from "@/features/Affiliate/utils/editAffiliateRequest.ts";

interface EditAffiliateModalProps {
    id: string;
    name: string;
    address: string;
    isModalEditActive: boolean;
    setIsModalEditActive: React.Dispatch<React.SetStateAction<boolean>>;
}

export const EditAffiliateModal = ({
                                       id,
                                       name,
                                       address,
                                       isModalEditActive,
                                       setIsModalEditActive
                                   }: EditAffiliateModalProps) => {
    const [isLoading, setIsLoading] = useState(false);
    const [newName, setNewName] = useState(name);
    const [newAddress, setNewAddress] = useState(address);

    const handleSubmit = async () => {
        editAffiliateRequest(id, newName, newAddress).then(() => {
            setIsLoading(false);
            setIsModalEditActive(false);
            window.location.reload();
        }).finally(() => setIsLoading(false));
    }

    return (
        <Dialog onOpenChange={setIsModalEditActive} open={isModalEditActive}>
            <DialogTrigger asChild></DialogTrigger>
            <DialogContent className="sm:max-w-[625px] overflow-y-auto max-h-[90vh]">
                <DialogHeader>
                    <DialogTitle>Изменить филиал {name}</DialogTitle>
                </DialogHeader>

                <Input type='text' placeholder='Название' value={newName} onChange={(e) => setNewName(e.target.value)}/>
                <Input type='text' placeholder='Адрес' value={newAddress} onChange={(e) => setNewAddress(e.target.value)}/>

                <DialogFooter>
                    <Button onClick={handleSubmit} loading={isLoading}>Изменить</Button>
                </DialogFooter>
            </DialogContent>
        </Dialog>
    );
}