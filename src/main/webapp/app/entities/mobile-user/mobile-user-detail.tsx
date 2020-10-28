import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './mobile-user.reducer';
import { IMobileUser } from 'app/shared/model/mobile-user.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMobileUserDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MobileUserDetail = (props: IMobileUserDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { mobileUserEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="covidFreeBackendApp.mobileUser.detail.title">MobileUser</Translate> [<b>{mobileUserEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="rodneCislo">
              <Translate contentKey="covidFreeBackendApp.mobileUser.rodneCislo">Rodne Cislo</Translate>
            </span>
          </dt>
          <dd>{mobileUserEntity.rodneCislo}</dd>
          <dt>
            <span id="phoneNumber">
              <Translate contentKey="covidFreeBackendApp.mobileUser.phoneNumber">Phone Number</Translate>
            </span>
          </dt>
          <dd>{mobileUserEntity.phoneNumber}</dd>
          <dt>
            <span id="idcardImage">
              <Translate contentKey="covidFreeBackendApp.mobileUser.idcardImage">Idcard Image</Translate>
            </span>
          </dt>
          <dd>
            {mobileUserEntity.idcardImage ? (
              <div>
                {mobileUserEntity.idcardImageContentType ? (
                  <a onClick={openFile(mobileUserEntity.idcardImageContentType, mobileUserEntity.idcardImage)}>
                    <img
                      src={`data:${mobileUserEntity.idcardImageContentType};base64,${mobileUserEntity.idcardImage}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {mobileUserEntity.idcardImageContentType}, {byteSize(mobileUserEntity.idcardImage)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="valid">
              <Translate contentKey="covidFreeBackendApp.mobileUser.valid">Valid</Translate>
            </span>
          </dt>
          <dd>{mobileUserEntity.valid ? 'true' : 'false'}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="covidFreeBackendApp.mobileUser.status">Status</Translate>
            </span>
          </dt>
          <dd>{mobileUserEntity.status ? 'true' : 'false'}</dd>
          <dt>
            <span id="createDate">
              <Translate contentKey="covidFreeBackendApp.mobileUser.createDate">Create Date</Translate>
            </span>
          </dt>
          <dd>
            {mobileUserEntity.createDate ? <TextFormat value={mobileUserEntity.createDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updateDate">
              <Translate contentKey="covidFreeBackendApp.mobileUser.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>
            {mobileUserEntity.updateDate ? <TextFormat value={mobileUserEntity.updateDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/mobile-user" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/mobile-user/${mobileUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ mobileUser }: IRootState) => ({
  mobileUserEntity: mobileUser.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MobileUserDetail);
