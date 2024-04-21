import {useEffect, useState} from "react";
import {SubmitHandler, useForm} from "react-hook-form";
import {FixedValues, FormFields} from './form';
import {zodResolver} from '@hookform/resolvers/zod';
import {validationSchema} from './validation';
import {editUserRequest} from "@/features/User/utils/editUserRequest.ts";
import {toast} from "sonner";
import {getUserByIdRequest} from "@/features/User/utils/getUserByIdRequest.ts";

export const useEditUserForm = (id: string) => {
    const [loading, setLoading] = useState(false);
    const [blockedValues, setBlockedValues] = useState<FixedValues>();
    const form = useForm<FormFields>({
        resolver: zodResolver(validationSchema),
    });

    const onSubmit: SubmitHandler<FormFields> = async (data) => {
        setLoading(true);
        await editUserRequest(data, id)
            .then(() => toast('Профиль успешно обновлён'))
            .catch(reason => toast(String(reason)))
            .finally(() => setLoading(false));
    };

    const downloadFile = async (url: string): Promise<File> => {
        const response = await fetch(url);
        const data = await response.blob();
        const contentType = response.headers.get('content-type') || 'image/png';
        const fileName = extractFileNameFromUrl(url);
        const file = new File([data], fileName, { type: contentType });

        return file;
    }
    const extractFileNameFromUrl = (url: string): string => {
        const urlParts = url.split('/');
        const lastPart = urlParts[urlParts.length - 1];
        const fileName = lastPart.split('?')[0];
        return fileName;
    }

    const setUserProfile = async () => {
        await getUserByIdRequest(id)
            .then(async (response) => {
                const {username, email, fullName, birthDate, photoUrl, command, affiliate, ...fixed} = response;
                form.setValue('username', username);
                form.setValue('email', email);
                form.setValue('fullName', fullName);
                form.setValue('birthDate', birthDate?.substring(0, 10) || '');
                form.setValue('photoUrl', await downloadFile(photoUrl));
                form.setValue('command', command ? [command.id] : []);
                form.setValue('affiliate', affiliate.id);

                setBlockedValues(fixed);
            })
            .catch(reason => toast(String(reason)));
    };

    useEffect(() => {
        setUserProfile();
    }, []);

    return {form, errors: form.formState.errors, onSubmit, blockedValues, loading};
}