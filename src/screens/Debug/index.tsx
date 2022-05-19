import {RouteProp} from '@react-navigation/native';
import {RootStacksParams, RootStacksProp} from '@root/Stacks';
import React from 'react';
import {Image, Text, View} from 'react-native';

interface DebugProps {
  navigation?: RootStacksProp;
  route?: RouteProp<RootStacksParams, 'Debug'>;
}

const Debug: React.FC<DebugProps> = props => {
  const {route} = props;
  return (
    <View style={{alignItems: 'center'}}>
      <Image source={require('@src/images/HelloWorld.png')} />
      <Text>{`${route.params.id}`}</Text>
    </View>
  );
};

export default Debug;
