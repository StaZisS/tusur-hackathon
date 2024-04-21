import {Button} from "@/components/ui";
import {useEffect, useState} from "react";
import {AffiliateCard} from "@/features/Affiliate/AffiliateCard.tsx";
import {getAffiliateByName} from "@/features/Affiliate/utils/getAffiliateByName.ts";
import {CreateAffiliateModal} from "@/features/Affiliate/CreateAffiliateModal.tsx";
import {Loader} from "@/components/ui/loader.tsx";

export const AffiliatePage = () => {
    const [isLoaded, setIsLoaded] = useState(false);
    const [affiliates, setAffiliates] = useState<AffiliationDto[]>([]);
    const [isModalCreateActive, setIsModalCreateActive] = useState(false);

    useEffect(() => {
        setIsLoaded(true);
        getAffiliateByName('')
            .then((data) => setAffiliates(data))
            .finally(() => setIsLoaded(false));
    }, []);

    return (
        <>
            <CreateAffiliateModal active={isModalCreateActive} setActive={setIsModalCreateActive}/>
            <div className="lg:p-10 p-4 space-y-5">
                <div className='flex justify-between items-center'>
                    <Button onClick={() => setIsModalCreateActive(!isModalCreateActive)}>Создать филиал</Button>
                </div>
                {isLoaded ? <Loader/> :
                    <>
                        {affiliates.map((a) => (
                            <AffiliateCard
                                key={a.id}
                                id={a.id}
                                name={a.name}
                                address={a.address}
                            />
                        ))}
                    </>
                }
            </div>
        </>
    );
}