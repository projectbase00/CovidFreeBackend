import { Moment } from 'moment';
import { IMobileUser } from 'app/shared/model/mobile-user.model';

export interface IEventLogging {
  id?: number;
  citizenId?: number;
  logType?: number;
  message?: string;
  createDate?: string;
  logsByUser?: IMobileUser;
}

export const defaultValue: Readonly<IEventLogging> = {};
