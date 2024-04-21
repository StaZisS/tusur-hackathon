import {z} from 'zod';

export const validationSchema = z.object({
    email: z
        .string()
        .min(1, 'Ожидалось значение')
        .email('Неверный формат почты'),
    password: z
        .string()
        .min(1, 'Ожидалось значение')
        .max(100, 'Максимальная длина: 100 символов.'),
});
