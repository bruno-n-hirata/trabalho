import React, { useState } from 'react';
import { StyleSheet, View, Alert } from 'react-native';
import api from '../api';
import Botao from './Botao';
import Campo from './Campo';

export default function Edicao({ route, navigation }) {
    const { tarefa } = route.params;

    const [nome, setNome] = useState(tarefa.nome);
    const [descricao, setDescricao] = useState(tarefa.descricao);
    const [tempo, setTempo] = useState(String(tarefa.tempo));

    const atualizarTarefa = async () => {
      if (!nome.trim()) {
        Alert.alert("Validação", "O campo nome não pode ficar em branco.");
        return;
      }

      try {
        await api.put(`/tarefas/${tarefa.id}`, {
          nome,
          descricao,
          tempo: parseInt(tempo) || 0
        });
        navigation.goBack();
      } catch (error) {
        console.log('Erro ao atualizar tarefa:', error.message);
        Alert.alert("Erro", "Não foi possível atualizar a tarefa.");
      }
    };

    return (
        <View style={styles.container}>
            <Campo label='Nome da Tarefa' value={nome} onChangeText={setNome} />
            <Campo label='Descrição' value={descricao} onChangeText={setDescricao} />
            <Campo label='Tempo (horas)' value={tempo} onChangeText={setTempo} keyboardType='numeric' />
            <Botao title='Salvar' onPress={atualizarTarefa} />
        </View>
    );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 16,
  },
});
