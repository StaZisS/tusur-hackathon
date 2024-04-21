import {createBrowserRouter} from 'react-router-dom';
import {App} from './App';
import {ROUTES} from "@/lib/constants/routes.ts";
import {MainPage} from "@/pages/MainPage/MainPage.tsx";
import {LoginPage} from "@/pages";
import {CreateUserPage} from "@/pages/User/CreateUserPage.tsx";
import {UsersPage} from "@/pages/User/UsersPage.tsx";
import {CommandPage} from "@/pages/CommandPage/CommandPage.tsx";
import {AffiliatePage} from "@/pages/AffiliatePage/AffiliatePage.tsx";
import {EditUserPage} from "@/pages/User/EditUserPage.tsx";


export const Router = createBrowserRouter([
    {
        element: <App/>,
        children: [
            {
                path: ROUTES.ROOT,
                element: <MainPage/>,
            },
            {
                path: ROUTES.LOGIN,
                element: <LoginPage/>,
            },
            {
                path: ROUTES.USERS,
                element: <UsersPage/>,
            },
            {
                path: ROUTES.CREATE_USER,
                element: <CreateUserPage/>,
            },
            {
                path: ROUTES.COMMANDS,
                element: <CommandPage/>,
            },
            {
                path: ROUTES.AFFILIATES,
                element: <AffiliatePage/>,
            },
            {
                path: ROUTES.EDIT_USER,
                element: <EditUserPage />,
            }
        ],
    },
]);
