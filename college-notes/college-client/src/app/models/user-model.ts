import { Role } from './role-model';

export class User {
    id: number;
    username: string;
    roles:Array<Role>
}