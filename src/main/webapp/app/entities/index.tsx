import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MobileUser from './mobile-user';
import EventLogging from './event-logging';
import OtpCodes from './otp-codes';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}mobile-user`} component={MobileUser} />
      <ErrorBoundaryRoute path={`${match.url}event-logging`} component={EventLogging} />
      <ErrorBoundaryRoute path={`${match.url}otp-codes`} component={OtpCodes} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
