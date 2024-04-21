import {Link} from "react-router-dom";
import {ROUTES} from "@/lib/constants/routes.ts";
import {Button} from "@/components/ui";
import {useEffect, useState} from "react";
import {getUsersByName} from "@/features/User/utils/getUsersByName.ts";
import {UserCart} from "@/features/User/UserCart.tsx";
import {Loader} from "@/components/ui/loader.tsx";

export const UsersPage = () => {
    const [isLoading, setIsLoading] = useState(true);
    const [users, setUsers] = useState<CommonUserDto[]>([]);

    useEffect(() => {
        setIsLoading(true);
        getUsersByName('')
            .then((data) => setUsers(data))
            .finally(() => setIsLoading(false));
    }, []);

    return (
        <div className="lg:p-10 p-4 space-y-5">
            <div className='flex justify-between items-center'>
                <Button>
                    <Link to={ROUTES.CREATE_USER}>Создать пользователя</Link>
                </Button>
            </div>
            {isLoading ? <Loader/> :
                <>
                    {users.map((user) => (
                        <UserCart
                            key={user.id}
                            id={user.id}
                            username={user.username}
                            email={user.email}
                            fullName={user.fullName}
                            birthDate={user.birthDate}
                            affiliateId={user.affiliateId}
                            affiliateName={user.affiliateName}
                            photoUrl={user.photoUrl}
                        />
                    ))}
                </>
            }
        </div>
    );
}