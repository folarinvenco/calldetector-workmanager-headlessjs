/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * Generated with the TypeScript template
 * https://github.com/react-native-community/react-native-template-typescript
 *
 * @format
 */

import React from 'react';
import {
  SafeAreaView,
  ScrollView,
  StatusBar,
  StyleSheet,
  Text,
  View,
  NativeModules,
} from 'react-native';
import CallDetectorManager from 'react-native-call-detection';
import notifee from '@notifee/react-native';
const App = () => {
  const callDetectorRef = React.useRef(CallDetectorManager);

  // function stopListenerTapped() {
  //   callDetectorRef.current && callDetectorRef.current.dispose();
  // }

  React.useEffect(() => {
    NativeModules.BackgroundWorkerManager.startBackgroundWork(
      'CALL_DETECTOR_BACKGROUND_WORKER'
    );
    console.log(NativeModules.BackgroundWorkerManager.getConstants());
  }, []);

  React.useEffect(() => {
    new callDetectorRef.current(
      (event, phoneNumber) => {
        // For iOS event will be either "Connected",
        // "Disconnected","Dialing" and "Incoming"

        // For Android event will be either "Offhook",
        // "Disconnected", "Incoming" or "Missed"
        // phoneNumber should store caller/called number
        (async () => {
          await notifee.createChannel({ id: 'test', name: 'test' });
          await notifee.displayNotification({
            title: `You received a call from ${phoneNumber}`,
            body: 'Confirming if the user is a resident of the community',
            android: {
              channelId: 'test',
              // smallIcon: 'name-of-a-small-icon', // optional, defaults to 'ic_launcher'.
              // pressAction is needed if you want the notification to open the app when pressed
              pressAction: {
                id: 'default',
              },
            },
          });
        })();

        console.log({ event, phoneNumber });
        console.log('phoneNumber', phoneNumber);
        notifee.displayNotification({ android: {} });
        if (event === 'Disconnected') {
          // Do something call got disconnected
        } else if (event === 'Connected') {
          // Do something call got connected
          // This clause will only be executed for iOS
        } else if (event === 'Incoming') {
          // Do something call got incoming
        } else if (event === 'Dialing') {
          // Do something call got dialing
          // This clause will only be executed for iOS
        } else if (event === 'Offhook') {
          //Device call state: Off-hook.
          // At least one call exists that is dialing,
          // active, or on hold,
          // and no calls are ringing or waiting.
          // This clause will only be executed for Android
        } else if (event === 'Missed') {
          // Do something call got missed
          // This clause will only be executed for Android
        }
      },
      false, // if you want to read the phone number of the incoming call [ANDROID], otherwise false
      () => {}, // callback if your permission got denied [ANDROID] [only if you want to read incoming number] default: console.error
      {
        title: 'Phone State Permission',
        message:
          'This app needs access to your phone state in order to react and/or to adapt to incoming calls.',
      } // a custom permission request message to explain to your user, why you need the permission [recommended] - this is the default one
    );
  });

  return (
    <SafeAreaView>
      <StatusBar barStyle={'light-content'} />
      <ScrollView>
        <View>
          <Text>react</Text>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
};

const hitSlop = { top: 10, left: 10, right: 10, bottom: 10 };
const styles = StyleSheet.create({
  container: {
    flex: 1,
    marginTop: 20,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
  button: {
    marginTop: 20,
    marginBottom: 20,
  },
  callButtons: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    paddingHorizontal: 30,
    width: '100%',
  },
  logContainer: {
    flex: 3,
    width: '100%',
    backgroundColor: '#D9D9D9',
  },
  log: {
    fontSize: 10,
  },
});

export default App;
