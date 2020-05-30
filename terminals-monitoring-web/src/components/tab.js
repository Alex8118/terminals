import React from 'react';
import { Tabs, Tab, TabPanel, TabList } from 'react-web-tabs';
import 'react-web-tabs/dist/react-web-tabs.css';

export default class settingTab extends React.Component {
  render() {
    return (
      <div className="pd-t-4 pd-l-18 pr-md-5">
        <Tabs defaultTab="vertical-tab-one" vertical>
          <TabList>
            <Tab tabFor="vertical-tab-one">Профиль</Tab>
            <Tab tabFor="vertical-tab-two">Токен</Tab>
          </TabList>
          <TabPanel tabId="vertical-tab-one">
            <p>
              <strong>
                Имя: <em>{sessionStorage.getItem('userName')}</em>
              </strong>
            </p>
            <hr />
            <p>
              <strong>
                Электронная почта: <em>{sessionStorage.getItem('email')}</em>
              </strong>
            </p>
          </TabPanel>
          <TabPanel tabId="vertical-tab-two" className="pd-r-18">
            <p>
              <b>encryptApiToken</b> используется для формирования параметра
              <b> Authorization</b> (используется для подписания запросов от
              терминалов). <b>Authorization</b> формируется следующим образом:
              Base64 (HMAC-SHA512 (encryptApiToken + ownerId + timestamp)), где:
              <b> ownerId</b> - ваш идентификатор, <b>timestamp</b> - текущее
              время.
              <b> encryptApiToken</b> - должен быть прописан на устройстве и
              участвовать в формировании параметра <b>Authorization</b>.
            </p>

            <hr />

            <strong>
              Ваш токен: <em>{this.props.token}</em>
            </strong>
          </TabPanel>
        </Tabs>
      </div>
    );
  }
}
