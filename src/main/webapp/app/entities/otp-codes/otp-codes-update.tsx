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
import { getEntity, updateEntity, createEntity, reset } from './otp-codes.reducer';
import { IOtpCodes } from 'app/shared/model/otp-codes.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IOtpCodesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const OtpCodesUpdate = (props: IOtpCodesUpdateProps) => {
  const [mobileUserId, setMobileUserId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { otpCodesEntity, mobileUsers, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/otp-codes');
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
    values.updateDate = convertDateTimeToServer(values.updateDate);

    if (errors.length === 0) {
      const entity = {
        ...otpCodesEntity,
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
          <h2 id="covidFreeBackendApp.otpCodes.home.createOrEditLabel">
            <Translate contentKey="covidFreeBackendApp.otpCodes.home.createOrEditLabel">Create or edit a OtpCodes</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : otpCodesEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="otp-codes-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="otp-codes-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="citizenIdLabel" for="otp-codes-citizenId">
                  <Translate contentKey="covidFreeBackendApp.otpCodes.citizenId">Citizen Id</Translate>
                </Label>
                <AvField id="otp-codes-citizenId" type="string" className="form-control" name="citizenId" />
              </AvGroup>
              <AvGroup>
                <Label id="otpCodeLabel" for="otp-codes-otpCode">
                  <Translate contentKey="covidFreeBackendApp.otpCodes.otpCode">Otp Code</Translate>
                </Label>
                <AvField id="otp-codes-otpCode" type="text" name="otpCode" />
              </AvGroup>
              <AvGroup>
                <Label id="createDateLabel" for="otp-codes-createDate">
                  <Translate contentKey="covidFreeBackendApp.otpCodes.createDate">Create Date</Translate>
                </Label>
                <AvInput
                  id="otp-codes-createDate"
                  type="datetime-local"
                  className="form-control"
                  name="createDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.otpCodesEntity.createDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="updateDateLabel" for="otp-codes-updateDate">
                  <Translate contentKey="covidFreeBackendApp.otpCodes.updateDate">Update Date</Translate>
                </Label>
                <AvInput
                  id="otp-codes-updateDate"
                  type="datetime-local"
                  className="form-control"
                  name="updateDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.otpCodesEntity.updateDate)}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/otp-codes" replace color="info">
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
  otpCodesEntity: storeState.otpCodes.entity,
  loading: storeState.otpCodes.loading,
  updating: storeState.otpCodes.updating,
  updateSuccess: storeState.otpCodes.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(OtpCodesUpdate);
