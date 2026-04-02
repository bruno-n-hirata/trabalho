import React from 'react';
import { View, Text, TextInput, StyleSheet } from 'react-native';

export default function Campo({ label, value, onChangeText, ...props }) {
  return (
    <View style={styles.container}>
      <Text style={styles.label}>{label}</Text>
      <TextInput style={styles.textInput} value={value} onChangeText={onChangeText} {...props} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    marginBottom: 12,
  },
  label: {
    marginBottom: 4,
    fontWeight: '500',
  },
  textInput: {
    borderWidth: 1,
    borderColor: '#ccc',
    paddingHorizontal: 8,
    paddingVertical: 6,
    borderRadius: 20,
  },
});
