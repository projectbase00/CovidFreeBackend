import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './mobile-user.reducer';
import { IMobileUser } from 'app/shared/model/mobile-user.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMobileUserUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MobileUserUpdate = (props: IMobileUserUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { mobileUserEntity, loading, updating } = props;

  const { idcardImage, idcardImageContentType } = mobileUserEntity;

  const handleClose = () => {
    props.history.push('/mobile-user');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

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
        ...mobileUserEntity,
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
          <h2 id="covidFreeBackendApp.mobileUser.home.createOrEditLabel">
            <Translate contentKey="covidFreeBackendApp.mobileUser.home.createOrEditLabel">Create or edit a MobileUser</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : mobileUserEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="mobile-user-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="mobile-user-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="rodneCisloLabel" for="mobile-user-rodneCislo">
                  <Translate contentKey="covidFreeBackendApp.mobileUser.rodneCislo">Rodne Cislo</Translate>
                </Label>
                <AvField id="mobile-user-rodneCislo" type="string" className="form-control" name="rodneCislo" />
              </AvGroup>
              <AvGroup>
                <Label id="phoneNumberLabel" for="mobile-user-phoneNumber">
                  <Translate contentKey="covidFreeBackendApp.mobileUser.phoneNumber">Phone Number</Translate>
                </Label>
                <AvField id="mobile-user-phoneNumber" type="text" name="phoneNumber" />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="idcardImageLabel" for="idcardImage">
                    <Translate contentKey="covidFreeBackendApp.mobileUser.idcardImage">Idcard Image</Translate>
                  </Label>
                  <br />
                  {idcardImage ? (
                    <div>
                      {idcardImageContentType ? (
                        <a onClick={openFile(idcardImageContentType, idcardImage)}>
                          <img src={`data:${idcardImageContentType};base64,${idcardImage}`} style={{ maxHeight: '100px' }} />
                        </a>
                      ) : null}
                      <br />
                      <Row>
                        <Col md="11">
                          <span>
                            {idcardImageContentType}, {byteSize(idcardImage)}
                          </span>
                        </Col>
                        <Col md="1">
                          <Button color="danger" onClick={clearBlob('idcardImage')}>
                            <FontAwesomeIcon icon="times-circle" />
                          </Button>
                        </Col>
                      </Row>
                    </div>
                  ) : null}
                  <input id="file_idcardImage" type="file" onChange={onBlobChange(true, 'idcardImage')} accept="image/*" />
                  <AvInput type="hidden" name="idcardImage" value={idcardImage} />
                </AvGroup>
              </AvGroup>
              <AvGroup check>
                <Label id="validLabel">
                  <AvInput id="mobile-user-valid" type="checkbox" className="form-check-input" name="valid" />
                  <Translate contentKey="covidFreeBackendApp.mobileUser.valid">Valid</Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="statusLabel">
                  <AvInput id="mobile-user-status" type="checkbox" className="form-check-input" name="status" />
                  <Translate contentKey="covidFreeBackendApp.mobileUser.status">Status</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="createDateLabel" for="mobile-user-createDate">
                  <Translate contentKey="covidFreeBackendApp.mobileUser.createDate">Create Date</Translate>
                </Label>
                <AvInput
                  id="mobile-user-createDate"
                  type="datetime-local"
                  className="form-control"
                  name="createDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.mobileUserEntity.createDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="updateDateLabel" for="mobile-user-updateDate">
                  <Translate contentKey="covidFreeBackendApp.mobileUser.updateDate">Update Date</Translate>
                </Label>
                <AvInput
                  id="mobile-user-updateDate"
                  type="datetime-local"
                  className="form-control"
                  name="updateDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.mobileUserEntity.updateDate)}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/mobile-user" replace color="info">
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
  mobileUserEntity: storeState.mobileUser.entity,
  loading: storeState.mobileUser.loading,
  updating: storeState.mobileUser.updating,
  updateSuccess: storeState.mobileUser.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MobileUserUpdate);
