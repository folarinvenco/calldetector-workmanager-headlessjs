/**
 * @format
 */

import { AppRegistry } from 'react-native';
import App from './App';
import { name as appName } from './app.json';
// import notifee from '@notifee/react-native';

// notifee.onBackgroundEvent(async ({ type, detail }) => {
//     const { notification, pressAction, data } = detail;

//     console.log(JSON.stringify({ detail }))
//     await notifee.displayNotification({
//         title: `You received a call from ${data?.phoneNumber}`,
//         body: 'Confirming if the user is a resident of the community',
//         android: {
//             channelId: "test",
//             // smallIcon: 'name-of-a-small-icon', // optional, defaults to 'ic_launcher'.
//             // pressAction is needed if you want the notification to open the app when pressed
//             pressAction: {
//                 id: 'default',
//             },
//         },
//     });
//     // await notifee.cancelNotification(notification.id);
// });


// AppRegistry.registerHeadlessTask('CallDetector', () => async (data) => {
//     //1
//     //2
//     //3
//     //4
//     //5
//     //6
//     //7
//     console.log('......call detector', data)
//     await notifee.displayNotification({
//         title: `You received a call from ${data?.phoneNumber}`,
//         body: 'Confirming if the user is a resident of the community',
//         android: {
//             channelId: "test",
//             // smallIcon: 'name-of-a-small-icon', // optional, defaults to 'ic_launcher'.
//             // pressAction is needed if you want the notification to open the app when pressed
//             pressAction: {
//                 id: 'default',
//             },
//         },
//     });
// });





AppRegistry.registerComponent(appName, () => App);

AppRegistry.registerHeadlessTask('CallDetector', () => async (data) => {
    //1
    //2
    //3
    //4
    //5
    //6
    //7
    console.log('......call detector', data)
});

