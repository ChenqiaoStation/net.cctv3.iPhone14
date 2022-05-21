import {Dimensions, Platform, StatusBar} from 'react-native';

/**
 * 屏幕适配暂时的方案
 * @param px
 * @returns
 */
const useDip = (px: number) =>
  Math.floor((px * Dimensions.get('screen').width) / 375);

/**
 * 是不是刘海屏
 * @returns
 */
const isiPhoneXSMax = () => {
  const screenHeights = [780, 812, 844, 896, 926];
  const screen = Dimensions.get('screen');
  return (
    Platform.OS == 'ios' &&
    screenHeights.some(it => it == screen.height || it == screen.width)
  );
};

/**
 * 状态栏的高度
 * @param isImmersible Android 平台是否沉浸式
 * @param isSafe iPhone X 刘海高度，如果留白边返回 44，否则返回 34
 * @returns number
 */
const useStatusBarHeight = (isImmersible: boolean, isSafe?: boolean) => {
  return Platform.select({
    android: isImmersible ? StatusBar.currentHeight : 0,
    ios: isiPhoneXSMax() ? (isSafe ? 44 : 34) : 20,
  });
};

/**
 * Home Indicator 的高度
 * @returns number
 */
const useHomeIndicatorHeight = () => {
  return isiPhoneXSMax() ? 34 : 0;
};

const useGoogleColors = {
  red: {name: '嫣红', dark: '#e74b44', light: '#fcdcdd'},
  orange: {name: '橘橙', dark: '#f57c1f', light: '#fee7d5'},
  yellow: {name: '明黄', dark: '#fdbc0a', light: '#fff3d1'},
  olive: {name: '橄榄', dark: '#8ec541', light: '#fcf4db'},
  green: {name: '森绿', dark: '#3ab54b', light: '#d9f0df'},
  cyan: {name: '天青', dark: '#1ebcb6', light: '#d4f2f4'},
  blue: {name: '海蓝', dark: '#0282ff', light: '#cfe7ff'},
  purple: {name: '姹紫', dark: '#6938b9', light: '#e2d7f3'},
  mauve: {name: '木槿', dark: '#9e28b2', light: '#edd4f2'},
  pink: {name: '桃粉', dark: '#e2399a', light: '#fcd7ec'},
  brown: {name: '棕褐', dark: '#a86640', light: '#f0e1dc'},
  grey: {name: '玄灰', dark: '#8899a7', light: '#eaebf1'},
};

export {
  useDip,
  isiPhoneXSMax,
  useHomeIndicatorHeight,
  useStatusBarHeight,
  useGoogleColors,
};
