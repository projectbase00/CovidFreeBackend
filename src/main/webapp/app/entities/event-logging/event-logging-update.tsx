import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IMobileUser } from 'app/shared/model/mobile-user.model';
import { getEntities as getMobileUsers } from 'app/entities/mobile-user/mobile-user.reducer';
import { getEntity, updateEntity, createEntity, reset } from './event-logging.reducer';
import { IEventLogging } from 'app/shared/model/event-logging.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEventLoggingUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EventLoggingUpdate = (props: IEventLoggingUpdateProps) => {
  const [logsId, setLogsId] = useState('0');
  const [logsByUserId, setLogsByUserId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { eventLoggingEntity, mobileUsers, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/event-logging');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getMobileUsers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.createDate = convertDateTimeToServer(values.createDate);

    if (errors.length === 0) {
      const entity = {
        ...eventLoggingEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="covidFreeBackendApp.eventLogging.home.createOrEditLabel">
            <Translate contentKey="covidFreeBackendApp.eventLogging.home.createOrEditLabel">Create or edit a EventLogging</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : eventLoggingEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="event-logging-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="event-logging-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="rodneCisloLabel" for="event-logging-rodneCislo">
                  <Translate contentKey="covidFreeBackendApp.eventLogging.rodneCislo">Rodne Cislo</Translate>
                </Label>
                <AvField id="event-logging-rodneCislo" type="string" className="form-control" name="rodneCislo" />
              </AvGroup>
              <AvGroup>
                <Label id="logTypeLabel" for="event-logging-logType">
                  <Translate contentKey="covidFreeBackendApp.eventLogging.logType">Log Type</Translate>
                </Label>
                <AvField id="event-logging-logType" type="string" className="form-control" name="logType" />
              </AvGroup>
              <AvGroup>
                <Label id="messageLabel" for="event-logging-message">
                  <Translate contentKey="covidFreeBackendApp.eventLogging.message">Message</Translate>
                </Label>
                <AvField id="event-logging-message" type="text" name="message" />
              </AvGroup>
              <AvGroup>
                <Label id="createDateLabel" for="event-logging-createDate">
                  <Translate contentKey="covidFreeBackendApp.eventLogging.createDate">Create Date</Translate>
                </Label>
                <AvInput
                  id="event-logging-createDate"
                  type="datetime-local"
                  className="form-control"
                  name="createDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.eventLoggingEntity.createDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="event-logging-logs">
                  <Translate contentKey="covidFreeBackendApp.eventLogging.logs">Logs</Translate>
                </Label>
                <AvInput id="event-logging-logs" type="select" className="form-control" name="logs.id">
                  <option value="" key="0" />
                  {mobileUsers
                    ? mobileUsers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="event-logging-logsByUser">
                  <Translate contentKey="covidFreeBackendApp.eventLogging.logsByUser">Logs By User</Translate>
                </Label>
                <AvInput id="event-logging-logsByUser" type="select" className="form-control" name="logsByUser.id">
                  <option value="" key="0" />
                  {mobileUsers
                    ? mobileUsers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/event-logging" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  mobileUsers: storeState.mobileUser.entities,
  eventLoggingEntity: storeState.eventLogging.entity,
  loading: storeState.eventLogging.loading,
  updating: storeState.eventLogging.updating,
  updateSuccess: storeState.eventLogging.updateSuccess,
});

const mapDispatchToProps = {
  getMobileUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EventLoggingUpdate);
