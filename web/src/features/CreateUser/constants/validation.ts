import {z} from 'zod';

const MAX_FILE_SIZE = 5000000;
const ACCEPTED_IMAGE_TYPES = ['image/jpeg', 'image/jpg', 'image/png', 'image/webp'];

export const validationSchema = z.object({
    username: z
        .string()
        .min(3, 'Ожидалось значение')
        .max(100, 'Максимальная длина: 100 символов.'),
    email: z
        .string()
        .email('Неверный формат почты'),
    password: z
        .string()
        .min(6, 'Минимальная длина: 6 символов.')
        .max(100, 'Максимальная длина: 100 символов.'),
    fullName: z
        .string()
        .min(3, 'Ожидалось значение')
        .max(100, 'Максимальная длина: 100 символов.'),
    birthDate: z
        .string()
        .min(1, 'Ожидалось значение'),
    affiliateId: z
        .string(),
    commandIds: z
        .array(z.string()),
    photo: z
        .custom<File>((file) => file instanceof File)
        .optional()
        .refine((file) => !file || (file instanceof File && file.size <= MAX_FILE_SIZE), {
            message: 'validation.photo.max'
        })
        .refine(
            (file) => {
                if (!file) return true;
                return ACCEPTED_IMAGE_TYPES.includes(file?.type);
            },
            {message: 'Неправильный формат ввода'}
        )
});
