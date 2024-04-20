import {Link} from 'react-router-dom';
import {Logo} from '..';
import {HEADER_LINKS} from '@/lib/constants/navigation/HEADER_LINKS';
import {ROUTES} from '@/lib/constants/routes.ts';
import {useDispatch, useSelector} from 'react-redux';
import {selectIsAuthenticated} from '@/auth/auth/slice.ts';
import {Button} from '@/components/ui';
import {AppDispatch} from '@/store/store.tsx';
import {logoutUser} from '@/auth/auth/thunk.ts';
import {Sheet, SheetClose, SheetContent, SheetFooter, SheetHeader, SheetTrigger} from '@/components/ui/sheet.tsx';
import {RowsIcon} from '@radix-ui/react-icons';

export const Header = () => {
    const isAuthenticated = useSelector(selectIsAuthenticated);
    const dispatch: AppDispatch = useDispatch();

    const isVisible = (href: string): boolean => {
        if (href === ROUTES.ROOT) {
            return true;
        }
        if (isAuthenticated) {
            return true;
        }
        return false;
    };

    return (
        <header className="flex justify-between md:px-10 py-4 border-b-2 px-4">
            <div className="flex justify-between">
                <div className="flex font-bold items-center mr-6">
                    <Logo/>
                </div>
                <div className="hidden lg:flex lg:justify-center">
                    <nav className="flex items-center gap-6 text-sm">
                        {HEADER_LINKS.map((link, index) => (
                            isVisible(link.href) &&
                            <Link
                                to={link.href}
                                className="transition-colors hover:text-foreground/80 text-foreground/60 active:text-current"
                                key={index}
                            >
                                <span>{link.text}</span>
                            </Link>
                        ))}
                    </nav>
                </div>
            </div>

            <div className="flex gap-2 invisible lg:visible">
                {isAuthenticated ? (
                    <>
                        <Button>
                            <Link to="/profile">
                                <span>{localStorage.getItem('email')}</span>
                            </Link>
                        </Button>
                        <Button onClick={() => dispatch(logoutUser())}>
                            <Link to="/login">
                                <span>Выйти</span>
                            </Link>
                        </Button>
                    </>
                ) : (
                    <>
                        <Button>
                            <Link to="/login">
                                <span>Войти</span>
                            </Link>
                        </Button>
                    </>
                )}
            </div>

            <div className="flex lg:hidden">
                <Sheet>
                    <SheetTrigger>
                        <RowsIcon/>
                    </SheetTrigger>
                    <SheetContent>
                        <form>
                            <SheetHeader>
                                <nav className="flex flex-col gap-6">
                                    {HEADER_LINKS.map((link, index) => (
                                        isVisible(link.href) &&
                                        <SheetClose asChild>
                                            <Link
                                                type="submit"
                                                to={link.href}
                                                className="transition-colors hover:text-foreground/80 text-foreground/60 active:text-current"
                                                key={index}
                                            >
                                                <span>{link.text}</span>
                                            </Link>
                                        </SheetClose>
                                    ))}
                                </nav>
                            </SheetHeader>
                            <SheetFooter className="bottom-5 fixed">
                                <div className="flex gap-5">
                                    {isAuthenticated ? (
                                        <>
                                            <Button type="submit">
                                                <SheetClose asChild>
                                                    <Link to="/profile">
                                                        <span>{localStorage.getItem('email')}</span>
                                                    </Link>
                                                </SheetClose>
                                            </Button>
                                            <Button onClick={() => dispatch(logoutUser())} type="submit">
                                                <SheetClose asChild>
                                                    <Link to="/login">
                                                        <span>Выйти</span>
                                                    </Link>
                                                </SheetClose>
                                            </Button>
                                        </>
                                    ) : (
                                        <>
                                            <Button type="submit">
                                                <SheetClose asChild>
                                                    <Link to="/login">
                                                        <span>Войти</span>
                                                    </Link>
                                                </SheetClose>
                                            </Button>
                                        </>
                                    )}
                                </div>
                            </SheetFooter>
                        </form>
                    </SheetContent>
                </Sheet>
            </div>
        </header>
    );
};
