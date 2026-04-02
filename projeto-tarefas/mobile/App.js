import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import Home from './components/Home';
import Cadastro from './components/Cadastro';
import Edicao from './components/Edicao';

const Stack = createStackNavigator();

export default function App() {
  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName='Home'>
        <Stack.Screen name='Home' component={Home}/>
        <Stack.Screen name='Cadastro' component={Cadastro}/>
        <Stack.Screen name='Edição' component={Edicao}/>
      </Stack.Navigator>
    </NavigationContainer>
  );
}
