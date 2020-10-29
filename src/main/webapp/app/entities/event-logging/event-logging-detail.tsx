import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './event-logging.reducer';
import { IEventLogging } from 'app/shared/model/event-logging.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEventLoggingDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EventLoggingDetail = (props: IEventLoggingDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { eventLoggingEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="covidFreeBackendApp.eventLogging.detail.title">EventLogging</Translate> [<b>{eventLoggingEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="citizenId">
              <Translate contentKey="covidFreeBackendApp.eventLogging.citizenId">Citizen Id</Translate>
            </span>
          </dt>
          <dd>{eventLoggingEntity.citizenId}</dd>
          <dt>
            <span id="logType">
              <Translate contentKey="covidFreeBackendApp.eventLogging.logType">Log Type</Translate>
            </span>
          </dt>
          <dd>{eventLoggingEntity.logType}</dd>
          <dt>
            <span id="message">
              <Translate contentKey="covidFreeBackendApp.eventLogging.message">Message</Translate>
            </span>
          </dt>
          <dd>{eventLoggingEntity.message}</dd>
          <dt>
            <span id="createDate">
              <Translate contentKey="covidFreeBackendApp.eventLogging.createDate">Create Date</Translate>
            </span>
          </dt>
          <dd>
            {eventLoggingEntity.createDate ? (
              <TextFormat value={eventLoggingEntity.createDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="covidFreeBackendApp.eventLogging.logsByUser">Logs By User</Translate>
          </dt>
          <dd>{eventLoggingEntity.logsByUser ? eventLoggingEntity.logsByUser.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/event-logging" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/event-logging/${eventLoggingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ eventLogging }: IRootState) => ({
  eventLoggingEntity: eventLogging.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EventLoggingDetail);
