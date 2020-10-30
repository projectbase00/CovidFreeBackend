import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './otp-codes.reducer';
import { IOtpCodes } from 'app/shared/model/otp-codes.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOtpCodesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const OtpCodesDetail = (props: IOtpCodesDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { otpCodesEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="covidFreeBackendApp.otpCodes.detail.title">OtpCodes</Translate> [<b>{otpCodesEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="otpCode">
              <Translate contentKey="covidFreeBackendApp.otpCodes.otpCode">Otp Code</Translate>
            </span>
          </dt>
          <dd>{otpCodesEntity.otpCode}</dd>
          <dt>
            <span id="createDate">
              <Translate contentKey="covidFreeBackendApp.otpCodes.createDate">Create Date</Translate>
            </span>
          </dt>
          <dd>
            {otpCodesEntity.createDate ? <TextFormat value={otpCodesEntity.createDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updateDate">
              <Translate contentKey="covidFreeBackendApp.otpCodes.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>
            {otpCodesEntity.updateDate ? <TextFormat value={otpCodesEntity.updateDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/otp-codes" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/otp-codes/${otpCodesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ otpCodes }: IRootState) => ({
  otpCodesEntity: otpCodes.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OtpCodesDetail);
