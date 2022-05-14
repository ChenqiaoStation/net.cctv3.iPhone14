import React from 'react';
import {Image, View} from 'react-native';

interface HelloWorldProps {}
const HelloWorld: React.FC<HelloWorldProps> = props => {
  return (
    <View>
      <Image source={require('@src/images/HelloWorld.png')} />
    </View>
  );
};

export default HelloWorld;
