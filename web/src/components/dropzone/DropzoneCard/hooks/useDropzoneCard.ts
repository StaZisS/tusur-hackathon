import {toast} from 'sonner';

import type {FileType} from '../constants/types.ts';

interface UseDropzoneCardProps {
    onChange: (props: File | undefined) => void;
    type: FileType;
}

export const useDropzoneCard = ({onChange}: UseDropzoneCardProps) => {

    const onError = () =>
        toast.error('Неверный формат файла. Пожалуйста, загрузите файл в формате .jpg, .jpeg, .png или .svg');

    const deleteFile = () => onChange(undefined);

    return {
        functions: {onError, deleteFile}
    };
};
