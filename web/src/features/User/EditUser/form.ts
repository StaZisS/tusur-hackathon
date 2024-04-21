import {z} from 'zod';
import {validationSchema} from './validation';

export type FormFields = z.infer<typeof validationSchema>;

export type FixedValues = Omit<FullUserDto, keyof FormFields>;