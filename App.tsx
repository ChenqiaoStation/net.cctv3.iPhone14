// import {useStore} from '@src/zustand';
import React from 'react';
import {StyleSheet, Text, TouchableOpacity, View} from 'react-native';
import {useStore} from './useStore';

interface AppProps {}

const App: React.FC<AppProps> = () => {
  const [bears, increasePopulation] = useStore(state => [
    state.bears,
    state.increasePopulation,
  ]);
  return (
    <View style={{flex: 1, justifyContent: 'center', alignItems: 'center'}}>
      <TouchableOpacity
        onPress={() => {
          increasePopulation(1);
        }}>
        <Text>Zustand 🐻</Text>
      </TouchableOpacity>
      <View style={{height: 12}} />
      <Text>{bears}</Text>
    </View>
  );
};

const styles = StyleSheet.create({});

export default App;
