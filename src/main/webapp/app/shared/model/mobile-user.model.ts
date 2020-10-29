import { Moment } from 'moment';
import { IOtpCodes } from 'app/shared/model/otp-codes.model';
import { IEventLogging } from 'app/shared/model/event-logging.model';

export interface IMobileUser {
  id?: number;
  citizenId?: number;
  phoneNumber?: string;
  hash?: string;
  idcardImageContentType?: string;
  idcardImage?: any;
  valid?: boolean;
  status?: boolean;
  createDate?: string;
  updateDate?: string;
  otpCodes?: IOtpCodes;
  eventLoggings?: IEventLogging[];
}

export const defaultValue: Readonly<IMobileUser> = {
  valid: false,
  status: false,
};
