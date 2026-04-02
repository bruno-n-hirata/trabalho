import React from 'react';
import { View, Text, StyleSheet } from 'react-native';

export default function ExibeStatus({ status }) {
  const color = status === 'Concluída' ? '#4CAF50' : '#2196F3';

  return (
    <View style={[styles.statusContainer, { backgroundColor: color }]}>
      <Text style={styles.statusText}>{status}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  statusContainer: {
    borderRadius: 12,
    paddingHorizontal: 10,
    paddingVertical: 4,
  },
  statusText: {
    color: '#fff',
    fontWeight: 'bold',
    fontSize: 12,
  },
});
