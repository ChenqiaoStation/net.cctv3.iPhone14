import {RouteProp} from '@react-navigation/native';
import {RootStacksParams, RootStacksProp} from '@root/Stacks';
import {useStore} from '@root/useStore';
import ToolBar from '@src/components/ToolBar';
import React from 'react';
import {Image, Text, TouchableOpacity, View} from 'react-native';
import {useUUID} from '../../utils';

interface DebugProps {
  navigation?: RootStacksProp;
  route?: RouteProp<RootStacksParams, 'Debug'>;
}

const Debug: React.FC<DebugProps> = props => {
  const {route, navigation} = props;
  const [bears, increasePopulation, mergeLogs] = useStore(state => [
    state.bears,
    state.increasePopulation,
    state.mergeLogs,
  ]);

  return (
    <>
      <ToolBar
        onBackPress={() => {
          navigation.goBack();
        }}
        title="测试页面"
      />
      <View style={{alignItems: 'center'}}>
        <TouchableOpacity
          onPress={() => {
            increasePopulation(1);
            // console.log({useUUID: useUUID()});
            mergeLogs({title: 'useUUID', message: useUUID()});
          }}>
          <Image source={require('@src/images/HelloWorld.png')} />
        </TouchableOpacity>
        <Text>{`${route.params?.id} -> ${bears}`}</Text>
      </View>
    </>
  );
};

export default Debug;
