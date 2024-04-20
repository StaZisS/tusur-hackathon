import {z} from 'zod';
import {validationSchema} from '@/features/Login/constants/validation.ts';

export type FormFields = z.infer<typeof validationSchema>;
