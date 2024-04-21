import {
    AlertDialog,
    AlertDialogAction,
    AlertDialogCancel,
    AlertDialogContent,
    AlertDialogDescription,
    AlertDialogFooter,
    AlertDialogHeader,
    AlertDialogTitle,
} from '@/components/ui/alert-dialog.tsx';
import React from 'react';

interface CustomAlertDialogProps {
    isActive: boolean,
    setIsActive: React.Dispatch<React.SetStateAction<boolean>>,
    title: string,
    description: string,
    onContinue: () => void,
}

export const CustomAlertDialog = ({
                                      isActive,
                                      setIsActive,
                                      title,
                                      description,
                                      onContinue,
                                  }: CustomAlertDialogProps) => {

    return (
        <AlertDialog open={isActive} onOpenChange={setIsActive}>
            <AlertDialogContent>
                <AlertDialogHeader>
                    <AlertDialogTitle>{title}</AlertDialogTitle>
                    <AlertDialogDescription>{description}</AlertDialogDescription>
                </AlertDialogHeader>
                <AlertDialogFooter>
                    <AlertDialogCancel>Отменить</AlertDialogCancel>
                    <AlertDialogAction onClick={onContinue}>Продолжить</AlertDialogAction>
                </AlertDialogFooter>
            </AlertDialogContent>
        </AlertDialog>
    );
};