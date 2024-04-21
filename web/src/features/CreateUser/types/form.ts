import {z} from 'zod';
import {validationSchema} from '@/features/CreateUser/constants/validation.ts';

export type FormFields = z.infer<typeof validationSchema>;
