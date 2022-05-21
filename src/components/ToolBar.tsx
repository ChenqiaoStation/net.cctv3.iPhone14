import {useStore} from '@root/useStore';
import {AnyView} from '@src/types';
import {useDip, useStatusBarHeight} from '@src/utils';
import React from 'react';
import {Image, View, Text, TouchableOpacity, StyleSheet} from 'react-native';

interface ToolBarProps {
  title: string;
  onBackPress: () => void;
  moreView?: AnyView;
}

const ToolBar: React.FC<ToolBarProps> = props => {
  const [bears, increasePopulation, setting] = useStore(state => [
    state.bears,
    state.increasePopulation,
    state.setting,
  ]);

  const {title, onBackPress, moreView} = props;

  return (
    <>
      <View
        style={{
          height: useStatusBarHeight(true, true),
          backgroundColor: 'white',
        }}
      />
      <View style={styles.viewContainer}>
        <TouchableOpacity onPress={onBackPress} activeOpacity={0.88}>
          <Image
            source={require('@src/images/common_back.png')}
            style={styles.viewBack}
          />
        </TouchableOpacity>
        <Text style={{fontSize: 20, color: '#333', fontWeight: '500'}}>
          {title}
        </Text>
        {moreView ?? <View style={styles.viewBack} />}
      </View>
    </>
  );
};

const styles = StyleSheet.create({
  viewBack: {
    height: useDip(20),
    width: useDip(20),
  },
  viewContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: 'white',
    justifyContent: 'space-between',
    height: 44,
    paddingHorizontal: 16,
  },
});

export default ToolBar;
