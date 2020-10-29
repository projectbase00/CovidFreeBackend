import { Moment } from 'moment';
import { IEventLogging } from 'app/shared/model/event-logging.model';

export interface IMobileUser {
  id?: number;
  citizenId?: number;
  phoneNumber?: string;
  idcardImageContentType?: string;
  idcardImage?: any;
  valid?: boolean;
  status?: boolean;
  createDate?: string;
  updateDate?: string;
  eventLoggings?: IEventLogging[];
}

export const defaultValue: Readonly<IMobileUser> = {
  valid: false,
  status: false,
};
