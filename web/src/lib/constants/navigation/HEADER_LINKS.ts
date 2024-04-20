import {ROUTES} from '../routes';

interface NavigationLinkInfo {
    text: string;
    href: string;
}

export const HEADER_LINKS: NavigationLinkInfo[] = [
    {
        text: 'Основная страница',
        href: ROUTES.ROOT
    },
    {
        text: 'Команды',
        href: ROUTES.COMMANDS
    },
    {
        text: 'Филиалы',
        href: ROUTES.AFFILIATES
    },
    {
        text: 'Пользователи',
        href: ROUTES.USERS
    }
];
