import {SubmitHandler, useForm} from 'react-hook-form';
import {zodResolver} from '@hookform/resolvers/zod';
import {useState} from 'react';
import {validationSchema} from "@/features/CreateUser/constants/validation.ts";
import {FormFields} from "@/features/CreateUser/types/form.ts";
import {createUserRequest} from "@/features/CreateUser/utils/createUserRequest.ts";
import {toast} from "sonner";

export const useUserCreateForm = () => {
    const [isLoading, setLoading] = useState(false);
    const form = useForm<FormFields>({
        resolver: zodResolver(validationSchema),
        defaultValues: {
            commandIds: []
        }
    });

    const onSubmit: SubmitHandler<FormFields> = async (data) => {
        setLoading(true);
        await createUserRequest(data)
            .then(() => toast('Пользователь успешно создан'))
            .catch((e) => toast('Ошибка создания пользователя:' + e))
            .finally(() => setLoading(false));
    };

    return {
        form,
        errors: form.formState.errors,
        onSubmit,
        isLoading
    };
};
