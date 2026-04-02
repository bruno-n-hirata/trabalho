import React from 'react';
import { TouchableOpacity, Text, StyleSheet } from 'react-native';


export default function Botao({ title, onPress, bgColor = '#2196F3' }) {
  return (
    <TouchableOpacity style={[styles.buttonContainer, { backgroundColor: bgColor }]} onPress={onPress}>
      <Text style={styles.buttonText}>{title}</Text>
    </TouchableOpacity>
  );
}
  
const styles = StyleSheet.create({
  buttonContainer: {
    paddingVertical: 12,
    paddingHorizontal: 20,
    borderRadius: 20,
    margin: 4,
  },
  buttonText: {
    color: '#fff',
    fontWeight: 'bold',
    textAlign: 'center',
  }
});
