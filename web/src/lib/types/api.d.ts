interface Tokens {
    accessToken: string;
    refreshToken: string;
    expiresIn: number;
    refreshExpiresIn: number;
    tokenType: string;
    notBeforePolicy: number;
    sessionState: string;
    scope: string;
}

interface AffiliationDto {
    id: string;
    name: string;
    address: string;
}

interface CommandDto {
    id: string;
    name: string;
    description: string;
}

interface CommonUserDto {
    id: string;
    username: string;
    email: string;
    fullName: string;
    birthDate: string;
    affiliateId: string;
    affiliateName: string;
    affiliateAddress: string;
    onlineStatus: boolean;
    commandNames: string[];
    photoUrl: string;
}

interface FullUserDto {
    id: string;
    username: string;
    email: string;
    fullName: string;
    birthDate: string;
    affiliate: AffiliationDto;
    onlineStatus: boolean;
    command?: CommandDto;
    photoUrl: string;
}

