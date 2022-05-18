import AsyncStorage from '@react-native-async-storage/async-storage';
import {ZustandTypes} from '@src/types';
import create from 'zustand';
import {devtools, persist} from 'zustand/middleware';

export const useStore = create<ZustandTypes>(
  devtools(
    persist(
      (set, get) => ({
        /** Zustand 默认 */
        bears: 0,
        increasePopulation: n => set(state => ({bears: state.bears + n})),
        removeAllBears: () => set({bears: 0}),
      }),
      {
        name: 'Cached useStorage',
        getStorage: () => AsyncStorage,
        /** 白名单 */
        partialize: state => ({bears: state.bears}),
      },
    ),
    {anonymousActionType: 'useStore.action'},
  ),
);