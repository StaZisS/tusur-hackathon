import {Label} from '@/components/ui/label.tsx';
import {Card} from "@/components/ui";
import {Avatar, AvatarFallback, AvatarImage} from "@/components/ui/avatar.tsx";
import telegramIcon from '@/assets/telegram.svg';
import yangel from '@/assets/yangel.jpg';
import dovydenko from '@/assets/dovydenko.jpg';
import seregin from '@/assets/seregin.jpg';
import gafarov from '@/assets/gafarov.jpg';
import lelikov from '@/assets/lelikov.jpg';
import githubIcon from '@/assets/github.svg';
import {Link} from "react-router-dom";

interface TeamMemberProps {
    fullName: string;
    role: string;
    photoPath: string;
    telegram: string;
    github: string;
}

const arrayTeamMembers: TeamMemberProps[] = [
    {
        fullName: 'Юрий Янгель',
        role: 'Lead Android Mobile developer',
        photoPath: yangel,
        telegram: 'https://t.me/rendivy',
        github: 'https://github.com/rendivy',
    },
    {
        fullName: 'Матвей Серегин',
        role: 'Mobile Android developer',
        photoPath: seregin,
        telegram: 'https://t.me/l4ntt',
        github: 'https://github.com/Lvntt',
    },
    {
        fullName: 'Руслан Гафаров',
        role: 'Mobile Android developer',
        photoPath: gafarov,
        telegram: 'https://t.me/suslanium',
        github: 'https://github.com/Suslanium',
    },
    {
        fullName: 'Гордей Довыденко',
        role: 'Java Backend developer',
        photoPath: dovydenko,
        telegram: 'https://t.me/ggnation',
        github: 'https://github.com/StaZisS',
    },
    {
        fullName: 'Леликов Сергей',
        role: 'Java Backend developer',
        photoPath: lelikov,
        telegram: 'https://t.me/help_tom',
        github: 'https://github.com/T9404',
    },
];

export const MainPage = () => (
    <div className="lg:p-10 p-4 space-y-5">
        <Label htmlFor="terms" className='text-4xl font-bold'>Состав команды</Label>

        <div className='items-center space-y-2'>
            {arrayTeamMembers.map((teamMember, index) => (
                <Card key={index}>
                    <div className='flex gap-2 items-center'>
                        <div className='flex flex-auto space-x-4 items-center p-6'>
                            <Avatar className='h-40 w-40'>
                                <AvatarImage src={teamMember.photoPath} alt={teamMember.fullName}/>
                                <AvatarFallback>{teamMember.fullName[0]}</AvatarFallback>
                            </Avatar>
                            <div>
                                <div className='font-bold text-xl'>
                                    <>{teamMember.fullName}</>
                                </div>
                                <div className='text-lg'>
                                    <>{teamMember.role}</>
                                </div>
                            </div>
                        </div>
                        <div className='flex items-center p-6'>
                            <Link to={teamMember.telegram}>
                                <img src={telegramIcon} alt='telegram' className='h-10 w-10'/>
                            </Link>
                            <Link to={teamMember.github}>
                                <img src={githubIcon} alt='github' className='h-12 w-12'/>
                            </Link>
                        </div>
                    </div>
                </Card>
                ))}
        </div>
    </div>
);
