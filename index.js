/**
 * @format
 */

import {AppRegistry} from 'react-native';
import App from './App';
import {name as appName} from './app.json';

AppRegistry.registerHeadlessTask('RNCallKeepBackgroundMessage', () => ({ name, callUUID, handle }) => {
  // Make your call here
  console.log('got here....');
  console.log(name, callUUID);
  return Promise.resolve();
});

/**
 * Issues
 * https://github.com/react-native-webrtc/react-native-callkeep/issues/594
 */
AppRegistry.registerComponent(appName, () => App);
