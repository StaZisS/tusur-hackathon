import React, {useState} from "react";
import {editCommandRequest} from "@/features/Command/utils/editCommandRequest.ts";
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

interface EditCommandModalProps {
    isModalEditActive: boolean;
    setIsModalEditActive: React.Dispatch<React.SetStateAction<boolean>>;
    id: string;
    name: string;
    description: string;
}

export const EditCommandModal = ({
                                     isModalEditActive,
                                     setIsModalEditActive,
                                     id,
                                     name,
                                     description
                                 }: EditCommandModalProps) => {
    const [isLoading, setIsLoading] = useState(false);
    const [newName, setNewName] = useState(name);
    const [newDescription, setNewDescription] = useState(description);

    const handleSubmit = async () => {
        setIsLoading(true);
        editCommandRequest(id, newName, newDescription).then(() => {
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
                    <DialogTitle>Изменить команду {name}</DialogTitle>
                </DialogHeader>

                <Input type='text' placeholder='Название' value={newName} onChange={(e) => setNewName(e.target.value)}/>
                <Input type='text' placeholder='Описание' value={newDescription}
                       onChange={(e) => setNewDescription(e.target.value)}/>

                <DialogFooter>
                    <Button onClick={handleSubmit} loading={isLoading}>Изменить</Button>
                </DialogFooter>
            </DialogContent>
        </Dialog>
    );
}