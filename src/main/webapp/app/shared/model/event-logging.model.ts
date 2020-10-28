import { Moment } from 'moment';
import { IMobileUser } from 'app/shared/model/mobile-user.model';

export interface IEventLogging {
  id?: number;
  rodneCislo?: number;
  logType?: number;
  message?: string;
  createDate?: string;
  logs?: IMobileUser;
  logsByUser?: IMobileUser;
}

export const defaultValue: Readonly<IEventLogging> = {};
