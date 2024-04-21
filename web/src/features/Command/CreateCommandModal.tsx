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
import {createCommandRequest} from "@/features/Command/utils/createCommandRequest.ts";

interface CreateCommandModalProps {
    isActive: boolean;
    setIsActive: React.Dispatch<React.SetStateAction<boolean>>;
}

export const CreateCommandModal = ({isActive, setIsActive}: CreateCommandModalProps) => {
    const [isLoading, setIsLoading] = useState(false);
    const [name, setName] = useState('');
    const [description, setDescription] = useState('');

    const handleSubmit = async () => {
        setIsLoading(true);
        createCommandRequest(name, description).then(() => {
            setIsLoading(false);
            setIsActive(false);
            setName('');
            setDescription('');
            window.location.reload();
        }).finally(() => setIsLoading(false));
    }

    return (
        <Dialog onOpenChange={setIsActive} open={isActive}>
            <DialogTrigger asChild></DialogTrigger>
            <DialogContent className="sm:max-w-[625px] overflow-y-auto max-h-[90vh]">
                <DialogHeader>
                    <DialogTitle>Создать комманду</DialogTitle>
                </DialogHeader>

                <Input type='text' placeholder='Название' value={name} onChange={(e) => setName(e.target.value)}/>
                <Input type='text' placeholder='Описание' value={description}
                       onChange={(e) => setDescription(e.target.value)}/>

                <DialogFooter>
                    <Button onClick={handleSubmit} loading={isLoading}>Создать</Button>
                </DialogFooter>
            </DialogContent>
        </Dialog>
    );
}