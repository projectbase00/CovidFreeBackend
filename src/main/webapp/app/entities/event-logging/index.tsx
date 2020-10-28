import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EventLogging from './event-logging';
import EventLoggingDetail from './event-logging-detail';
import EventLoggingUpdate from './event-logging-update';
import EventLoggingDeleteDialog from './event-logging-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EventLoggingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EventLoggingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EventLoggingDetail} />
      <ErrorBoundaryRoute path={match.url} component={EventLogging} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EventLoggingDeleteDialog} />
  </>
);

export default Routes;
