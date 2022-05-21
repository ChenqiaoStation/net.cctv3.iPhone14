import {RouteProp} from '@react-navigation/native';
import {RootStacksParams, RootStacksProp} from '@root/Stacks';
import {useStore} from '@root/useStore';
import ToolBar from '@src/components/ToolBar';
import React from 'react';
import {Image, Text, TouchableOpacity, View} from 'react-native';

interface DebugProps {
  navigation?: RootStacksProp;
  route?: RouteProp<RootStacksParams, 'Debug'>;
}

const Debug: React.FC<DebugProps> = props => {
  const {route, navigation} = props;
  const [bears, increasePopulation] = useStore(state => [
    state.bears,
    state.increasePopulation,
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
          }}>
          <Image source={require('@src/images/HelloWorld.png')} />
        </TouchableOpacity>
        <Text>{`${route.params?.id} -> ${bears}`}</Text>
      </View>
    </>
  );
};

export default Debug;
