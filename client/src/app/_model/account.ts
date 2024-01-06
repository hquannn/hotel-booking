export interface Account {
    id: number,
    email: string,
    password: string,
    fullName: string,
    phone: string,
    gender: string,
    birthday: string,
    image: string | null,
    role: number
}