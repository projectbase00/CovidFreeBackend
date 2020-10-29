import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOtpCodes, defaultValue } from 'app/shared/model/otp-codes.model';

export const ACTION_TYPES = {
  FETCH_OTPCODES_LIST: 'otpCodes/FETCH_OTPCODES_LIST',
  FETCH_OTPCODES: 'otpCodes/FETCH_OTPCODES',
  CREATE_OTPCODES: 'otpCodes/CREATE_OTPCODES',
  UPDATE_OTPCODES: 'otpCodes/UPDATE_OTPCODES',
  DELETE_OTPCODES: 'otpCodes/DELETE_OTPCODES',
  RESET: 'otpCodes/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOtpCodes>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type OtpCodesState = Readonly<typeof initialState>;

// Reducer

export default (state: OtpCodesState = initialState, action): OtpCodesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_OTPCODES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_OTPCODES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_OTPCODES):
    case REQUEST(ACTION_TYPES.UPDATE_OTPCODES):
    case REQUEST(ACTION_TYPES.DELETE_OTPCODES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_OTPCODES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_OTPCODES):
    case FAILURE(ACTION_TYPES.CREATE_OTPCODES):
    case FAILURE(ACTION_TYPES.UPDATE_OTPCODES):
    case FAILURE(ACTION_TYPES.DELETE_OTPCODES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_OTPCODES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_OTPCODES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_OTPCODES):
    case SUCCESS(ACTION_TYPES.UPDATE_OTPCODES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_OTPCODES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/otp-codes';

// Actions

export const getEntities: ICrudGetAllAction<IOtpCodes> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_OTPCODES_LIST,
  payload: axios.get<IOtpCodes>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IOtpCodes> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_OTPCODES,
    payload: axios.get<IOtpCodes>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IOtpCodes> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_OTPCODES,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IOtpCodes> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_OTPCODES,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOtpCodes> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_OTPCODES,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
