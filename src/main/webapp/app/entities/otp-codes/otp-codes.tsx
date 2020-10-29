import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './otp-codes.reducer';
import { IOtpCodes } from 'app/shared/model/otp-codes.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOtpCodesProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const OtpCodes = (props: IOtpCodesProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { otpCodesList, match, loading } = props;
  return (
    <div>
      <h2 id="otp-codes-heading">
        <Translate contentKey="covidFreeBackendApp.otpCodes.home.title">Otp Codes</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="covidFreeBackendApp.otpCodes.home.createLabel">Create new Otp Codes</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {otpCodesList && otpCodesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="covidFreeBackendApp.otpCodes.citizenId">Citizen Id</Translate>
                </th>
                <th>
                  <Translate contentKey="covidFreeBackendApp.otpCodes.otpCode">Otp Code</Translate>
                </th>
                <th>
                  <Translate contentKey="covidFreeBackendApp.otpCodes.createDate">Create Date</Translate>
                </th>
                <th>
                  <Translate contentKey="covidFreeBackendApp.otpCodes.updateDate">Update Date</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {otpCodesList.map((otpCodes, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${otpCodes.id}`} color="link" size="sm">
                      {otpCodes.id}
                    </Button>
                  </td>
                  <td>{otpCodes.citizenId}</td>
                  <td>{otpCodes.otpCode}</td>
                  <td>{otpCodes.createDate ? <TextFormat type="date" value={otpCodes.createDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{otpCodes.updateDate ? <TextFormat type="date" value={otpCodes.updateDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${otpCodes.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${otpCodes.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${otpCodes.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="covidFreeBackendApp.otpCodes.home.notFound">No Otp Codes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ otpCodes }: IRootState) => ({
  otpCodesList: otpCodes.entities,
  loading: otpCodes.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OtpCodes);
