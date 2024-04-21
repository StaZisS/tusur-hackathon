import {z} from 'zod';

const MAX_FILE_SIZE = 5000000;
const ACCEPTED_IMAGE_TYPES = ['image/jpeg', 'image/jpg', 'image/png', 'image/webp', 'application/octet-stream'];

export const validationSchema = z.object({
    username: z.string()
        .min(3, 'Username must be at least 3 characters')
        .max(20, 'Username must be at most 20 characters'),
    email: z.string()
        .email('validation.email.invalid'),
    password: z.string()
        .min(6, 'Password must be at least 6 characters')
        .max(50, 'Password must be at most 50 characters')
        .optional(),
    fullName: z.string()
        .min(3, 'Full name must be at least 3 characters')
        .max(50, 'Full name must be at most 50 characters'),
    birthDate: z.string()
        .min(1, 'Ожидается значение'),
    affiliate: z.string(),
    command: z.array(z.string()),
    photoUrl: z
        .custom<File>((file) => file instanceof File)
        .optional()
        .refine((file) => !file || (file instanceof File && file.size <= MAX_FILE_SIZE), {
            message: 'validation.photo.max'
        })
        .refine(
            (file) => {
                if (!file) return true;
                console.log(file?.type);
                return ACCEPTED_IMAGE_TYPES.includes(file?.type);
            },
            {message: 'Неправильный формат ввода'}
        )
});