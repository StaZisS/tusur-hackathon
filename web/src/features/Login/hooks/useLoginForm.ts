import {SubmitHandler, useForm} from 'react-hook-form';
import {FormFields} from '@/features/Login/types/form';
import {zodResolver} from '@hookform/resolvers/zod';
import {validationSchema} from '@/features/Login/constants/validation';
import {useNavigate} from 'react-router-dom';
import {AppDispatch} from '@/store/store.tsx';
import {useDispatch} from "react-redux";
import {loginUser} from '@/auth/auth/thunk.ts';
import {ACCESS_TOKEN_NAME} from '@/lib/constants/userClaims.ts';
import {setToken} from '@/auth/auth/slice.ts';
import {useState} from 'react';

export const useLoginForm = () => {
    const navigate = useNavigate();
    const dispatch: AppDispatch = useDispatch();
    const [isLoading, setLoading] = useState(false);
    const form = useForm<FormFields>({
        resolver: zodResolver(validationSchema),
    });

    const onSubmit: SubmitHandler<FormFields> = async (data) => {
        setLoading(true);
        await dispatch(loginUser(data))
            .unwrap()
            .then(tokens => {
                localStorage.setItem(ACCESS_TOKEN_NAME, tokens.accessToken);
                localStorage.setItem("email", data.email);
                dispatch(setToken(tokens.accessToken));
                navigate(-1);
            })
            .catch(() => setLoading(false));
    };

    return {
        form,
        errors: form.formState.errors,
        onSubmit,
        isLoading
    };
};
