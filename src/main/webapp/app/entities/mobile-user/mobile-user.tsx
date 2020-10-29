import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { openFile, byteSize, Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './mobile-user.reducer';
import { IMobileUser } from 'app/shared/model/mobile-user.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMobileUserProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const MobileUser = (props: IMobileUserProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { mobileUserList, match, loading } = props;
  return (
    <div>
      <h2 id="mobile-user-heading">
        <Translate contentKey="covidFreeBackendApp.mobileUser.home.title">Mobile Users</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="covidFreeBackendApp.mobileUser.home.createLabel">Create new Mobile User</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {mobileUserList && mobileUserList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="covidFreeBackendApp.mobileUser.citizenId">Citizen Id</Translate>
                </th>
                <th>
                  <Translate contentKey="covidFreeBackendApp.mobileUser.phoneNumber">Phone Number</Translate>
                </th>
                <th>
                  <Translate contentKey="covidFreeBackendApp.mobileUser.hash">Hash</Translate>
                </th>
                <th>
                  <Translate contentKey="covidFreeBackendApp.mobileUser.idcardImage">Idcard Image</Translate>
                </th>
                <th>
                  <Translate contentKey="covidFreeBackendApp.mobileUser.valid">Valid</Translate>
                </th>
                <th>
                  <Translate contentKey="covidFreeBackendApp.mobileUser.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="covidFreeBackendApp.mobileUser.createDate">Create Date</Translate>
                </th>
                <th>
                  <Translate contentKey="covidFreeBackendApp.mobileUser.updateDate">Update Date</Translate>
                </th>
                <th>
                  <Translate contentKey="covidFreeBackendApp.mobileUser.otpCodes">Otp Codes</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {mobileUserList.map((mobileUser, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${mobileUser.id}`} color="link" size="sm">
                      {mobileUser.id}
                    </Button>
                  </td>
                  <td>{mobileUser.citizenId}</td>
                  <td>{mobileUser.phoneNumber}</td>
                  <td>{mobileUser.hash}</td>
                  <td>
                    {mobileUser.idcardImage ? (
                      <div>
                        {mobileUser.idcardImageContentType ? (
                          <a onClick={openFile(mobileUser.idcardImageContentType, mobileUser.idcardImage)}>
                            <img
                              src={`data:${mobileUser.idcardImageContentType};base64,${mobileUser.idcardImage}`}
                              style={{ maxHeight: '30px' }}
                            />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {mobileUser.idcardImageContentType}, {byteSize(mobileUser.idcardImage)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{mobileUser.valid ? 'true' : 'false'}</td>
                  <td>{mobileUser.status ? 'true' : 'false'}</td>
                  <td>
                    {mobileUser.createDate ? <TextFormat type="date" value={mobileUser.createDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {mobileUser.updateDate ? <TextFormat type="date" value={mobileUser.updateDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{mobileUser.otpCodes ? <Link to={`otp-codes/${mobileUser.otpCodes.id}`}>{mobileUser.otpCodes.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${mobileUser.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${mobileUser.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${mobileUser.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="covidFreeBackendApp.mobileUser.home.notFound">No Mobile Users found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ mobileUser }: IRootState) => ({
  mobileUserList: mobileUser.entities,
  loading: mobileUser.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MobileUser);
