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
import React, {useState} from "react";
import {createAffiliateRequest} from "@/features/Affiliate/utils/createAffiliateRequest.ts";

interface CreateAffiliateModalProps {
    active: boolean,
    setActive: React.Dispatch<React.SetStateAction<boolean>>,
}

export const CreateAffiliateModal = ({active, setActive}: CreateAffiliateModalProps) => {
    const [isLoading, setIsLoading] = useState(false);
    const [name, setName] = useState('');
    const [address, setAddress] = useState('');

    const handleSubmit = async () => {
        setIsLoading(true);
        createAffiliateRequest(name, address).then(() => {
            setIsLoading(false);
            setActive(false);
            setName('');
            setAddress('');
            window.location.reload();
        }).finally(() => setIsLoading(false));
    }

    return (
        <Dialog onOpenChange={setActive} open={active}>
            <DialogTrigger asChild></DialogTrigger>
            <DialogContent className="sm:max-w-[625px] overflow-y-auto max-h-[90vh]">
                <DialogHeader>
                    <DialogTitle>Создать филиал</DialogTitle>
                </DialogHeader>

                <Input type='text' placeholder='Название' value={name} onChange={(e) => setName(e.target.value)}/>
                <Input type='text' placeholder='Адрес' value={address} onChange={(e) => setAddress(e.target.value)}/>

                <DialogFooter>
                    <Button onClick={handleSubmit} loading={isLoading}>Создать</Button>
                </DialogFooter>
            </DialogContent>
        </Dialog>
    );
}