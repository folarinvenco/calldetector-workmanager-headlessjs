import CallDetectorManager from 'react-native-call-detection';


module.export = async () => {
    console.log('background task')
    new CallDetectorManager(
        (event, phoneNumber) => {
            // For iOS event will be either "Connected",
            // "Disconnected","Dialing" and "Incoming"

            // For Android event will be either "Offhook",
            // "Disconnected", "Incoming" or "Missed"
            // phoneNumber should store caller/called number

            console.log({ event, phoneNumber });
            console.log('phoneNumber', phoneNumber);

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
        () => { }, // callback if your permission got denied [ANDROID] [only if you want to read incoming number] default: console.error
        {
            title: 'Phone State Permission',
            message:
                'This app needs access to your phone state in order to react and/or to adapt to incoming calls.',
        }, // a custom permission request message to explain to your user, why you need the permission [recommended] - this is the default one
    );

}