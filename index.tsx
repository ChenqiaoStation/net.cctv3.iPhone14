import React from 'react';
import {AppRegistry, StatusBar, View} from 'react-native';
import {useStore} from './useStore';
import createContext from 'zustand/context';
import App from './App';
// @ts-ignore
import {name as appName} from './app.json';

const StoreContext = createContext();

interface iPhone14Props {}

const iPhone14: React.FC<iPhone14Props> = props => {
  return (
    <StoreContext.Provider createStore={() => useStore}>
      <View style={{flex: 1}}>
        <StatusBar translucent={true} barStyle="dark-content" />
        <App />
        {/* <View style={{height: useHomeIndicatorHeight()}} /> */}
      </View>
    </StoreContext.Provider>
  );
};

AppRegistry.registerComponent(appName, () => iPhone14);
