import {BottomTabBarButtonProps} from '@react-navigation/bottom-tabs';
import {BottomTabItem} from '@src/types';
import {useDip, useGoogleColors} from '@src/utils';
import React, {useEffect, useRef} from 'react';
import {Image, StyleSheet, Text, TouchableOpacity, View} from 'react-native';
import * as Animatable from 'react-native-animatable';

interface BottomTabProps {
  item: BottomTabItem;
  activeColor: string;
  index: number;
}

const BottomTab: React.FC<BottomTabProps & BottomTabBarButtonProps> = props => {
  const {item, accessibilityState, onPress, index} = props;
  const selected = accessibilityState.selected;
  const tabRef = useRef<Animatable.View & View>(null);
  const textRef = useRef<Animatable.View & View>(null);
  let {red, orange, green, mauve, brown} = useGoogleColors;
  const colors = [red, orange, green, mauve, brown];

  useEffect(() => {
    if (selected) {
      // @ts-ignore
      tabRef.current.animate({
        0: {scale: 1},
        0.1: {scale: 0.9},
        1: {scale: 1},
      });
      // @ts-ignore
      textRef.current.animate({
        0: {scale: 1},
        0.618: {scale: 0.8},
        1: {scale: 1},
      });
    } else {
      // @ts-ignore
      // tabRef.current.animate({
      //   0: {scale: 0.618},
      //   1: {scale: 1},
      // });
    }
    return () => {};
  }, [selected]);

  return (
    <TouchableOpacity
      onPress={onPress}
      style={[styles.viewTab, selected ? {flex: 1} : {flex: 0.618}]}>
      <Animatable.View
        ref={tabRef}
        style={[
          {flexDirection: 'row', alignItems: 'center'},
          selected && {
            backgroundColor: colors[index].light,
            paddingHorizontal: 12,
            paddingVertical: 6,
            borderRadius: 8,
            justifyContent: 'center',
          },
        ]}
        useNativeDriver={true}>
        <Image
          source={item.icon}
          style={{
            height: useDip(24),
            width: useDip(24),
            tintColor: selected ? colors[index].dark : '#666',
          }}
        />
        <Animatable.View ref={textRef} useNativeDriver={true}>
          {selected && (
            <Text
              style={{
                fontSize: useDip(16),
                color: colors[index].dark,
                marginLeft: 6,
              }}>
              {item.name}
            </Text>
          )}
        </Animatable.View>
      </Animatable.View>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  viewTab: {
    borderRadius: 12,
    justifyContent: 'center',
    alignItems: 'center',
    flexDirection: 'row',
    flex: 1,
  },
});

export default BottomTab;
