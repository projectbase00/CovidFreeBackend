import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OtpCodes from './otp-codes';
import OtpCodesDetail from './otp-codes-detail';
import OtpCodesUpdate from './otp-codes-update';
import OtpCodesDeleteDialog from './otp-codes-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OtpCodesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OtpCodesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OtpCodesDetail} />
      <ErrorBoundaryRoute path={match.url} component={OtpCodes} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={OtpCodesDeleteDialog} />
  </>
);

export default Routes;
