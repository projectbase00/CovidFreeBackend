import { Moment } from 'moment';
import { IMobileUser } from 'app/shared/model/mobile-user.model';

export interface IOtpCodes {
  id?: number;
  otpCode?: string;
  createDate?: string;
  updateDate?: string;
  mobileUser?: IMobileUser;
}

export const defaultValue: Readonly<IOtpCodes> = {};
