import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { openFile, byteSize, Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import ThumbUpAltIcon from '@material-ui/icons/ThumbUpAlt';
import ThumbDownAltIcon from '@material-ui/icons/ThumbDownAlt';
import EditIcon from '@material-ui/icons/Edit';
import DeleteIcon from '@material-ui/icons/Delete';
import ReactDOMServer from 'react-dom/server';
import { IRootState } from 'app/shared/reducers';
import { getEntities, acceptUser, rejectUser } from './mobile-user.reducer';
import { IMobileUser } from 'app/shared/model/mobile-user.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import MUIDataTable from "mui-datatables";
import { toast } from 'react-toastify';
export interface IMobileUserProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const MobileUser = (props: IMobileUserProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);


  const getValueFromSPan = (spanString: string) => {
    const doc = new DOMParser().parseFromString(spanString, "text/xml");
    return doc.firstChild.textContent;
  }

  const translate = (key: any, txt: any) => {
    return getValueFromSPan(ReactDOMServer.renderToString(<Translate contentKey={key}>{txt}</Translate>));
  }

  const acceptReport = (data) => {
    if (data.data === true) {
      toast.success(<Translate contentKey="global.status.success">Success</Translate>)
      props.getEntities();
    } else {
      toast.error(<Translate contentKey="global.status.failure">Failure</Translate>)
    }

  };


  const rejectReport = (data) => {

    if (data.data === true) {
      toast.success(<Translate contentKey="global.status.success">Success</Translate>)
      props.getEntities();
    } else {
      toast.error(<Translate contentKey="global.status.failure">Failure</Translate>)
    }
  };

  const acceptAction = (id) => {
    acceptUser(id, acceptReport)
  }

  const rejectAction = (id) => {
    rejectUser(id, rejectReport)
  }

  const { mobileUserList, match, loading } = props;

  const options = {
    filterType: "dropdown",
    elevation: 0,
    responsive: "scroll",
    selectableRows: "none",
    selectableRowsHeader: false,
    textLabels: {
      body: {
        noMatch: translate("global.table.body.noMatch", "noMatch"),
        toolTip: translate("global.table.body.toolTip", "toolTip"),
      },
      pagination: {
        next: translate("global.table.pagination.next", "next"),
        previous: translate("global.table.pagination.previous", "previous"),
        rowsPerPage: translate("global.table.pagination.rowsPerPage", "rowsPerPage"),
        displayRows: translate("global.table.pagination.displayRows", "displayRows"),
      },
      toolbar: {
        search: translate("global.table.toolbar.search", "search"),
        downloadCsv: translate("global.table.toolbar.downloadCsv", "downloadCsv"),
        print: translate("global.table.toolbar.print", "print"),
        viewColumns: translate("global.table.toolbar.viewColumns", "viewColumns"),
        filterTable: translate("global.table.toolbar.filterTable", "filterTable"),
      },
      filter: {
        all: translate("global.table.filter.all", "all"),
        title: translate("global.table.filter.title", "title"),
        reset: translate("global.table.filter.reset", "reset"),
      },
      viewColumns: {
        title: translate("global.table.viewColumns.title", "title"),
        titleAria: translate("global.table.viewColumns.titleAria", "titleAria"),
      }
    }
  };

  const columns = [
    {
      name: "ID",
      label: translate("global.field.id", "ID"),
      options: {
        filter: false,
        sort: true
      }
    },
    {
      name: "Citizen Id",
      label: translate("covidFreeBackendApp.mobileUser.citizenId", "Citizen Id"),
      options: {
        filter: false,
        sort: false
      }
    },
    {
      name: "Phone Number",
      label: translate("covidFreeBackendApp.mobileUser.phoneNumber", "Phone Number"),
      options: {
        filter: false,
        sort: true
      }
    },
    {
      name: "IdcardImage",
      label: translate("covidFreeBackendApp.mobileUser.idcardImage", "IdcardImage"),
      options: {
        filter: false,
        sort: false
      }
    },
    {
      name: "Valid",
      label: translate("covidFreeBackendApp.mobileUser.valid", "Valid"),
      options: {
        filter: true,
        sort: false
      }
    },
    {
      name: "Status",
      label: translate("covidFreeBackendApp.mobileUser.status", "Status"),
      options: {
        filter: true,
        sort: false
      }
    },
    {
      name: "CreateDate",
      label: translate("covidFreeBackendApp.mobileUser.createDate", "CreateDate"),
      options: {
        filter: false,
        sort: true,
        display: true
      }
    },
    {
      name: "UpdateDate",
      label: translate("covidFreeBackendApp.mobileUser.updateDate", "UpdateDate"),
      options: {
        filter: false,
        sort: true,
        display: true

      }
    },
    {
      name: "OtpCodes",
      label: translate("covidFreeBackendApp.mobileUser.otpCodes", "OtpCodes"),
      options: {
        filter: false,
        sort: true,
        display: false

      }
    },
    {
      name: "",
      label: "",
      options: {
        viewColumns: false,
        filter: false,
        sort: false,
        // eslint-disable-line
        customBodyRender(value) {
          return (
            <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${value}/edit`} color="info" size="sm">
                        <EditIcon  fontSize = "small"/>{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button onClick={() => {
                        acceptAction(value)
                      }} color="success" size="sm">
                        <ThumbUpAltIcon  fontSize = "small"/>{' '}
                        <span className="d-none d-md-inline">
                        <Translate contentKey="entity.action.accept">Accept</Translate>
                      </span>
                      </Button>
                      <Button onClick={() => {
                        rejectAction(value)
                      }} color="primary" size="sm">
                        <ThumbDownAltIcon  fontSize = "small"/>{' '}
                        <span className="d-none d-md-inline">
                        <Translate contentKey="entity.action.reject">Reject</Translate>
                      </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${value}/delete`} color="danger" size="sm">
                        <DeleteIcon fontSize = "small" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
          )
        }
      }
    }
  ]


  const setJsonArray = (_mobileUserList) => {
    const result = [];
    _mobileUserList.forEach((element: IMobileUser) => {
      const arr = [];
      arr.push(element.id)
      arr.push(element.citizenId)
      arr.push(element.phoneNumber)
      arr.push(
        <td>
        {element.idcardImage ? (
          <div>
            {element.idcardImageContentType ? (
              <a onClick={openFile(element.idcardImageContentType, element.idcardImage)}>
                <img
                  src={`data:${element.idcardImageContentType};base64,${element.idcardImage}`}
                  style={{ maxHeight: '30px' }}
                />
                &nbsp;
              </a>
            ) : null}
            <span>
              {element.idcardImageContentType}, {byteSize(element.idcardImage)}
            </span>
          </div>
        ) : null}
      </td>)
      arr.push(element.valid ? 'true' : 'false')
      arr.push(element.statusType)
      arr.push(<td>
        {element.createDate ? <TextFormat type="date" value={element.createDate} format={APP_DATE_FORMAT} /> : null}
      </td>)
      arr.push(<td>
        {element.updateDate ? <TextFormat type="date" value={element.updateDate} format={APP_DATE_FORMAT} /> : null}
      </td>)
      arr.push(element.otpCodes)
      arr.push(element.id)
      result.push(arr);
    });
    return result;
  }

 
  return (
    <MUIDataTable
    title={"Mobil Users"}
    data={setJsonArray(mobileUserList)}
    columns={columns}
    options={options}
  />
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
