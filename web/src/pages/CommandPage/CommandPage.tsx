import {Button} from "@/components/ui";
import {useEffect, useState} from "react";
import {getCommandsByName} from "@/features/Command/utils/getCommandsByName.ts";
import {CommandCard} from "@/features/Command/CommandCard.tsx";
import {CreateCommandModal} from "@/features/Command/CreateCommandModal.tsx";
import {Loader} from "@/components/ui/loader.tsx";

export const CommandPage = () => {
    const [isLoaded, setIsLoaded] = useState(false);
    const [command, setCommand] = useState<CommandDto[]>([]);
    const [isModalCreateActive, setIsModalCreateActive] = useState(false);

    useEffect(() => {
        setIsLoaded(true);
        getCommandsByName('')
            .then((data) => setCommand(data))
            .finally(() => setIsLoaded(false));
    }, []);

    return (
        <>
            <CreateCommandModal
                setIsActive={setIsModalCreateActive}
                isActive={isModalCreateActive}
            />
            <div className="lg:p-10 p-4 space-y-5">
                <div className='flex justify-between items-center'>
                    <Button onClick={() => setIsModalCreateActive(!isModalCreateActive)}>Создать команду</Button>
                </div>
                {isLoaded ? <Loader/> :
                    <>
                        {command.map((c) => (
                            <CommandCard
                                key={c.id}
                                id={c.id}
                                name={c.name}
                                description={c.description}
                            />
                        ))}
                    </>
                }
            </div>
        </>
    );
}