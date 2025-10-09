export interface IUser {
  id: number;
  createdBy: number;
  updatedBy: number;
  userId: string;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  bio: string;
  imageUrl: string;
  qrCodeImageUri?: string;
  lastLogin: number | string | Date;
  createdAt: string;
  updatedAt: string;
  role: string;
  authorities: string;
  accountNonExpired: boolean;
  accountNonLocked: boolean;
  credentialsNonExpired: boolean;
  enabled: boolean;
  mfa: boolean;
}

export type Role = { role: string };
export type User = { user: IUser };
export type Users = { users: IUser[] };
