import logo from '@/assets/tink.png'
import {cn} from '@/lib';
import React from 'react';

interface LogoProps extends React.ComponentPropsWithRef<'img'> {
}

export const Logo = React.forwardRef<HTMLDivElement, LogoProps>(({className}) => (
    <img src={logo} alt='logo' className={cn('w-44', className)}/>
));
