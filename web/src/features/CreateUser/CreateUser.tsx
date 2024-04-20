import {useUserCreateForm} from "@/features/CreateUser/hooks/useUserCreateForm.ts";
import {Loader} from "@/components/ui/loader.tsx";
import {
    Button,
    Command,
    CommandEmpty,
    CommandGroup,
    CommandInput,
    CommandItem,
    Form,
    FormControl,
    FormField,
    FormItem,
    FormLabel,
    FormMessage,
    Input,
    Popover,
    PopoverContent,
    PopoverTrigger,
    ScrollArea
} from "@/components/ui";
import {CaretSortIcon, CheckIcon} from '@radix-ui/react-icons';
import {cn} from "@/lib";
import {useEffect, useState} from "react";
import {DropzoneCard} from "@/components/dropzone";
import {getCommandsByName} from "@/features/Command/utils/getCommandsByName.ts";
import {getAffiliateByName} from "@/features/Affiliate/utils/getAffiliateByName.ts";

export const CreateUser = () => {
    const {form, errors, onSubmit, isLoading} = useUserCreateForm();
    const [commands, setCommands] = useState<CommandDto[]>([]);
    const [affiliations, setAffiliations] = useState<AffiliationDto[]>([]);

    useEffect(() => {
        getCommandsByName('').then((data) => setCommands(data));
        getAffiliateByName('').then((data) => setAffiliations(data));
    }, []);

    return (
        <Form {...form} >
            {isLoading ? (<Loader/>) : (
                <form className="flex flex-col max-w-prose gap-8 border p-4 rounded-lg"
                      onSubmit={form.handleSubmit(onSubmit)}>
                    <div className='flex items-center gap-4 flex-row justify-between'>
                        <div className='w-1/2'>
                            <FormField
                                control={form.control}
                                name="username"
                                render={({field}) => (
                                    <FormItem>
                                        <FormLabel>Логин</FormLabel>
                                        <FormControl>
                                            <Input type="text" {...field} isError={!!errors.username}/>
                                        </FormControl>
                                        <FormMessage className="absolute">{errors.username &&
                                            <span>{errors.username.message}</span>}</FormMessage>
                                    </FormItem>
                                )}
                            />
                            <FormField
                                control={form.control}
                                name="email"
                                render={({field}) => (
                                    <FormItem>
                                        <FormLabel>Email</FormLabel>
                                        <FormControl>
                                            <Input type="email" {...field} isError={!!errors.email}/>
                                        </FormControl>
                                        <FormMessage className="absolute">{errors.email &&
                                            <span>{errors.email.message}</span>}</FormMessage>
                                    </FormItem>
                                )}
                            />
                        </div>

                        <div className=''>
                            <FormField
                                control={form.control}
                                name='photo'
                                render={({field}) => {
                                    return (
                                        <FormItem className='flex items-center gap-3'>
                                            <FormControl>
                                                <div>
                                                    <DropzoneCard {...field} className='h-48 w-48'/>
                                                </div>
                                            </FormControl>
                                            <div>
                                                <FormMessage className="absolute">{errors.photo &&
                                                    <span>{errors.photo.message}</span>}</FormMessage>
                                            </div>
                                        </FormItem>
                                    );
                                }}
                            />
                        </div>
                    </div>

                    <FormField
                        control={form.control}
                        name="fullName"
                        render={({field}) => (
                            <FormItem>
                                <FormLabel>Ф.И.О</FormLabel>
                                <FormControl>
                                    <Input type="text" {...field} isError={!!errors.fullName}/>
                                </FormControl>
                                <FormMessage className="absolute">{errors.fullName &&
                                    <span>{errors.fullName.message}</span>}</FormMessage>
                            </FormItem>
                        )}
                    />

                    <FormField
                        control={form.control}
                        name="birthDate"
                        render={({field}) => (
                            <FormItem>
                                <FormLabel>Дата рождения</FormLabel>
                                <FormControl>
                                    <Input type="date" {...field} max={new Date().toISOString().substring(0, 10)}/>
                                </FormControl>
                                <FormMessage>{errors.birthDate && <span>{errors.birthDate.message}</span>}</FormMessage>
                            </FormItem>
                        )}
                    />

                    <FormField
                        control={form.control}
                        name="affiliateId"
                        render={({field}) => (
                            <FormItem className="flex flex-col">
                                <FormLabel>Филиал</FormLabel>
                                <Popover>
                                    <PopoverTrigger asChild>
                                        <FormControl>
                                            <Button
                                                variant="outline"
                                                role="combobox"
                                                className={cn(
                                                    'w-full justify-between',
                                                    !field.value && 'text-muted-foreground',
                                                )}
                                            >
                                                {field.value
                                                    ? affiliations.find(
                                                        (u) => u.id === field.value,
                                                    )?.name
                                                    : 'Выберите филиал'}
                                                <CaretSortIcon className="ml-2 h-4 w-4 shrink-0 opacity-50"/>
                                            </Button>
                                        </FormControl>
                                    </PopoverTrigger>
                                    <PopoverContent className="p-0">
                                        <Command className="w-full">
                                            <CommandInput
                                                placeholder="Поиск филиала..."
                                                className="h-9 w-full"
                                            />
                                            <CommandEmpty>Филиал не найден.</CommandEmpty>
                                            <CommandGroup className="overflow-auto max-h-80">
                                                <ScrollArea className="h-72 w-full">
                                                    {affiliations.map((u) => (
                                                        <CommandItem
                                                            value={u.name}
                                                            key={u.id}
                                                            onSelect={() => {
                                                                form.setValue('affiliateId', u.id);
                                                            }}
                                                        >
                                                            {u.name}
                                                            <CheckIcon
                                                                className={cn(
                                                                    'ml-auto h-4 w-4',
                                                                    u.id === field.value
                                                                        ? 'opacity-100'
                                                                        : 'opacity-0',
                                                                )}
                                                            />
                                                        </CommandItem>
                                                    ))}
                                                </ScrollArea>
                                            </CommandGroup>
                                        </Command>
                                    </PopoverContent>
                                </Popover>
                                <FormMessage className="absolute">{errors.affiliateId &&
                                    <span>{errors.affiliateId.message}</span>}</FormMessage>
                            </FormItem>
                        )}
                    />

                    <FormField
                        control={form.control}
                        name="commandIds"
                        render={({ field }) => (
                            <FormItem className="flex flex-col">
                                <FormLabel>Команды</FormLabel>
                                <Popover>
                                    <PopoverTrigger asChild>
                                        <FormControl>
                                            <Button
                                                variant="outline"
                                                role="combobox"
                                                className={cn(
                                                    'w-full justify-between',
                                                    !field.value.length && 'text-muted-foreground',
                                                )}
                                            >
                                                {field.value.length
                                                    ? field.value.map(id =>
                                                        commands.find(u => u.id === id)?.name
                                                    ).join(', ')
                                                    : 'Выберите команды'}
                                                <CaretSortIcon className="ml-2 h-4 w-4 shrink-0 opacity-50"/>
                                            </Button>
                                        </FormControl>
                                    </PopoverTrigger>
                                    <PopoverContent className="p-0">
                                        <Command className="w-full">
                                            <CommandInput
                                                placeholder="Поиск команды..."
                                                className="h-9 w-full"
                                            />
                                            <CommandEmpty>Команда не найдена.</CommandEmpty>
                                            <CommandGroup className="overflow-auto max-h-80">
                                                <ScrollArea className="h-72 w-full">
                                                    {commands.map(u => (
                                                        <CommandItem
                                                            key={u.id}
                                                            value={u.name}
                                                            onSelect={() => {
                                                                const newValue = field.value.includes(u.id)
                                                                    ? field.value.filter(id => id !== u.id)
                                                                    : [...field.value, u.id];
                                                                form.setValue('commandIds', newValue);
                                                            }}
                                                        >
                                                            {u.name}
                                                            <CheckIcon
                                                                className={cn(
                                                                    'ml-auto h-4 w-4',
                                                                    field.value.includes(u.id)
                                                                        ? 'opacity-100'
                                                                        : 'opacity-0',
                                                                )}
                                                            />
                                                        </CommandItem>
                                                    ))}
                                                </ScrollArea>
                                            </CommandGroup>
                                        </Command>
                                    </PopoverContent>
                                </Popover>
                                <FormMessage className="absolute">{errors.commandIds &&
                                    <span>{errors.commandIds.message}</span>}</FormMessage>
                            </FormItem>
                        )}
                    />

                    <FormField
                        control={form.control}
                        name="password"
                        render={({field}) => (
                            <FormItem>
                                <FormLabel>Пароль</FormLabel>
                                <FormControl>
                                    <Input type="password" {...field} isError={!!errors.password}/>
                                </FormControl>
                                <FormMessage className="absolute">
                                    {errors.password && <span>{errors.password.message}</span>}
                                </FormMessage>
                            </FormItem>
                        )}
                    />

                    <Button type="submit" className="mt-3">
                        Зарегестрировать пользователя
                    </Button>
                    {errors.root && <span className="text-error -mt-3">{errors.root.message}</span>}
                </form>
            )}
        </Form>
    );
}