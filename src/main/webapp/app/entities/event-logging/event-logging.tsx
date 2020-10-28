import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './event-logging.reducer';
import { IEventLogging } from 'app/shared/model/event-logging.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEventLoggingProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const EventLogging = (props: IEventLoggingProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { eventLoggingList, match, loading } = props;
  return (
    <div>
      <h2 id="event-logging-heading">
        <Translate contentKey="covidFreeBackendApp.eventLogging.home.title">Event Loggings</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="covidFreeBackendApp.eventLogging.home.createLabel">Create new Event Logging</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {eventLoggingList && eventLoggingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="covidFreeBackendApp.eventLogging.rodneCislo">Rodne Cislo</Translate>
                </th>
                <th>
                  <Translate contentKey="covidFreeBackendApp.eventLogging.logType">Log Type</Translate>
                </th>
                <th>
                  <Translate contentKey="covidFreeBackendApp.eventLogging.message">Message</Translate>
                </th>
                <th>
                  <Translate contentKey="covidFreeBackendApp.eventLogging.createDate">Create Date</Translate>
                </th>
                <th>
                  <Translate contentKey="covidFreeBackendApp.eventLogging.logs">Logs</Translate>
                </th>
                <th>
                  <Translate contentKey="covidFreeBackendApp.eventLogging.logsByUser">Logs By User</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {eventLoggingList.map((eventLogging, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${eventLogging.id}`} color="link" size="sm">
                      {eventLogging.id}
                    </Button>
                  </td>
                  <td>{eventLogging.rodneCislo}</td>
                  <td>{eventLogging.logType}</td>
                  <td>{eventLogging.message}</td>
                  <td>
                    {eventLogging.createDate ? <TextFormat type="date" value={eventLogging.createDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{eventLogging.logs ? <Link to={`mobile-user/${eventLogging.logs.id}`}>{eventLogging.logs.id}</Link> : ''}</td>
                  <td>
                    {eventLogging.logsByUser ? (
                      <Link to={`mobile-user/${eventLogging.logsByUser.id}`}>{eventLogging.logsByUser.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${eventLogging.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${eventLogging.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${eventLogging.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="covidFreeBackendApp.eventLogging.home.notFound">No Event Loggings found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ eventLogging }: IRootState) => ({
  eventLoggingList: eventLogging.entities,
  loading: eventLogging.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EventLogging);
