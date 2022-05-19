// import {useStore} from '@src/zustand';
import {useNavigation} from '@react-navigation/native';
import HelloWorld from '@src/components/HelloWorld';
import React from 'react';
import {StyleSheet, Text, TouchableOpacity, View} from 'react-native';
import {RootStacksProp} from './Stacks';
import {useStore} from './useStore';

interface AppProps {
  navigation?: RootStacksProp;
}

const App: React.FC<AppProps> = props => {
  const [bears, increasePopulation] = useStore(state => [
    state.bears,
    state.increasePopulation,
  ]);

  const {navigation} = props;
  return (
    <View style={{flex: 1, justifyContent: 'center', alignItems: 'center'}}>
      <TouchableOpacity
        onPress={() => {
          increasePopulation(1);
          navigation.navigate('Debug', {id: Math.random().toString()});
        }}>
        <Text>Zustand 🐻</Text>
      </TouchableOpacity>
      <View style={{height: 12}} />
      <HelloWorld />
    </View>
  );
};

const styles = StyleSheet.create({});

export default App;
