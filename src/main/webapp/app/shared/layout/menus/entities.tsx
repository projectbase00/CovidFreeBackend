import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/mobile-user">
      <Translate contentKey="global.menu.entities.mobileUser" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/event-logging">
      <Translate contentKey="global.menu.entities.eventLogging" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/otp-codes">
      <Translate contentKey="global.menu.entities.otpCodes" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
