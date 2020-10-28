import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEventLogging, defaultValue } from 'app/shared/model/event-logging.model';

export const ACTION_TYPES = {
  FETCH_EVENTLOGGING_LIST: 'eventLogging/FETCH_EVENTLOGGING_LIST',
  FETCH_EVENTLOGGING: 'eventLogging/FETCH_EVENTLOGGING',
  CREATE_EVENTLOGGING: 'eventLogging/CREATE_EVENTLOGGING',
  UPDATE_EVENTLOGGING: 'eventLogging/UPDATE_EVENTLOGGING',
  DELETE_EVENTLOGGING: 'eventLogging/DELETE_EVENTLOGGING',
  RESET: 'eventLogging/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEventLogging>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type EventLoggingState = Readonly<typeof initialState>;

// Reducer

export default (state: EventLoggingState = initialState, action): EventLoggingState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EVENTLOGGING_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EVENTLOGGING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_EVENTLOGGING):
    case REQUEST(ACTION_TYPES.UPDATE_EVENTLOGGING):
    case REQUEST(ACTION_TYPES.DELETE_EVENTLOGGING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_EVENTLOGGING_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EVENTLOGGING):
    case FAILURE(ACTION_TYPES.CREATE_EVENTLOGGING):
    case FAILURE(ACTION_TYPES.UPDATE_EVENTLOGGING):
    case FAILURE(ACTION_TYPES.DELETE_EVENTLOGGING):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EVENTLOGGING_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EVENTLOGGING):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_EVENTLOGGING):
    case SUCCESS(ACTION_TYPES.UPDATE_EVENTLOGGING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EVENTLOGGING):
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

const apiUrl = 'api/event-loggings';

// Actions

export const getEntities: ICrudGetAllAction<IEventLogging> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_EVENTLOGGING_LIST,
  payload: axios.get<IEventLogging>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IEventLogging> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EVENTLOGGING,
    payload: axios.get<IEventLogging>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEventLogging> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EVENTLOGGING,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEventLogging> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EVENTLOGGING,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEventLogging> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EVENTLOGGING,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
