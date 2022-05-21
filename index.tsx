import React, {useEffect} from 'react';
import {AppRegistry, StatusBar, View} from 'react-native';
import createContext from 'zustand/context';
// @ts-ignore
import {name as appName} from './app.json';
import Stacks from './Stacks';
import {useStore} from './useStore';
import RNFS from 'react-native-fs';

const StoreContext = createContext();

interface iPhone14Props {}

const iPhone14: React.FC<iPhone14Props> = props => {
  useEffect(() => {
    console.log({DocumentPath: RNFS.DocumentDirectoryPath});
    return function () {};
  }, [props]);

  return (
    <StoreContext.Provider createStore={() => useStore}>
      <View style={{flex: 1}}>
        <StatusBar
          translucent={true}
          barStyle="dark-content"
          backgroundColor={'transparent'}
        />
        <Stacks />
        {/* <View style={{height: useHomeIndicatorHeight()}} /> */}
      </View>
    </StoreContext.Provider>
  );
};

AppRegistry.registerComponent(appName, () => iPhone14);
